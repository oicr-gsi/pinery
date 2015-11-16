package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.service.OrderService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

import com.google.common.collect.Lists;

@Component
@Path("/")
@Api(value = "order")
public class OrderResource {

//   private static final Logger log = LoggerFactory.getLogger(OrderResource.class);

   @Context
   private UriInfo uriInfo;

   @Autowired
   private OrderService orderService;

   void setUriInfo(UriInfo uriInfo) {
      this.uriInfo = uriInfo;
   }

   void setOrderService(OrderService orderService) {
      this.orderService = orderService;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/orders")
   @ApiOperation(value = "List all orders", response = OrderDto.class, responseContainer = "List")
   public List<OrderDto> getOrders() {
      List<Order> orders = orderService.getOrder();
      List<OrderDto> result = Lists.newArrayList();
      final URI baseUri = uriInfo.getBaseUriBuilder().path("order/").build();
      for (Order order : orders) {
         OrderDto dto = Dtos.asDto(order);
         dto.setUrl(baseUri + dto.getId().toString());
         addSampleUrl(dto);
         addOrders(order, dto);
         result.add(dto);
      }
      return result;
   }

   @GET
   @Produces({ "application/json" })
   @Path("/order/{id}")
   @ApiOperation(value = "Find order by ID", response = OrderDto.class)
   @ApiResponse(code = 404, message = "No order found")
   public OrderDto getOrder(@ApiParam(value = "ID of order to fetch", required = true) @PathParam("id") Integer id) {
      Order order = orderService.getOrder(id);
      if (order == null) {
        throw new NotFoundException("No order found with ID: " + id);
      }
      OrderDto dto = Dtos.asDto(order);
      final URI uri = uriInfo.getAbsolutePathBuilder().build();
      dto.setUrl(uri.toString());
      addSampleUrl(dto);
      addOrders(order, dto);
      return dto;
   }

   private OrderDto addSampleUrl(OrderDto dto) {
      final URI baseUriSample = uriInfo.getBaseUriBuilder().path("sample/").build();

      if (dto != null && dto.getSamples() != null) {
         for (OrderDtoSample orderDtoSample : dto.getSamples()) {
            orderDtoSample.setUrl(baseUriSample + orderDtoSample.getId().toString());
         }
      }
      return dto;
   }

   private void addOrders(Order order, OrderDto dto) {

      final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();

      if (order.getCreatedById() != null) {
         dto.setCreatedByUrl(baseUri + order.getCreatedById().toString());
      }

      if (order.getModifiedById() != null) {
         dto.setModifiedByUrl(baseUri + order.getModifiedById().toString());
      }

   }
}
