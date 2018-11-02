package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.PineryUtils.*;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.service.RunService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Sequencer Runs"})
public class RunResource {

   @Autowired
   private RunService runService;

   void setRunService(RunService runService) {
      this.runService = runService;
   }

   @GetMapping("/sequencerruns")
   @ApiOperation(value = "List all sequencer runs", response = RunDto.class, responseContainer = "List")
   public List<RunDto> getRuns(UriComponentsBuilder uriBuilder) {
      List<Run> runs = runService.getRun();
      List<RunDto> result = Lists.newArrayList();
      for (Run run : runs) {
         RunDto dto = Dtos.asDto(run);
         addUrls(dto, uriBuilder);
         result.add(dto);
      }
      return result;
   }

   @GetMapping("/sequencerrun/{id}")
   @ApiOperation(value = "Find sequencer run by ID", response = RunDto.class)
   @ApiResponses({@ApiResponse(code = 404, message = "No sequencer run found")})
   public RunDto getRun(UriComponentsBuilder uriBuilder,
       @ApiParam(value = "ID of sequencer run to fetch") @PathVariable("id") Integer id) {
      Run run = runService.getRun(id);
      if (run == null) {
        throw new RestException(HttpStatus.NOT_FOUND, "No run found with ID: " + id);
      }
      RunDto dto = Dtos.asDto(run);
      addUrls(dto, uriBuilder);

      return dto;
   }
   
   @GetMapping("/sequencerrun")
   @ApiOperation(value = "Find sequencer run by name", response = RunDto.class)
   @ApiResponses({
     @ApiResponse(code = 400, message = "Missing or invalid name parameter"),
     @ApiResponse(code = 404, message = "No sequencer run found")
   })
   public RunDto getRunByName(UriComponentsBuilder uriBuilder,
       @ApiParam(value = "Name of sequencer run to fetch") @RequestParam("name") String runName) {
     if (runName == null || runName.isEmpty()) {
       throw new RestException(HttpStatus.BAD_REQUEST, "Name parameter is required");
     }
     
     Run run = runService.getRun(runName);
     if (run == null) {
       throw new RestException(HttpStatus.NOT_FOUND, "No run found with name: " + runName);
     }
     RunDto dto = Dtos.asDto(run);
     addUrls(dto, uriBuilder);
     
     return dto;
   }

   private void addUrls(RunDto dto, UriComponentsBuilder uriBuilder) {
      URI baseUri = getBaseUri(uriBuilder);
      dto.setUrl(buildRunUrl(baseUri, dto.getId()));

      if (dto.getPositions() != null) {
         for (RunDtoPosition runDtoPosition : dto.getPositions()) {
           if (runDtoPosition.getSamples() != null && !runDtoPosition.getSamples().isEmpty()) {
             for (RunDtoSample runDtoSample : runDtoPosition.getSamples()) {
               runDtoSample.setUrl(buildSampleUrl(baseUri, runDtoSample.getId()));
             }
           }
         }
      }
      if (dto.getCreatedById() != null) {
         dto.setCreatedByUrl(buildUserUrl(baseUri, dto.getCreatedById()));
      }
      if (dto.getModifiedById() != null) {
        dto.setModifiedByUrl(buildUserUrl(baseUri, dto.getModifiedById()));
      }
      if (dto.getInstrumentId() != null) {
         dto.setInstrumentUrl(buildInstrumentUrl(baseUri, dto.getInstrumentId()));
      }
   }
}
