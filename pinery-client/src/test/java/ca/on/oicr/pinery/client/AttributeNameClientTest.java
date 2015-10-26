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

import ca.on.oicr.ws.dto.AttributeNameDto;

public class AttributeNameClientTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  private PineryClient pineryClientMock;
  private AttributeNameClient client;
  
  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new AttributeNameClient(pineryClientMock));
  }
  
  @Test
  public void testGetAll() throws HttpResponseException {
    AttributeNameDto att1 = new AttributeNameDto();
    att1.setName("att1");
    AttributeNameDto att2 = new AttributeNameDto();
    att2.setName("att2");
    List<AttributeNameDto> list = new ArrayList<>();
    list.add(att1);
    list.add(att2);
    doReturn(list).when(client).getResourceList("sample/attributenames");
    
    List<AttributeNameDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals("att1", results.get(0).getName());
    assertEquals("att2", results.get(1).getName());
  }
  
  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<AttributeNameDto>()).when(client).getResourceList("sample/attributenames");
    List<AttributeNameDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }
  
  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("sample/attributenames");
    
    exception.expect(HttpResponseException.class);
    client.all();
  }

}
