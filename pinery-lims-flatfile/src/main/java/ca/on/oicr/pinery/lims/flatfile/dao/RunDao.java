package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Run;
import java.util.List;
import java.util.Set;

public interface RunDao {

  /**
   * @param sampleIds filter to include only runs containing these samples if not null
   * @return a List of all sequencer Runs
   */
  public List<Run> getAllRuns(Set<String> sampleIds);

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
