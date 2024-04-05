package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.ws.dto.ChangeLogDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ChangeLogClientTest {

  private PineryClient pineryClientMock;
  private ChangeLogClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new ChangeLogClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    ChangeLogDto log1 = new ChangeLogDto();
    log1.setSampleUrl("url1");
    ChangeLogDto log2 = new ChangeLogDto();
    log2.setSampleUrl("url2");
    List<ChangeLogDto> list = new ArrayList<>();
    list.add(log1);
    list.add(log2);
    doReturn(list).when(client).getResourceList("sample/changelogs");

    List<ChangeLogDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals("url1", results.get(0).getSampleUrl());
    assertEquals("url2", results.get(1).getSampleUrl());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<ChangeLogDto>()).when(client).getResourceList("sample/changelogs");
    List<ChangeLogDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("sample/changelogs");

    assertThrows(HttpResponseException.class, () -> client.all());
  }

  @Test
  public void testGetById() throws HttpResponseException {
    ChangeLogDto log = new ChangeLogDto();
    log.setSampleUrl("url");
    doReturn(log).when(client).getResource("sample/22/changelog");

    ChangeLogDto result = client.forSample(22);
    assertEquals("url", result.getSampleUrl());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("sample/22/changelog");

    assertThrows(HttpResponseException.class, () -> client.forSample(22));
  }
}
