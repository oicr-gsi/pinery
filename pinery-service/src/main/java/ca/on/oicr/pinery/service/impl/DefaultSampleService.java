package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.service.SampleService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSampleService implements SampleService {

  @Autowired private CacheOrLims lims;

  @Override
  public Sample getSample(String id) {
    return lims.getSample(id);
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    return lims.getSampleProjects();
  }

  @Override
  public List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after) {
    return lims.getSamples(archived, projects, types, before, after);
  }

  @Override
  public List<Type> getTypes() {
    return lims.getTypes();
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    return lims.getAttributeNames();
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    return lims.getChangeLogs();
  }

  @Override
  public ChangeLog getChangeLog(String id) {
    return lims.getChangeLog(id);
  }
}
