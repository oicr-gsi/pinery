package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.SampleProject;

public interface SampleProjectDao {
  /**
   * @return a List of all Projects
   */
  public List<SampleProject> getAllSampleProjects();
  
}
