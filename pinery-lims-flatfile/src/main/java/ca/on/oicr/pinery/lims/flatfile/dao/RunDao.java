package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.Run;

public interface RunDao {
  
  /**
   * @return a List of all sequencer Runs
   */
  public List<Run> getAllRuns();
  
  /**
   * Retrieves a single sequencer Run by Run ID
   * 
   * @param id ID of the Run to retrieve
   * @return the Run if one is found with the provided Run ID; otherwise null
   */
  public Run getRun(Integer id);
  
  /**
   * Retrieves a single sequencer Run by Run name
   * 
   * @param name name of the Run to retrieve
   * @return the Run if one is found matching the provided Run name; otherwise null
   */
  public Run getRun(String name);
  
}
