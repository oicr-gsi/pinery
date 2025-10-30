package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.service.RunService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoContainer;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.google.common.collect.Lists;
import java.net.URI;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sequencer Runs")
public class RunResource {

  @Autowired
  private RunService runService;

  void setRunService(RunService runService) {
    this.runService = runService;
  }

  @GetMapping("/sequencerruns")
  @Operation(summary = "List all sequencer runs")
  public List<RunDto> getRuns(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "filter by sampleId(s)") @RequestParam(name = "sampleId", required = false) Set<String> sampleIds) {
    List<Run> runs = runService.getAll(sampleIds);
    List<RunDto> result = Lists.newArrayList();
    for (Run run : runs) {
      RunDto dto = Dtos.asDto(run);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/sequencerrun/{id}")
  @Operation(summary = "Find sequencer run by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No sequencer run found", content = @Content)
  })
  public RunDto getRun(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of sequencer run to fetch") @PathVariable("id") Integer id) {
    Run run = runService.getRun(id);
    if (run == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No run found with ID: " + id);
    }
    RunDto dto = Dtos.asDto(run);
    addUrls(dto, uriBuilder);

    return dto;
  }

  @GetMapping("/sequencerrun")
  @Operation(summary = "Find sequencer run by name")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Missing or invalid name parameter", content = @Content),
      @ApiResponse(responseCode = "404", description = "No sequencer run found", content = @Content)
  })
  public RunDto getRunByName(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "Name of sequencer run to fetch") @RequestParam("name") String runName) {
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

    if (dto.getContainers() != null) {
      for (RunDtoContainer container : dto.getContainers()) {
        if (container.getPositions() != null) {
          for (RunDtoPosition runDtoPosition : container.getPositions()) {
            if (runDtoPosition.getSamples() != null && !runDtoPosition.getSamples().isEmpty()) {
              for (RunDtoSample runDtoSample : runDtoPosition.getSamples()) {
                runDtoSample.setUrl(buildSampleUrl(baseUri, runDtoSample.getId()));
              }
            }
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
