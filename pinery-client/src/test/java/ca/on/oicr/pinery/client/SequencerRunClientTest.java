package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.RunDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SequencerRunClientTest {

  private PineryClient pineryClientMock;
  private SequencerRunClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new SequencerRunClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    RunDto run1 = new RunDto();
    run1.setId(111);
    RunDto run2 = new RunDto();
    run2.setId(222);
    List<RunDto> list = new ArrayList<>();
    list.add(run1);
    list.add(run2);
    doReturn(list).when(client).getResourceList("sequencerruns");

    List<RunDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals(Integer.valueOf(111), results.get(0).getId());
    assertEquals(Integer.valueOf(222), results.get(1).getId());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<RunDto>()).when(client).getResourceList("sequencerruns");
    List<RunDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("sequencerruns");

    assertThrows(HttpResponseException.class, () -> client.all());
  }

  @Test
  public void testGetById() throws HttpResponseException {
    RunDto run = new RunDto();
    run.setId(6);
    doReturn(run).when(client).getResource("sequencerrun/6");

    RunDto result = client.byId(6);
    assertEquals(Integer.valueOf(6), result.getId());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("sequencerrun/6");

    assertThrows(HttpResponseException.class, () -> client.byId(6));
  }

  @Test
  public void testGetByName() throws HttpResponseException {
    RunDto run = new RunDto();
    run.setId(567);
    doReturn(run).when(client).getResource("sequencerrun?name=Jimmy");

    RunDto result = client.byName("Jimmy");
    assertEquals(Integer.valueOf(567), result.getId());
  }

  @Test
  public void testGetByNameBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("sequencerrun?name=Jimmy");

    assertThrows(HttpResponseException.class, () -> client.byName("Jimmy"));
  }
}
