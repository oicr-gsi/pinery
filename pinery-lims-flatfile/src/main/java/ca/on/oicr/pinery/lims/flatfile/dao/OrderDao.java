package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.Order;

public interface OrderDao {
  
  /**
   * @return a List of all Orders
   */
  public List<Order> getAllOrders();
  
  /**
   * Retrieves a single Order by Order ID
   * 
   * @param id ID of the Order to retrieve
   * @return the Order if one is found with the provided Order ID; otherwise null
   */
  public Order getOrder(Integer id);
  
}
