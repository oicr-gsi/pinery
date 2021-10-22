package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Assay;
import java.util.List;

public interface AssayService {

  List<Assay> getAssays();

  Assay getAssay(Integer id);
}
