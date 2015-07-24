package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.ChangeLogDto;


public class ChangeLogClient extends ResourceClient<ChangeLogDto> {

	public ChangeLogClient(PineryClient mainClient) {
		super(ChangeLogDto.class, ChangeLogDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all changelogs in the database
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public List<ChangeLogDto> all() throws HttpResponseException {
		return getResourceList("sample/changelogs");
	}
	
	/**
	 * Retrieves the changelog for a specified sample
	 * 
	 * @param sampleId LIMS ID of the sample whose changelog is wanted
	 * @return the changelog
	 * @throws HttpResponseException on any HTTP Status other than 200 OK
	 */
	public ChangeLogDto forSample(int sampleId) throws HttpResponseException {
		return getResource("sample/"+sampleId+"/changelog");
	}

}
