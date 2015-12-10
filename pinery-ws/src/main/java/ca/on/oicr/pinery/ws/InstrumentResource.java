package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.service.InstrumentService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.InstrumentDto;
import ca.on.oicr.ws.dto.InstrumentModelDto;

import com.google.common.collect.Lists;

@Component
@Path("/")
@Api(value = "instrument")
public class InstrumentResource {

   @Context
   private UriInfo uriInfo;

   @Autowired
   private InstrumentService instrumentService;

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodels")
   @ApiOperation(value = "List all instrument models", response = ca.on.oicr.ws.dto.InstrumentModelDto.class, responseContainer = "List")
   public List<InstrumentModelDto> getInstrumentModels() {
      List<InstrumentModel> instrumentModels = instrumentService.getInstrumentModels();
      List<InstrumentModelDto> result = Lists.newArrayList();
      for (InstrumentModel instrumentModel : instrumentModels) {
         InstrumentModelDto dto = Dtos.asDto(instrumentModel);
         addUrls(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodel/{id}")
   @ApiOperation(value = "Find instrument model by ID", response = ca.on.oicr.ws.dto.InstrumentModelDto.class)
   @ApiResponse(code = 404, message = "Instrument model not found")
   public InstrumentModelDto getInstrumentModel(
         @ApiParam(value = "ID of instrument model to fetch", required = true) @PathParam("id") Integer id) {
      InstrumentModel instrumentModel = instrumentService.getInstrumentModel(id);
      if (instrumentModel == null) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      InstrumentModelDto dto = Dtos.asDto(instrumentModel);
      addUrls(dto);
      return dto;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instruments")
   @ApiOperation(value = "List all instruments", response = ca.on.oicr.ws.dto.InstrumentDto.class, responseContainer = "List")
   public List<InstrumentDto> getInstruments() {
      List<Instrument> instruments = instrumentService.getInstruments();
      List<InstrumentDto> result = Lists.newArrayList();
      for (Instrument instrument : instruments) {
         InstrumentDto dto = Dtos.asDto(instrument);
         addUrls(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodel/{id}/instruments")
   @ApiOperation(value = "List all instruments for a given instrument model ID", response = ca.on.oicr.ws.dto.InstrumentDto.class, responseContainer = "List")
   @ApiResponse(code = 404, message = "No instruments found")
   public List<InstrumentDto> getInstrumentsModelInstrument(
         @ApiParam(value = "ID of instrument model to fetch instruments for", required = true) @PathParam("id") Integer id) {
      List<Instrument> instruments = instrumentService.getInstrumentModelInstrument(id);
      List<InstrumentDto> result = Lists.newArrayList();
      for (Instrument instrument : instruments) {
         InstrumentDto dto = Dtos.asDto(instrument);
         addUrls(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrument/{id}")
   @ApiOperation(value = "Find instrument by ID", response = ca.on.oicr.ws.dto.InstrumentDto.class)
   @ApiResponse(code = 404, message = "No instrument found")
   public InstrumentDto getInstrument(@ApiParam(value = "ID of instrument to fetch", required = true) @PathParam("id") Integer instrumentId) {
      Instrument instrument = instrumentService.getInstrument(instrumentId);
      if (instrument == null) {
         throw new NotFoundException("No instrument found with ID: " + instrumentId);
      }

      InstrumentDto dto = Dtos.asDto(instrument);
      addUrls(dto);
      return dto;
   }
   
   private void addUrls(InstrumentDto dto) {
     final URI uri = uriInfo.getBaseUriBuilder().build();
     final URI uriInstrumentModel = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
     dto.setUrl(uri + "instrument/" + dto.getId().toString());
     dto.setModelUrl(uriInstrumentModel + "/" + dto.getModelId());
   }
   
   private void addUrls(InstrumentModelDto dto) {
     final URI uri = uriInfo.getBaseUriBuilder().path("instrumentmodel/").build();
     final URI baseUserUri = uriInfo.getBaseUriBuilder().path("user/").build();
     dto.setUrl(uri + dto.getId().toString());
     dto.setInstrumentsUrl(uri + dto.getId().toString() + "/instruments");
     if (dto.getCreatedById() != null) {
        dto.setCreatedByUrl(baseUserUri + dto.getCreatedById().toString());
     }
     if (dto.getModifiedById() != null) {
        dto.setModifiedByUrl(baseUserUri + dto.getModifiedById().toString());
     }
   }
   
}
