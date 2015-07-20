package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.TypeDto;

public class SampleTypeClient extends ResourceClient<TypeDto> {

	public SampleTypeClient(PineryClient mainClient) {
		super(TypeDto.class, TypeDto[].class, mainClient);
	}
	
	public List<TypeDto> all() throws HttpResponseException {
		return getResourceList("sample/types");
	}
	
}
