package ca.on.oicr.pinery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.service.OrderService;

public class DefaultOrderService implements OrderService {

   @Autowired
   private Lims lims;

   @Override
   public List<Order> getOrder() {
      return lims.getOrders();
   }

   @Override
   public Order getOrder(Integer id) {
      return lims.getOrder(id);
   }

}
