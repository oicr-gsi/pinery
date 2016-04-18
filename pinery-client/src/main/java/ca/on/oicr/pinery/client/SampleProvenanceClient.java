package ca.on.oicr.pinery.client;

import java.util.List;
import ca.on.oicr.ws.dto.SampleProvenanceDto;

public class SampleProvenanceClient extends ResourceClient<SampleProvenanceDto> {
	
	private static final String resourceDir = "sample-provenance/";
	
	public SampleProvenanceClient(PineryClient mainClient) {
		super(SampleProvenanceDto.class, SampleProvenanceDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all sample provenance
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<SampleProvenanceDto> all() throws HttpResponseException {
		return getResourceList("sample-provenance");
	}

}
