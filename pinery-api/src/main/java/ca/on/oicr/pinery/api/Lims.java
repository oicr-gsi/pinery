package ca.on.oicr.pinery.api;

import java.util.List;

public interface Lims {
	
	/**
	 * Returns a list of project names. Useful for retrieving samples that belong to a particular project.
	 * @return
	 */
	public List<String> getProjects();
	
	/**
	 * Returns all the samples.
	 * @return
	 */
	public List<Sample> getSamples();
	
	public Sample getSample(Integer id);
	
	public List<SampleProject> getSampleProjects();

}
