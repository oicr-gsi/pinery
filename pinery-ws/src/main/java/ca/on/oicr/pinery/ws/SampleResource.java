package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.service.SampleService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.AttributeNameDto;
import ca.on.oicr.ws.dto.ChangeDto;
import ca.on.oicr.ws.dto.ChangeLogDto;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleDto;
import ca.on.oicr.ws.dto.SampleProjectDto;
import ca.on.oicr.ws.dto.SampleReferenceDto;
import ca.on.oicr.ws.dto.TypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.google.common.collect.Lists;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
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
@Tag(name = "Samples")
public class SampleResource {

  @Autowired
  private SampleService sampleService;

  @GetMapping(value = "/samples")
  @Operation(summary = "List all samples")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Invalid parameter", content = @Content)
  })
  public List<SampleDto> getSamples(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "filter by archived status") @RequestParam(name = "archived", required = false) Boolean archived,
      @Parameter(description = "filter by project(s)") @RequestParam(name = "project", required = false) Set<String> projects,
      @Parameter(description = "filter by sample type(s)") @RequestParam(name = "type", required = false) Set<String> types,
      @Parameter(description = "filter to include samples created before this date", example = "yyyy-mm-dd") @RequestParam(name = "before", required = false) String before,
      @Parameter(description = "filter to include samples created after this date", example = "yyyy-mm-dd") @RequestParam(name = "after", required = false) String after) {
    ZonedDateTime beforeDateTime = null;
    ZonedDateTime afterDateTime = null;
    try {
      if (before != null && !before.equals("")) {
        beforeDateTime = ZonedDateTime.parse(before);
      }
      if (after != null && !after.equals("")) {
        afterDateTime = ZonedDateTime.parse(after);
      }
    } catch (IllegalArgumentException e) {
      throw new RestException(
          HttpStatus.BAD_REQUEST,
          "Invalid date format in parameter [before] or [after]. Use ISO8601 formatting. "
              + e.getMessage());
    }
    List<Sample> samples = sampleService.getSamples(archived, projects, types, beforeDateTime, afterDateTime);
    List<SampleDto> result = Lists.newArrayList();

    for (Sample sample : samples) {
      SampleDto dto = Dtos.asDto(sample);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/sample/{id}")
  @Operation(summary = "Find sample by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No sample found", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid ID format", content = @Content)
  })
  public SampleDto getSample(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of sample to fetch") @PathVariable("id") String id) {
    Sample sample = null;
    try {
      sample = sampleService.getSample(id);
    } catch (IllegalArgumentException e) {
      throw new RestException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    if (sample == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No sample found with ID: " + id);
    }
    SampleDto dto = Dtos.asDto(sample);
    addUrls(dto, uriBuilder);
    return dto;
  }

  @GetMapping("/sample/projects")
  @Operation(summary = "List all projects")
  public List<SampleProjectDto> getSampleProjects() {
    List<SampleProject> projects = sampleService.getSampleProjects();
    List<SampleProjectDto> result = Lists.newArrayList();
    for (SampleProject sampleProject : projects) {
      result.add(Dtos.asDto(sampleProject));
    }
    Collections.sort(
        result,
        new Comparator<SampleProjectDto>() {

          @Override
          public int compare(SampleProjectDto o1, SampleProjectDto o2) {
            return o1.getName().compareTo(o2.getName());
          }
        });
    return result;
  }

  @GetMapping("/sample/types")
  @Operation(summary = "List all sample types")
  public List<TypeDto> getTypes() {
    List<Type> types = sampleService.getTypes();
    List<TypeDto> result = Lists.newArrayList();
    for (Type type : types) {
      result.add(Dtos.asDto(type));
    }
    Collections.sort(
        result,
        new Comparator<TypeDto>() {

          @Override
          public int compare(TypeDto o1, TypeDto o2) {
            return o1.getName().compareTo(o2.getName());
          }
        });
    return result;
  }

  @GetMapping("/sample/attributenames")
  @Operation(summary = "List all sample attribute names")
  public List<AttributeNameDto> getAttributeNames() {
    List<AttributeName> attributeNames = sampleService.getAttributeNames();
    List<AttributeNameDto> result = Lists.newArrayList();
    for (AttributeName attributeName : attributeNames) {
      result.add(Dtos.asDto(attributeName));
    }
    Collections.sort(
        result,
        new Comparator<AttributeNameDto>() {

          @Override
          public int compare(AttributeNameDto o1, AttributeNameDto o2) {
            return o1.getName().compareTo(o2.getName());
          }
        });
    return result;
  }

  private void addUrls(SampleDto dto, UriComponentsBuilder uriBuilder) {
    URI baseUri = uriBuilder.build().toUri();
    dto.setUrl(buildSampleUrl(baseUri, dto.getId()));

    if (dto.getCreatedById() != null) {
      dto.setCreatedByUrl(buildUserUrl(baseUri, dto.getCreatedById()));
    }
    if (dto.getModifiedById() != null) {
      dto.setModifiedByUrl(buildUserUrl(baseUri, dto.getModifiedById()));
    }

    addUrls(dto.getParents(), baseUri);
    addUrls(dto.getChildren(), baseUri);
  }

  private void addUrls(Set<SampleReferenceDto> relatedSamples, URI baseUri) {
    if (relatedSamples != null) {
      for (SampleReferenceDto sample : relatedSamples) {
        sample.setUrl(buildSampleUrl(baseUri, sample.getId()));
      }
    }
  }

  @GetMapping("/sample/changelogs")
  @Operation(summary = "List changelogs for all samples")
  public List<ChangeLogDto> getChangeLogs(UriComponentsBuilder uriBuilder) {
    List<ChangeLog> changeLogs = sampleService.getChangeLogs();
    List<ChangeLogDto> result = Lists.newArrayList();

    for (ChangeLog changeLog : changeLogs) {
      ChangeLogDto dto = Dtos.asDto(changeLog);
      addUrls(dto, uriBuilder);
      Collections.sort(
          dto.getChanges(), (o1, o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()));
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/sample/{id}/changelog")
  @Operation(summary = "Find sample changelog by sample ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No sample changelog found", content = @Content)
  })
  public ChangeLogDto getChangeLog(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of sample to fetch changelogs for") @PathVariable("id") String id) {
    ChangeLog changeLog = null;
    try {
      changeLog = sampleService.getChangeLog(id);
    } catch (IllegalArgumentException e) {
      throw new RestException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    if (changeLog == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No changelog found for sample ID: " + id);
    }
    ChangeLogDto dto = Dtos.asDto(changeLog);
    addUrls(dto, uriBuilder);
    Collections.sort(
        dto.getChanges(), (o1, o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()));
    return dto;
  }

  private void addUrls(ChangeLogDto dto, UriComponentsBuilder uriBuilder) {
    URI baseUri = uriBuilder.build().toUri();
    dto.setSampleUrl(buildSampleUrl(baseUri, dto.getSampleId()));
    for (ChangeDto change : dto.getChanges()) {
      if (change.getCreatedById() != null) {
        change.setCreatedByUrl(buildUserUrl(baseUri, change.getCreatedById()));
      }
    }
  }
}
