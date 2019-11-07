package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Order;
import java.util.List;

public interface OrderService {

  public List<Order> getOrder();

  public Order getOrder(Integer id);
}
