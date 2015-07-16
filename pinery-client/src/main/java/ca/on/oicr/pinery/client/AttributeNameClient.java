package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.AttributeNameDto;


public class AttributeNameClient extends ResourceClient<AttributeNameDto> {

	public AttributeNameClient(PineryClient mainClient) {
		super(AttributeNameDto.class, AttributeNameDto[].class, mainClient);
	}
	
	public List<AttributeNameDto> all() {
		return getResourceList("sample/attributenames");
	}

}
