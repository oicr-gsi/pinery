package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.InstrumentDto;

public class InstrumentClient extends ResourceClient<InstrumentDto> {

	public InstrumentClient(PineryClient mainClient) {
		super(InstrumentDto.class, InstrumentDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all instruments in the database
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<InstrumentDto> all() throws HttpResponseException {
		return getResourceList("instruments");
	}
	
	/**
	 * Retrieves all instruments of the specified model
	 * 
	 * @param instrumentModelId LIMS instrument model ID to look for
	 * @return a list of all matching instruments
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<InstrumentDto> byModel(int instrumentModelId) throws HttpResponseException {
		return getResourceList("instrumentmodel/"+instrumentModelId+"/instruments");
	}
	
	/**
	 * Retrieves a single instrument by ID
	 * 
	 * @param instrumentId LIMS ID of the instrument to retrieve
	 * @return
	 * @throws HttpResponseException
	 */
	public InstrumentDto byId(int instrumentId) throws HttpResponseException {
		return getResource("instrument/"+instrumentId);
	}

}
