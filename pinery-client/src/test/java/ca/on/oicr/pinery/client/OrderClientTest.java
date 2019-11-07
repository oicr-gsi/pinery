package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.OrderDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OrderClientTest {

  @Rule public final ExpectedException exception = ExpectedException.none();

  private PineryClient pineryClientMock;
  private OrderClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new OrderClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    OrderDto order1 = new OrderDto();
    order1.setId(111);
    OrderDto order2 = new OrderDto();
    order2.setId(222);
    List<OrderDto> list = new ArrayList<>();
    list.add(order1);
    list.add(order2);
    doReturn(list).when(client).getResourceList("orders");

    List<OrderDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals(new Integer(111), results.get(0).getId());
    assertEquals(new Integer(222), results.get(1).getId());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<OrderDto>()).when(client).getResourceList("orders");
    List<OrderDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("orders");

    exception.expect(HttpResponseException.class);
    client.all();
  }

  @Test
  public void testGetById() throws HttpResponseException {
    OrderDto order = new OrderDto();
    order.setId(27);
    doReturn(order).when(client).getResource("order/27");

    OrderDto result = client.byId(27);
    assertEquals(new Integer(27), result.getId());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("order/27");

    exception.expect(HttpResponseException.class);
    client.byId(27);
  }
}
