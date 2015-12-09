package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.service.SampleService;
import ca.on.oicr.ws.dto.AttributeNameDto;
import ca.on.oicr.ws.dto.ChangeDto;
import ca.on.oicr.ws.dto.ChangeLogDto;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleDto;
import ca.on.oicr.ws.dto.SampleProjectDto;
import ca.on.oicr.ws.dto.SampleReferenceDto;
import ca.on.oicr.ws.dto.TypeDto;

import com.google.common.collect.Lists;

@Component
@Path("/")
@Api(value = "sample")
public class SampleResource {

//   private static final Logger log = LoggerFactory.getLogger(SampleResource.class);

   @Context
   private UriInfo uriInfo;

   @Autowired
   private SampleService sampleService;

   @GET
   @Produces({ "application/json" })
   @Path("/samples")
   @ApiOperation(value = "List all samples", response = SampleDto.class, responseContainer = "List")
   @ApiResponse(code = 400, message = "Invalid parameter")
   public List<SampleDto> getSamples(
         @ApiParam(value = "filter by archived status", required = false) @QueryParam("archived") Boolean archived,
         @ApiParam(value = "filter by project(s)", required = false, allowMultiple = true) @QueryParam("project") Set<String> projects,
         @ApiParam(value = "filter by sample type(s)", required = false, allowMultiple = true) @QueryParam("type") Set<String> types,
         @ApiParam(value = "filter to include samples created before this date", required = false) @QueryParam("before") String before,
         @ApiParam(value = "filter to include samples created after this date", required = false) @QueryParam("after") String after) {
      DateTime beforeDateTime = null;
      DateTime afterDateTime = null;
      try {
         if (before != null && !before.equals("")) {
            beforeDateTime = DateTime.parse(before);
         }
         if (after != null && !after.equals("")) {
            afterDateTime = DateTime.parse(after);
         }
      } catch (IllegalArgumentException e) {
         throw new BadRequestException("Invalid date format in parameter [before] or [after]. Use ISO8601 formatting. " + e.getMessage(), e);
      }
      List<Sample> samples = sampleService.getSamples(archived, projects, types, beforeDateTime, afterDateTime);
      List<SampleDto> result = Lists.newArrayList();
      
      for (Sample sample : samples) {
         SampleDto dto = Dtos.asDto(sample);
         addUrls(dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/{id}")
   @ApiOperation(value = "Find sample by ID", response = SampleDto.class)
   @ApiResponse(code = 404, message = "No sample found")
   public SampleDto getSample(@ApiParam(value = "ID of sample to fetch", required = true) @PathParam("id") Integer id) {
      Sample sample = sampleService.getSample(id);
      if (sample == null) {
        throw new NotFoundException("No sample found with ID: " + id);
      }
      SampleDto dto = Dtos.asDto(sample);
      addUrls(dto);
      return dto;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/projects")
   @ApiOperation(value = "List all projects", response = SampleProjectDto.class, responseContainer = "List")
   public List<SampleProjectDto> getSampleProjects() {
      List<SampleProject> projects = sampleService.getSampleProjects();
      List<SampleProjectDto> result = Lists.newArrayList();
      for (SampleProject sampleProject : projects) {
         result.add(Dtos.asDto(sampleProject));
      }
      Collections.sort(result, new Comparator<SampleProjectDto>() {

         @Override
         public int compare(SampleProjectDto o1, SampleProjectDto o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/types")
   @ApiOperation(value = "List all sample types", response = TypeDto.class, responseContainer = "List")
   public List<TypeDto> getTypes() {
      List<Type> types = sampleService.getTypes();
      List<TypeDto> result = Lists.newArrayList();
      for (Type type : types) {
         result.add(Dtos.asDto(type));
      }
      Collections.sort(result, new Comparator<TypeDto>() {

         @Override
         public int compare(TypeDto o1, TypeDto o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/attributenames")
   @ApiOperation(value = "List all sample attribute names", response = AttributeNameDto.class, responseContainer = "List")
   public List<AttributeNameDto> getAttributeNames() {
      List<AttributeName> attributeNames = sampleService.getAttributeNames();
      List<AttributeNameDto> result = Lists.newArrayList();
      for (AttributeName attributeName : attributeNames) {
         result.add(Dtos.asDto(attributeName));
      }
      Collections.sort(result, new Comparator<AttributeNameDto>() {

         @Override
         public int compare(AttributeNameDto o1, AttributeNameDto o2) {
            return o1.getName().compareTo(o2.getName());
         }
      });
      return result;
   }

   private void addUrls(SampleDto dto) {
      final URI baseUri = uriInfo.getBaseUriBuilder().path("sample").build();
      final URI baseUriUser = uriInfo.getBaseUriBuilder().path("user/").build();
      
      dto.setUrl(baseUri + "/" + dto.getId().toString());
      
      if (dto.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUriUser + dto.getCreatedById().toString());
      }
      if (dto.getModifiedById() != null) {
         dto.setModifiedByUrl(baseUriUser + dto.getModifiedById().toString());
      }
      
      addUrls(dto.getParents());
      addUrls(dto.getChildren());
   }
   
   private void addUrls(Set<SampleReferenceDto> relatedSamples) {
     if (relatedSamples != null) {
        final URI baseUriSample = uriInfo.getBaseUriBuilder().path("sample").build();
        for (SampleReferenceDto sample : relatedSamples) {
           sample.setUrl(baseUriSample + "/" + sample.getId());
        }
     }
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/changelogs")
   @ApiOperation(value = "List changelogs for all samples", response = ChangeLogDto.class, responseContainer = "List")
   public List<ChangeLogDto> getChangeLogs() {
      List<ChangeLog> changeLogs = sampleService.getChangeLogs();
      List<ChangeLogDto> result = Lists.newArrayList();

      final URI baseUri = uriInfo.getBaseUriBuilder().path("sample/").build();
      for (ChangeLog changeLog : changeLogs) {
         ChangeLogDto dto = Dtos.asDto(changeLog);
         dto.setSampleUrl(baseUri + changeLog.getSampleId().toString());
         for (ChangeDto change : dto.getChanges()) {
            addUrls(change);
         }
         Collections.sort(dto.getChanges(), new Comparator<ChangeDto>() {

            @Override
            public int compare(ChangeDto o1, ChangeDto o2) {
               return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }

         });
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/sample/{id}/changelog")
   @ApiOperation(value = "Find sample changelog by sample ID", response = ChangeLogDto.class)
   @ApiResponse(code = 404, message = "No sample changelog found")
   public ChangeLogDto getChangeLog(@ApiParam(value = "ID of sample to fetch changelogs for", required = true) @PathParam("id") Integer id) {
      ChangeLog changeLog = sampleService.getChangeLog(id);
      if (changeLog == null) {
        throw new NotFoundException("No changelog found for sample ID: " + id);
      }
      ChangeLogDto dto = Dtos.asDto(changeLog);
      final URI uri = uriInfo.getBaseUriBuilder().path("sample").path(changeLog.getSampleId().toString()).build();
      dto.setSampleUrl(uri.toString());
      for (ChangeDto change : dto.getChanges()) {
         addUrls(change);
      }
      Collections.sort(dto.getChanges(), new Comparator<ChangeDto>() {

         @Override
         public int compare(ChangeDto o1, ChangeDto o2) {
            return o1.getCreatedDate().compareTo(o2.getCreatedDate());
         }

      });
      return dto;
   }

   private void addUrls(ChangeDto dto) {
      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
      if (dto.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUri + dto.getCreatedById().toString());
      }
   }
   
}
