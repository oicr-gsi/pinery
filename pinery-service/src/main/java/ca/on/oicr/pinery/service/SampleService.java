package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface SampleService {

  public List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after);

  public Sample getSample(String id);

  public List<SampleProject> getSampleProjects();

  public List<Type> getTypes();

  public List<AttributeName> getAttributeNames();

  public List<ChangeLog> getChangeLogs();

  public ChangeLog getChangeLog(String id);
}
