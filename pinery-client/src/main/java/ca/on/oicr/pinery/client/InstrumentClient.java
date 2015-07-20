package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.InstrumentDto;

public class InstrumentClient extends ResourceClient<InstrumentDto> {

	public InstrumentClient(PineryClient mainClient) {
		super(InstrumentDto.class, InstrumentDto[].class, mainClient);
	}
	
	public List<InstrumentDto> all() throws HttpResponseException {
		return getResourceList("instruments");
	}
	
	public List<InstrumentDto> byModel(int instrumentModelId) throws HttpResponseException {
		return getResourceList("instrumentmodel/"+instrumentModelId+"/instruments");
	}
	
	public InstrumentDto byId(int instrumentId) throws HttpResponseException {
		return getResource("instrument/"+instrumentId);
	}

}
