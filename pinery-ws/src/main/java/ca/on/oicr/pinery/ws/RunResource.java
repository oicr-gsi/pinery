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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RunResource {

   private static final Logger log = LoggerFactory.getLogger(RunResource.class);

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
   public List<RunDto> getRuns() {
      List<Run> runs = runService.getRun();
      if (runs.isEmpty()) {
         throw new NotFoundException("", Response.noContent().status(Status.NOT_FOUND).build());
      }
      List<RunDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sequencerrun").build();
      for (Run run : runs) {
         RunDto dto = Dtos.asDto(run);
         dto.setUrl(baseUri + "/" + dto.getId().toString());
         addUser(run, dto);
         addSampleUrl(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sequencerrun/{id}")
   public RunDto getRun(@PathParam("id") Integer id) {

      Run run = runService.getRun(id);
      RunDto dto = Dtos.asDto(run);
      final URI uri = uriInfo.getAbsolutePathBuilder().build();
      dto.setUrl(uri.toString());
      addUser(run, dto);
      addSampleUrl(dto);

      return dto;
   }

   private RunDto addSampleUrl(RunDto dto) {
      final URI baseUriSample = uriInfo.getBaseUriBuilder().path("sample/").build();

      if (dto.getPositions() != null) {
         for (RunDtoPosition runDtoPosition : dto.getPositions()) {
            for (RunDtoSample runDtoSample : runDtoPosition.getSamples()) {
               runDtoSample.setUrl(baseUriSample + runDtoSample.getId().toString());
            }
         }
      }
      return dto;
   }

   private void addUser(Run run, RunDto dto) {

      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      final URI baseUriInstrument = uriInfo.getBaseUriBuilder().path("instrument/").build();

      if (run.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUri + run.getCreatedById().toString());
      }

      if (run.getInstrumentId() != null) {
         dto.setInstrument_Url(((baseUriInstrument + run.getInstrumentId().toString())));
      }
   }
}
