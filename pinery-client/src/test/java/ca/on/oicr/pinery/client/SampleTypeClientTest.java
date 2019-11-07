package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.TypeDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SampleTypeClientTest {

  @Rule public final ExpectedException exception = ExpectedException.none();

  private PineryClient pineryClientMock;
  private SampleTypeClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new SampleTypeClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    TypeDto type1 = new TypeDto();
    type1.setName("type1");
    TypeDto type2 = new TypeDto();
    type2.setName("type2");
    List<TypeDto> list = new ArrayList<>();
    list.add(type1);
    list.add(type2);
    doReturn(list).when(client).getResourceList("sample/types");

    List<TypeDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals("type1", results.get(0).getName());
    assertEquals("type2", results.get(1).getName());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<TypeDto>()).when(client).getResourceList("sample/types");
    List<TypeDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("sample/types");

    exception.expect(HttpResponseException.class);
    client.all();
  }
}
