package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.service.RunService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;

import com.google.common.collect.Lists;

@Component
@Path("/")
@Api(value = "sequencer run")
public class RunResource {

//   private static final Logger log = LoggerFactory.getLogger(RunResource.class);

   @Context
   private UriInfo uriInfo;

   @Autowired
   private RunService runService;

   void setUriInfo(UriInfo uriInfo) {
      this.uriInfo = uriInfo;
   }

   void setRunService(RunService runService) {
      this.runService = runService;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sequencerruns")
   @ApiOperation(value = "List all sequencer runs", response = RunDto.class, responseContainer = "List")
   public List<RunDto> getRuns() {
      List<Run> runs = runService.getRun();
      List<RunDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sequencerrun").build();
      for (Run run : runs) {
         RunDto dto = Dtos.asDto(run);
         dto.setUrl(baseUri + "/" + dto.getId().toString());
         addUrls(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sequencerrun/{id}")
   @ApiOperation(value = "Find sequencer run by ID", response = RunDto.class)
   @ApiResponse(code = 404, message = "No sequencer run found")
   public RunDto getRun(@ApiParam(value = "ID of sequencer run to fetch", required = true) @PathParam("id") Integer id) {
      Run run = runService.getRun(id);
      if (run == null) {
        throw new NotFoundException("No run found with ID: " + id);
      }
      RunDto dto = Dtos.asDto(run);
      final URI uri = uriInfo.getAbsolutePathBuilder().build();
      dto.setUrl(uri.toString());
      addUrls(dto);

      return dto;
   }
   
   @GET
   @Produces({ "application/json" })
   @Path("/sequencerrun")
   @ApiOperation(value = "Find sequencer run by name", response = RunDto.class)
   @ApiResponses({
     @ApiResponse(code = 400, message = "Missing or invalid name parameter"),
     @ApiResponse(code = 404, message = "No sequencer run found")
   })
   public RunDto getRunByName(@ApiParam(value = "Name of sequencer run to fetch", required = true) @QueryParam("name") String runName) {
     if (runName == null || runName.isEmpty()) {
       throw new BadRequestException("Name parameter is required");
     }
     
     Run run = runService.getRun(runName);
     if (run == null) {
       throw new NotFoundException("No run found with name: " + runName);
     }
     RunDto dto = Dtos.asDto(run);
     final URI baseUri = uriInfo.getBaseUriBuilder().path("sequencerrun").build();
     dto.setUrl(baseUri + "/" + dto.getId().toString());
     addUrls(dto);
     
     return dto;
   }

   private void addUrls(RunDto dto) {
      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      final URI baseUriInstrument = uriInfo.getBaseUriBuilder().path("instrument/").build();
      final URI baseUriSample = uriInfo.getBaseUriBuilder().path("sample/").build();

      if (dto.getPositions() != null) {
         for (RunDtoPosition runDtoPosition : dto.getPositions()) {
           if (runDtoPosition.getSamples() != null && !runDtoPosition.getSamples().isEmpty()) {
             for (RunDtoSample runDtoSample : runDtoPosition.getSamples()) {
               runDtoSample.setUrl(baseUriSample + runDtoSample.getId().toString());
             }
           }
         }
      }
      if (dto.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUri + dto.getCreatedById().toString());
      }
      if (dto.getInstrumentId() != null) {
         dto.setInstrumentUrl(((baseUriInstrument + dto.getInstrumentId().toString())));
      }
   }
}
