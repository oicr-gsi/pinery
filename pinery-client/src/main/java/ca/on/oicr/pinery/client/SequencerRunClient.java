package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.RunDto;

public class SequencerRunClient extends ResourceClient<RunDto> {

	public SequencerRunClient(PineryClient mainClient) {
		super(RunDto.class, RunDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all sequencer runs in the database
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<RunDto> all() throws HttpResponseException {
		return getResourceList("sequencerruns");
	}
	
	/**
	 * Retrieves a single sequencer run by ID
	 * 
	 * @param runId LIMS ID of the sequencer run to retrieve
	 * @return the sequencer run
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public RunDto byId(int runId) throws HttpResponseException {
		return getResource("sequencerrun/"+runId);
	}
	
	/**
	 * Retrieves a single sequencer run by run name
	 * 
	 * @param runName
	 * @return the sequencer run
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public RunDto byName(String runName) throws HttpResponseException {
	  return getResource("sequencerrun?name="+runName);
	}

}
