package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Requisition;
import java.util.List;

public interface RequisitionService {

  List<Requisition> getRequisitions();

  Requisition getRequisition(Integer id);

  Requisition getRequisition(String name);
}
