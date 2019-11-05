package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Box;
import java.util.List;

public interface BoxDao {

  /** @return a List of all Boxes */
  public List<Box> getAllBoxes();
}
