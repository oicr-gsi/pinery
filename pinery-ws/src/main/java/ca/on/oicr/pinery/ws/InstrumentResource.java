package ca.on.oicr.pinery.ws;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.NotFoundException;
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
public class InstrumentResource {

   @Context
   private UriInfo uriInfo;

   @Autowired
   private InstrumentService instrumentService;

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodels")
   public List<InstrumentModelDto> getInstrumentModels() {
      List<InstrumentModel> instrumentModels = instrumentService.getInstrumentModels();
      if (instrumentModels.isEmpty()) { throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build()); }
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
   public InstrumentModelDto getInstrumentModel(@PathParam("id") Integer id) {
      InstrumentModel instrumentModel = instrumentService.getInstrumentModel(id);
      if (instrumentModel == null) { throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build()); }
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
   @Path("/instrumentmodel/{id}/instruments")
   public List<InstrumentDto> getInstruments(@PathParam("id") Integer id) {
      List<Instrument> instruments = instrumentService.getInstruments(id);

      if (instruments.isEmpty()) { throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build()); }
      List<InstrumentDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      for (Instrument instrument : instruments) {
         InstrumentDto dto = Dtos.asDto(instrument);
         dto.setUrl(baseUri + "/" + id + "/instrument/" + dto.getId().toString());
         dto.setInstrumentModel(baseUri + "/" + id);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/instrumentmodel/{model_id}/instrument/{id}")
   public InstrumentDto getInstrument(@PathParam("model_id") Integer instrumentModelId, @PathParam("id") Integer instrumentId) {
      Instrument instrument = instrumentService.getInstrument(instrumentModelId, instrumentId);
      if (instrument == null) { throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build()); }

      InstrumentDto dto = Dtos.asDto(instrument);

      final URI uri = uriInfo.getBaseUriBuilder().path("instrumentmodel").build();
      dto.setUrl(uri + "/" + instrumentModelId + "/instrument/" + dto.getId().toString());
      dto.setInstrumentModel(uri + "/" + instrumentModelId);
      return dto;
   }
}
