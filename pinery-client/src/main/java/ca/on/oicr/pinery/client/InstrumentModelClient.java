package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.InstrumentModelDto;

public class InstrumentModelClient extends ResourceClient<InstrumentModelDto> {

	public InstrumentModelClient(PineryClient mainClient) {
		super(InstrumentModelDto.class, InstrumentModelDto[].class, mainClient);
	}
	
	public List<InstrumentModelDto> all() throws HttpResponseException {
		return getResourceList("instrumentmodels");
	}
	
	public InstrumentModelDto byId(int instrumentModelId) throws HttpResponseException {
		return getResource("instrumentmodel/"+instrumentModelId);
	}

}
