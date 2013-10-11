package ca.on.oicr.pinery.service;

import java.util.List;

import ca.on.oicr.pinery.api.Order;

public interface OrderService {

   public List<Order> getOrder();

   public Order getOrder(Integer id);
}
