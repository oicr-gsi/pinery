package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.InstrumentModelDto;

public class InstrumentModelClient extends ResourceClient<InstrumentModelDto> {

	public InstrumentModelClient(PineryClient mainClient) {
		super(InstrumentModelDto.class, InstrumentModelDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all instrument models in the database
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<InstrumentModelDto> all() throws HttpResponseException {
		return getResourceList("instrumentmodels");
	}
	
	/**
	 * Retrieves a single instrument model by ID
	 * 
	 * @param instrumentModelId LIMS ID of the instrument model to retrieve
	 * @return the instrument model
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public InstrumentModelDto byId(int instrumentModelId) throws HttpResponseException {
		return getResource("instrumentmodel/"+instrumentModelId);
	}

}
