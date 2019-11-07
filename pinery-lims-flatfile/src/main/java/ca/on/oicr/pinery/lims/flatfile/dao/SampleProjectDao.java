package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.SampleProject;
import java.util.List;

public interface SampleProjectDao {
  /** @return a List of all Projects */
  public List<SampleProject> getAllSampleProjects();
}
