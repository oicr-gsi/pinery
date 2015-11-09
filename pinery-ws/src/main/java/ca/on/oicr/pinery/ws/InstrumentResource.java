package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
@Api(value = "/instrument", description = "Operations about instruments")
public class InstrumentResource {

   @Context
   private UriInfo uriInfo;

   @Autowired
   private InstrumentService instrumentService;

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodels")
   @ApiOperation(value = "List all instrument models", response = ca.on.oicr.ws.dto.InstrumentModelDto.class, responseContainer = "List")
   @ApiResponse(code = 404, message = "No instruments found")
   public List<InstrumentModelDto> getInstrumentModels() {
      List<InstrumentModel> instrumentModels = instrumentService.getInstrumentModels();
      if (instrumentModels.isEmpty()) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      List<InstrumentModelDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      final URI baseUserUri = uriInfo.getBaseUriBuilder().path("user/").build();
      for (InstrumentModel instrumentModel : instrumentModels) {
         InstrumentModelDto dto = Dtos.asDto(instrumentModel);
         dto.setUrl(baseUri + "/" + dto.getId().toString());
         dto.setInstrumentsUrl(baseUri + "/" + dto.getId().toString() + "/instruments");
         if (instrumentModel.getCreatedById() != null) {
            dto.setCreatedByUrl(baseUserUri + instrumentModel.getCreatedById().toString());
         }
         if (instrumentModel.getModifiedById() != null) {
            dto.setModifiedByUrl(baseUserUri + instrumentModel.getModifiedById().toString());
         }
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodel/{id}")
   @ApiOperation(value = "Find instrument model by ID", response = ca.on.oicr.ws.dto.InstrumentModelDto.class)
   @ApiResponses(value = {
       @ApiResponse(code = 400, message = "Invalid ID supplied"),
       @ApiResponse(code = 404, message = "Instrument not found")
   })
   public InstrumentModelDto getInstrumentModel(
         @ApiParam(value = "ID of instrument model that needs to be fetched", required = true) @PathParam("id") Integer id) {
      InstrumentModel instrumentModel = instrumentService.getInstrumentModel(id);
      if (instrumentModel == null) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      InstrumentModelDto dto = Dtos.asDto(instrumentModel);

      final URI uri = uriInfo.getAbsolutePathBuilder().build();
      final URI baseUserUri = uriInfo.getBaseUriBuilder().path("user/").build();
      dto.setUrl(uri.toString());
      dto.setInstrumentsUrl(uri + "/instruments");
      if (instrumentModel.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUserUri + instrumentModel.getCreatedById().toString());
      }
      if (instrumentModel.getModifiedById() != null) {
         dto.setModifiedByUrl(baseUserUri + instrumentModel.getModifiedById().toString());
      }
      return dto;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instruments")
   @ApiOperation(value = "List all instruments for a given instrument model ID", response = ca.on.oicr.ws.dto.InstrumentDto.class, responseContainer = "List")
   public List<InstrumentDto> getInstruments() {
      List<Instrument> instruments = instrumentService.getInstruments();

      if (instruments.isEmpty()) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      List<InstrumentDto> result = Lists.newArrayList();
      final URI baseUriInstrumentModel = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      final URI baseUri = uriInfo.getBaseUriBuilder().build();
      for (Instrument instrument : instruments) {
         InstrumentDto dto = Dtos.asDto(instrument);
         dto.setUrl(baseUri + "instrument/" + dto.getId().toString());
         dto.setInstrumentModel(baseUriInstrumentModel + "/" + dto.getInstrumentModel());
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodel/{id}/instruments")
   @ApiOperation(value = "List all instruments for a given instrument model ID", response = ca.on.oicr.ws.dto.InstrumentDto.class, responseContainer = "List")
   @ApiResponses(value = {
       @ApiResponse(code = 400, message = "Invalid ID supplied"),
       @ApiResponse(code = 404, message = "No instruments found")
   })
   public List<InstrumentDto> getInstrumentsModelInstrument(
         @ApiParam(value = "ID of instrument model", required = true) @PathParam("id") Integer id) {
      List<Instrument> instruments = instrumentService.getInstrumentModelInstrument(id);

      if (instruments.isEmpty()) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      List<InstrumentDto> result = Lists.newArrayList();
      final URI baseUriInstumentModel = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      final URI baseUri = uriInfo.getBaseUriBuilder().build();
      for (Instrument instrument : instruments) {
         InstrumentDto dto = Dtos.asDto(instrument);
         dto.setUrl(baseUri + "instrument/" + dto.getId().toString());
         dto.setInstrumentModel(baseUriInstumentModel + "/" + id);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrument/{id}")
   @ApiOperation(value = "Fine instrument by ID", response = ca.on.oicr.ws.dto.InstrumentDto.class)
   @ApiResponses(value = {
       @ApiResponse(code = 400, message = "Invalid ID supplied"),
       @ApiResponse(code = 404, message = "No instrument found")
   })
   public InstrumentDto getInstrument(@ApiParam(value = "ID of instrument", required = true) @PathParam("id") Integer instrumentId) {
      Instrument instrument = instrumentService.getInstrument(instrumentId);
      if (instrument == null) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }

      InstrumentDto dto = Dtos.asDto(instrument);

      final URI uri = uriInfo.getBaseUriBuilder().build();
      final URI uriInstrumentModel = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      dto.setUrl(uri + "instrument/" + dto.getId().toString());
      dto.setInstrumentModel(uriInstrumentModel + "/" + dto.getInstrumentModel());
      return dto;
   }
}
