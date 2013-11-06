package ca.on.oicr.pinery.ws;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.service.OrderService;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class OrderResourceTest {

   @Test
   public void test_Resource_1() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto, is(notNullValue()));
   }

   @Test
   public void test_Resource_2() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);
      Date date = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

      assertThat(orderDto.getCreatedDate(), is(sf.format(date)));
   }

   @Test
   public void test_Resource_3() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto.getId(), is(2));
   }

   @Test
   public void test_Resource_4() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);
      Date date = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

      assertThat(orderDto.getModifiedDate(), is(sf.format(date)));
   }

   @Test
   public void test_Resource_5() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto.getPlatform(), is("Illumina HiSeq 2000"));
   }

   @Test
   public void test_Resource_6() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto.getProject(), is("HALT"));
   }

   @Test
   public void test_Resource_7() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      OrderDto orderDto = orderResource.getOrder(1);

      assertThat(orderDto.getStatus(), is("Complete"));
   }

   @Test
   public void test_Resource_8() throws Exception {

      boolean status;
      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);

      AttributeDto attributeDto = new AttributeDto();
      Set<AttributeDto> attributeDtoSet = Sets.newHashSet();
      OrderDtoSample orderDtoSample = new OrderDtoSample();
      attributeDto.setName("read length");
      attributeDtoSet.add(attributeDto);
      orderDtoSample.setAttributes(attributeDtoSet);
      status = attributeContainsName(orderDtoSample.getAttributes(), attributeDto.getName());

      assertThat(status, is(true));
   }

   @Test
   public void test_Resource_9() throws Exception {

      boolean status;
      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getAbsolutePathBuilder().path("order/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);

      when(uriInfoMock.getAbsolutePathBuilder().build()).thenReturn(new URI("http://test/order/1"));
      when(uriInfoMock.getAbsolutePathBuilder().path("order/").build()).thenReturn(new URI("http://test/order/1"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder(1)).thenReturn(getOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);

      AttributeDto attributeDto = new AttributeDto();
      Set<AttributeDto> attributeDtoSet = Sets.newHashSet();
      OrderDtoSample orderDtoSample = new OrderDtoSample();
      attributeDto.setValue("2x101");
      attributeDtoSet.add(attributeDto);
      orderDtoSample.setAttributes(attributeDtoSet);
      status = attributeContainsValue(orderDtoSample.getAttributes(), attributeDto.getValue());

      assertThat(status, is(true));
   }

   @Test
   public void test_Resource_10() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("order")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("order/")).thenReturn(uriBuilderMock);

      when(uriInfoMock.getBaseUriBuilder().build()).thenReturn(new URI("http://test/order"));
      when(uriInfoMock.getBaseUriBuilder().path("order").build()).thenReturn(new URI("http://test/order"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample/1"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder()).thenReturn(getListOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);
      List<OrderDto> orderDto = orderResource.getOrders();

      assertThat(orderDto, is(notNullValue()));
   }

   @Test
   public void test_Resource_11() throws Exception {

      UriInfo uriInfoMock = mock(UriInfo.class);
      UriBuilder uriBuilderMock = mock(UriBuilder.class);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("order")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder()).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("order/")).thenReturn(uriBuilderMock);

      when(uriInfoMock.getBaseUriBuilder().build()).thenReturn(new URI("http://test/order"));
      when(uriInfoMock.getBaseUriBuilder().path("order").build()).thenReturn(new URI("http://test/order"));

      when(uriInfoMock.getBaseUriBuilder().path("user/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("user/").build()).thenReturn(new URI("http://test/user/1"));
      when(uriInfoMock.getBaseUriBuilder().path("sample/")).thenReturn(uriBuilderMock);
      when(uriInfoMock.getBaseUriBuilder().path("sample/").build()).thenReturn(new URI("http://test/sample"));

      OrderService orderService = mock(OrderService.class);
      when(orderService.getOrder()).thenReturn(getListOrder());
      OrderResource orderResource = new OrderResource();
      orderResource.setOrderService(orderService);
      orderResource.setUriInfo(uriInfoMock);

      List<OrderDto> originalListOrderDto = orderResource.getOrders();
      OrderDto orderDto = new OrderDto();
      List<OrderDto> listOrderDto = Lists.newArrayList();
      Date date = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

      orderDto.setCreatedDate(sf.format(date));
      orderDto.setId(2);
      orderDto.setModifiedDate(sf.format(date));
      orderDto.setPlatform("Illumina HiSeq 2000");
      orderDto.setProject("HALT");
      orderDto.setStatus("Complete");
      orderDto.setUrl("http://test/sample/2");

      listOrderDto.add(orderDto);

      assertThat(originalListOrderDto, containsInAnyOrder(listOrderDto.toArray()));
   }

   private Order getOrder() {
      Order order = new DefaultOrder();
      Date date = new Date();

      order.setCreatedDate(date);
      order.setId(2);
      order.setModifiedDate(date);
      order.setPlatform("Illumina HiSeq 2000");
      order.setProject("HALT");
      order.setStatus("Complete");

      return order;
   }

   public boolean attributeContainsName(Set<AttributeDto> attribute, String attributeName) {

      for (AttributeDto dto : attribute) {
         if (dto.getName().equals(attributeName)) {
            return true;
         }
      }
      return false;
   }

   public boolean attributeContainsValue(Set<AttributeDto> attribute, String attributeValue) {

      for (AttributeDto dto : attribute) {
         if (dto.getValue().equals(attributeValue)) {
            return true;
         }
      }
      return false;
   }

   private List<Order> getListOrder() {
      Order order = new DefaultOrder();
      List<Order> list = Lists.newArrayList();
      Date date = new Date();

      order.setCreatedDate(date);
      order.setId(2);
      order.setModifiedDate(date);
      order.setPlatform("Illumina HiSeq 2000");
      order.setProject("HALT");
      order.setStatus("Complete");

      list.add(order);

      return list;
   }
}
