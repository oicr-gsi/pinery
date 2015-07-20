package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.ChangeLogDto;


public class ChangeLogClient extends ResourceClient<ChangeLogDto> {

	public ChangeLogClient(PineryClient mainClient) {
		super(ChangeLogDto.class, ChangeLogDto[].class, mainClient);
	}
	
	public List<ChangeLogDto> all() throws HttpResponseException {
		return getResourceList("sample/changelogs");
	}
	
	public ChangeLogDto forSample(int sampleId) throws HttpResponseException {
		return getResource("sample/"+sampleId+"/changelog");
	}

}
