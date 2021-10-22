package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Requisition;
import java.util.List;

public interface RequisitionDao {

  List<Requisition> getAll();

  Requisition get(Integer id);

  Requisition getByName(String name);
}
