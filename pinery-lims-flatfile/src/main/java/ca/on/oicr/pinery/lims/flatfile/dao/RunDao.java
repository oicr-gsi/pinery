package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.Run;

public interface RunDao {
  
  public List<Run> getAllRuns();
  public Run getRun(Integer id);
  public Run getRun(String name);
  
}
