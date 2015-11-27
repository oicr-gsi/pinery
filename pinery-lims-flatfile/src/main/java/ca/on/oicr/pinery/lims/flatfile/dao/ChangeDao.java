package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.ChangeLog;

public interface ChangeDao {
  
  public List<ChangeLog> getAllChanges();
  public ChangeLog getSampleChanges(Integer sampleId);
  
}
