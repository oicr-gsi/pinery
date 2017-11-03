package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.Box;

public interface BoxDao {
  
  /**
   * @return a List of all Boxes
   */
  public List<Box> getAllBoxes();

}
