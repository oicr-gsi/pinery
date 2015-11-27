package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;

public class SampleFileDao implements SampleDao {

  @Override
  public Sample getSample(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Sample> getAllSamples() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Sample> getSamplesFiltered(Boolean archived,
      Set<String> projects, Set<String> types, DateTime before, DateTime after) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SampleProject> getAllSampleProjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AttributeName> getAllSampleAttributes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Type> getAllSampleTypes() {
    // TODO Auto-generated method stub
    return null;
  }

}
