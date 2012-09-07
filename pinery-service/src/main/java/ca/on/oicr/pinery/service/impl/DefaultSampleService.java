package ca.on.oicr.pinery.service.impl;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.service.SampleService;

@Service
public class DefaultSampleService implements SampleService {
	
	@Autowired
	private Lims lims;

	@Override
	public List<Sample> getSamples() {
		return lims.getSamples();
	}

	@Override
	public Sample getSample(Integer id) {
		return lims.getSample(id);
	}

	@Override
	public List<SampleProject> getSampleProjects() {
		return lims.getSampleProjects();
	}

	@Override
	public List<Sample> getSamples2(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after) {
		return lims.getSamples2(archived, projects, types, before, after);
	}
}
