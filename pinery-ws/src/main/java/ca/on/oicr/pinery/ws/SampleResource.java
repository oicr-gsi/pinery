package ca.on.oicr.pinery.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.service.SampleService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.SampleDto;

import com.google.common.collect.Lists;

@Component
@Path("/")
public class SampleResource {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private SampleService sampleService;

	@GET
	@Produces({ "application/json" })
	@Path("/samples")
	public List<SampleDto> getFiles() {
		System.out.println("Hello");
		List<Sample> samples = sampleService.getSamples();
		List<SampleDto> result = Lists.newArrayList();
		for(Sample sample : samples) {
			result.add(Dtos.asDto(sample));
		}
//		List<FsFile> fsFiles = fsFileService.getFsFiles();
//		if (fsFiles.isEmpty()) {
//			throw new NotFoundException("", Response.noContent()
//					.status(Status.NOT_FOUND).build());
//		}
//		List<FsFileDto> result = Lists.newArrayList();
//		for (FsFile fsFile : fsFiles) {
//			FsFileDto.Builder builder = Dtos.asDto(fsFile);
//			final URI uri = uriInfo.getBaseUriBuilder().path("file")
//					.path(fsFile.getFsFileId().toString()).build();
//			builder.setUrl(uri.toString());
//			result.add(builder.build());
//		}
		return result;
	}
	
	@GET
	@Produces({ "application/json" })
	@Path("/sample/{id}")
	public SampleDto getFile(@PathParam("id") Integer id) {
		Sample sample = sampleService.getSample(id);
		return Dtos.asDto(sample);
//		FsFile fsFile = fsFileService.getFsFile(id);
//		if (fsFile == null) {
//			throw new NotFoundException("", Response.noContent()
//					.status(Status.NOT_FOUND).build());
//		}
//		FsFileDto.Builder builder = Dtos.asDto(fsFile);
//		final URI uri = uriInfo.getAbsolutePathBuilder().build();
//		builder.setUrl(uri.toString());
//		return builder.build();
	}
	
}
