package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.on.oicr.ws.dto.UserDto;

public class UserClientTest {
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  private PineryClient pineryClientMock;
  private UserClient client;
  
  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new UserClient(pineryClientMock));
  }
  
  @Test
  public void testGetAll() throws HttpResponseException {
    UserDto user1 = new UserDto();
    user1.setId(111);
    UserDto user2 = new UserDto();
    user2.setId(222);
    List<UserDto> list = new ArrayList<>();
    list.add(user1);
    list.add(user2);
    doReturn(list).when(client).getResourceList("users");
    
    List<UserDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals(new Integer(111), results.get(0).getId());
    assertEquals(new Integer(222), results.get(1).getId());
  }
  
  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<UserDto>()).when(client).getResourceList("users");
    List<UserDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }
  
  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("users");
    
    exception.expect(HttpResponseException.class);
    client.all();
  }
  
  @Test
  public void testGetById() throws HttpResponseException {
    UserDto order = new UserDto();
    order.setId(27);
    doReturn(order).when(client).getResource("user/34");
    
    UserDto result = client.byId(34);
    assertEquals(new Integer(27), result.getId());
  }
  
  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("user/34");
    
    exception.expect(HttpResponseException.class);
    client.byId(34);
  }
  
}
