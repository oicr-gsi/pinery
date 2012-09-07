package ca.on.oicr.pinery.service;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;

public interface SampleService {

	public List<Sample> getSamples();
	
	public List<Sample> getSamples2(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after);
	
	public Sample getSample(Integer id);
	
	public List<SampleProject> getSampleProjects();

}
