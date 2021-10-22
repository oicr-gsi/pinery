package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Assay;
import java.util.List;

public interface AssayDao {

  List<Assay> getAll();

  Assay get(Integer id);
}
