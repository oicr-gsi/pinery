package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.SampleProjectDto;

public class SampleProjectClient extends ResourceClient<SampleProjectDto> {
	
	public SampleProjectClient(PineryClient mainClient) {
		super(SampleProjectDto.class, SampleProjectDto[].class, mainClient);
	}
	
	public List<SampleProjectDto> all() {
		return getResourceList("sample/projects");
	}
	
}
