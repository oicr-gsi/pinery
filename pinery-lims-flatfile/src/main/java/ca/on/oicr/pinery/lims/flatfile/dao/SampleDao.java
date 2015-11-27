package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;

public interface SampleDao {
  
  public Sample getSample(Integer id);
  public List<Sample> getAllSamples();
  public List<Sample> getSamplesFiltered(Boolean archived, Set<String> projects, Set<String> types, DateTime before, DateTime after);
  public List<SampleProject> getAllSampleProjects();
  public List<AttributeName> getAllSampleAttributes();
  public List<Type> getAllSampleTypes();
  
}
