package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<Order> getOrder() {
    return lims.getOrders();
  }

  @Override
  public Order getOrder(Integer id) {
    return lims.getOrder(id);
  }
}
