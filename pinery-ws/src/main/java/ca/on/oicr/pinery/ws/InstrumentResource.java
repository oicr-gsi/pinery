package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.service.InstrumentService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.InstrumentDto;
import ca.on.oicr.ws.dto.InstrumentModelDto;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Instruments"})
public class InstrumentResource {

  @Autowired private InstrumentService instrumentService;

  @GetMapping("/instrumentmodels")
  @ApiOperation(
      value = "List all instrument models",
      response = ca.on.oicr.ws.dto.InstrumentModelDto.class,
      responseContainer = "List")
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
  @ApiOperation(
      value = "Find instrument model by ID",
      response = ca.on.oicr.ws.dto.InstrumentModelDto.class)
  @ApiResponses({@ApiResponse(code = 404, message = "Instrument model not found")})
  public InstrumentModelDto getInstrumentModel(
      UriComponentsBuilder uriBuilder,
      @ApiParam(value = "ID of instrument model to fetch") @PathVariable("id") Integer id) {
    InstrumentModel instrumentModel = instrumentService.getInstrumentModel(id);
    if (instrumentModel == null) {
      throw new RestException(HttpStatus.NOT_FOUND, "Instrument model not found");
    }
    InstrumentModelDto dto = Dtos.asDto(instrumentModel);
    addUrls(dto, uriBuilder);
    return dto;
  }

  @GetMapping("/instruments")
  @ApiOperation(
      value = "List all instruments",
      response = ca.on.oicr.ws.dto.InstrumentDto.class,
      responseContainer = "List")
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
  @ApiOperation(
      value = "List all instruments for a given instrument model ID",
      response = ca.on.oicr.ws.dto.InstrumentDto.class,
      responseContainer = "List")
  @ApiResponses({@ApiResponse(code = 404, message = "No instruments found")})
  public List<InstrumentDto> getInstrumentsModelInstrument(
      UriComponentsBuilder uriBuilder,
      @ApiParam(value = "ID of instrument model to fetch instruments for") @PathVariable("id")
          Integer id) {
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
  @ApiOperation(value = "Find instrument by ID", response = ca.on.oicr.ws.dto.InstrumentDto.class)
  @ApiResponses({@ApiResponse(code = 404, message = "No instrument found")})
  public InstrumentDto getInstrument(
      UriComponentsBuilder uriBuilder,
      @ApiParam(value = "ID of instrument to fetch") @PathVariable("id") Integer instrumentId) {
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
