package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.service.InstrumentService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.InstrumentDto;
import ca.on.oicr.ws.dto.InstrumentModelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.google.common.collect.Lists;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Instruments")
public class InstrumentResource {

  @Autowired
  private InstrumentService instrumentService;

  @GetMapping("/instrumentmodels")
  @Operation(summary = "List all instrument models")
  public List<InstrumentModelDto> getInstrumentModels(UriComponentsBuilder uriBuilder) {
    List<InstrumentModel> instrumentModels = instrumentService.getInstrumentModels();
    List<InstrumentModelDto> result = Lists.newArrayList();
    for (InstrumentModel instrumentModel : instrumentModels) {
      InstrumentModelDto dto = Dtos.asDto(instrumentModel);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/instrumentmodel/{id}")
  @Operation(summary = "Find instrument model by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "Instrument model not found", content = @Content)
  })
  public InstrumentModelDto getInstrumentModel(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of instrument model to fetch") @PathVariable("id") Integer id) {
    InstrumentModel instrumentModel = instrumentService.getInstrumentModel(id);
    if (instrumentModel == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "Instrument model not found");
    }
    InstrumentModelDto dto = Dtos.asDto(instrumentModel);
    addUrls(dto, uriBuilder);
    return dto;
  }

  @GetMapping("/instruments")
  @Operation(summary = "List all instruments")
  public List<InstrumentDto> getInstruments(UriComponentsBuilder uriBuilder) {
    List<Instrument> instruments = instrumentService.getInstruments();
    List<InstrumentDto> result = Lists.newArrayList();
    for (Instrument instrument : instruments) {
      InstrumentDto dto = Dtos.asDto(instrument);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/instrumentmodel/{id}/instruments")
  @Operation(summary = "List all instruments for a given instrument model ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No instruments found", content = @Content)
  })
  public List<InstrumentDto> getInstrumentsModelInstrument(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of instrument model to fetch instruments for") @PathVariable("id") Integer id) {
    List<Instrument> instruments = instrumentService.getInstrumentModelInstrument(id);
    List<InstrumentDto> result = Lists.newArrayList();
    for (Instrument instrument : instruments) {
      InstrumentDto dto = Dtos.asDto(instrument);
      addUrls(dto, uriBuilder);
      result.add(dto);
    }
    return result;
  }

  @GetMapping("/instrument/{id}")
  @Operation(summary = "Find instrument by ID")
  @ApiResponses({
      @ApiResponse(useReturnTypeSchema = true, responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "No instrument found", content = @Content)
  })
  public InstrumentDto getInstrument(
      UriComponentsBuilder uriBuilder,
      @Parameter(description = "ID of instrument to fetch") @PathVariable("id") Integer instrumentId) {
    Instrument instrument = instrumentService.getInstrument(instrumentId);
    if (instrument == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "No instrument found with ID: " + instrumentId);
    }

    InstrumentDto dto = Dtos.asDto(instrument);
    addUrls(dto, uriBuilder);
    return dto;
  }

  private void addUrls(InstrumentDto dto, UriComponentsBuilder uriBuilder) {
    URI baseUri = getBaseUri(uriBuilder);
    dto.setUrl(buildInstrumentUrl(baseUri, dto.getId()));
    dto.setModelUrl(buildInstrumentModelUrl(baseUri, dto.getModelId()));
  }

  private void addUrls(InstrumentModelDto dto, UriComponentsBuilder uriBuilder) {
    URI baseUrl = getBaseUri(uriBuilder);
    dto.setUrl(buildInstrumentModelUrl(baseUrl, dto.getId()));
    dto.setInstrumentsUrl(buildModelInstrumentsUrl(baseUrl, dto.getId()));
    if (dto.getCreatedById() != null) {
      dto.setCreatedByUrl(buildUserUrl(baseUrl, dto.getCreatedById()));
    }
    if (dto.getModifiedById() != null) {
      dto.setModifiedByUrl(buildUserUrl(baseUrl, dto.getModifiedById()));
    }
  }
}
