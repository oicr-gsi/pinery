package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.SampleDto;

public class SampleClient extends ResourceClient<SampleDto> {
	
	// TODO: investigate why DTOs don't implement ca.on.oicr.pinery.api interfaces (Could return Samples rather than SampleDtos)
	
	private static final String resourceDir = "sample/";
	
	protected SampleClient(PineryClient mainClient) {
		super(SampleDto.class, SampleDto[].class, mainClient);
	}
	
	/**
	 * @return a list of all samples in the database
	 */
	public List<SampleDto> all() {
		return getResourceList("samples");
	}
	
	/**
	 * Retrieve a single sample by ID
	 * 
	 * @param sampleId ID of the sample to retrieve
	 * @return the sample
	 */
	public SampleDto byId(int sampleId) {
		return getResource(resourceDir + sampleId);
	}
	
}
