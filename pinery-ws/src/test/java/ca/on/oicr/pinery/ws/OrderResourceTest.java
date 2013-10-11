package ca.on.oicr.pinery.ws;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.service.OrderService;
import ca.on.oicr.ws.dto.OrderDto;

public class OrderResourceTest {

   @Test
   public void testName() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto, is(notNullValue()));
      assertThat(orderDto.getProject(), is("HALT"));
   }

   private Order getOrder() {
      Order order = new DefaultOrder();
      order.setCreatedById("One");
      order.setId(5);
      order.setModifiedById("Two");
      order.setPlatform("new platform");
      order.setProject("HALT");
      return order;
   }
}
