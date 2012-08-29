package ca.on.oicr.pinery.service;

import java.util.List;

import ca.on.oicr.pinery.api.Sample;

public interface SampleService {

	public List<Sample> getSamples();
	
	public Sample getSample(Integer id);

}
