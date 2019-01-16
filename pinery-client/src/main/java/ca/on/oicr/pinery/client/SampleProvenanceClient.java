package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.SampleProvenanceDto;

public class SampleProvenanceClient extends ResourceClient<SampleProvenanceDto> {
	
	public SampleProvenanceClient(PineryClient mainClient) {
		super(SampleProvenanceDto.class, SampleProvenanceDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all sample provenance in the latest provenance version
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<SampleProvenanceDto> latest() throws HttpResponseException {
    return getResourceList("provenance/latest/sample-provenance");
  }
	
	/**
   * @return a list of all sample provenance in the specified provenance version
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<SampleProvenanceDto> version(String version) throws HttpResponseException {
    return getResourceList(version == null ? "provenance/sample-provenance" : "provenance/" + version + "/sample-provenance");
  }

}
