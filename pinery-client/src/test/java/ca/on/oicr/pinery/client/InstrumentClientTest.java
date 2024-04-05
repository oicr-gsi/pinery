package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.InstrumentDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InstrumentClientTest {

  private PineryClient pineryClientMock;
  private InstrumentClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new InstrumentClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    InstrumentDto in1 = new InstrumentDto();
    in1.setId(111);
    InstrumentDto in2 = new InstrumentDto();
    in2.setId(222);
    List<InstrumentDto> list = new ArrayList<>();
    list.add(in1);
    list.add(in2);
    doReturn(list).when(client).getResourceList("instruments");

    List<InstrumentDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals(Integer.valueOf(111), results.get(0).getId());
    assertEquals(Integer.valueOf(222), results.get(1).getId());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<InstrumentDto>()).when(client).getResourceList("instruments");
    List<InstrumentDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("instruments");

    assertThrows(HttpResponseException.class, () -> client.all());
  }

  @Test
  public void testGetByModel() throws HttpResponseException {
    InstrumentDto in1 = new InstrumentDto();
    in1.setId(111);
    InstrumentDto in2 = new InstrumentDto();
    in2.setId(222);
    List<InstrumentDto> list = new ArrayList<>();
    list.add(in1);
    list.add(in2);
    doReturn(list).when(client).getResourceList("instrumentmodel/5/instruments");

    List<InstrumentDto> results = client.byModel(5);
    assertEquals(2, results.size());
    assertEquals(Integer.valueOf(111), results.get(0).getId());
    assertEquals(Integer.valueOf(222), results.get(1).getId());
  }

  @Test
  public void testGetByModelButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<InstrumentDto>())
        .when(client)
        .getResourceList("instrumentmodel/5/instruments");
    List<InstrumentDto> results = client.byModel(5);
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetByModelBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException())
        .when(client)
        .getResourceList("instrumentmodel/5/instruments");

    assertThrows(HttpResponseException.class, () -> client.byModel(5));
  }

  @Test
  public void testGetById() throws HttpResponseException {
    InstrumentDto in = new InstrumentDto();
    in.setId(123);
    doReturn(in).when(client).getResource("instrument/123");

    InstrumentDto result = client.byId(123);
    assertEquals(Integer.valueOf(123), result.getId());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("instrument/123");

    assertThrows(HttpResponseException.class, () -> client.byId(123));
  }
}
