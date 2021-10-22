package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.service.RequisitionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRequisitionService implements RequisitionService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<Requisition> getRequisitions() {
    return lims.getRequisitions();
  }

  @Override
  public Requisition getRequisition(Integer id) {
    return lims.getRequisition(id);
  }

  @Override
  public Requisition getRequisition(String name) {
    return lims.getRequisition(name);
  }
}
