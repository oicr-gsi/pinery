package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.InstrumentModelDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InstrumentModelClientTest {

  private PineryClient pineryClientMock;
  private InstrumentModelClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new InstrumentModelClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    InstrumentModelDto model1 = new InstrumentModelDto();
    model1.setId(111);
    InstrumentModelDto model2 = new InstrumentModelDto();
    model2.setId(222);
    List<InstrumentModelDto> list = new ArrayList<>();
    list.add(model1);
    list.add(model2);
    doReturn(list).when(client).getResourceList("instrumentmodels");

    List<InstrumentModelDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals(Integer.valueOf(111), results.get(0).getId());
    assertEquals(Integer.valueOf(222), results.get(1).getId());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<InstrumentModelDto>()).when(client).getResourceList("instrumentmodels");
    List<InstrumentModelDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("instrumentmodels");

    assertThrows(HttpResponseException.class, () -> client.all());
  }

  @Test
  public void testGetById() throws HttpResponseException {
    InstrumentModelDto model = new InstrumentModelDto();
    model.setId(12);
    doReturn(model).when(client).getResource("instrumentmodel/12");

    InstrumentModelDto result = client.byId(12);
    assertEquals(Integer.valueOf(12), result.getId());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("instrumentmodel/12");

    assertThrows(HttpResponseException.class, () -> client.byId(12));
  }
}
