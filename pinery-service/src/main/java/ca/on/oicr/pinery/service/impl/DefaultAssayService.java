package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.Assay;
import ca.on.oicr.pinery.service.AssayService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAssayService implements AssayService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<Assay> getAssays() {
    return lims.getAssays();
  }

  @Override
  public Assay getAssay(Integer id) {
    return lims.getAssay(id);
  }
}
