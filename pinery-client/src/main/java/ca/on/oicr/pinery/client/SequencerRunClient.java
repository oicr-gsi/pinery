package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.RunDto;

public class SequencerRunClient extends ResourceClient<RunDto> {

	public SequencerRunClient(PineryClient mainClient) {
		super(RunDto.class, RunDto[].class, mainClient);
	}
	
	public List<RunDto> all() {
		return getResourceList("sequencerruns");
	}
	
	public RunDto byId(int runId) {
		return getResource("sequencerrun/"+runId);
	}

}
