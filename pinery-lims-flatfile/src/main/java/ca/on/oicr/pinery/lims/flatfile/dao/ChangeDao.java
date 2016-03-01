package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.ChangeLog;

public interface ChangeDao {
  
  /**
   * @return a List of all Sample ChangeLogs
   */
  public List<ChangeLog> getAllChanges();
  
  /**
   * Retrieves the ChangeLog for a single Sample by Sample ID
   * 
   * @param sampleId
   * @return the ChangeLog if there are any Changes for this Sample ID; otherwise null
   */
  public ChangeLog getSampleChanges(String sampleId);
  
}
