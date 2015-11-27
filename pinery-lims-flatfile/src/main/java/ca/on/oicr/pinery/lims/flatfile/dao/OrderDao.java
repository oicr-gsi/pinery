package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.Order;

public interface OrderDao {
  
  public List<Order> getAllOrders();
  public Order getOrder(Integer id);
  
}
