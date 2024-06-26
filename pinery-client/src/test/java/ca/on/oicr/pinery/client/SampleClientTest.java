package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import ca.on.oicr.pinery.client.SampleClient.SamplesFilter;
import ca.on.oicr.ws.dto.SampleDto;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SampleClientTest {

  private PineryClient pineryClientMock;
  private SampleClient client;

  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new SampleClient(pineryClientMock));
  }

  @Test
  public void testGetAll() throws HttpResponseException {
    SampleDto sample1 = new SampleDto();
    sample1.setId("111");
    SampleDto sample2 = new SampleDto();
    sample2.setId("222");
    List<SampleDto> list = new ArrayList<>();
    list.add(sample1);
    list.add(sample2);
    doReturn(list).when(client).getResourceList("samples");

    List<SampleDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals("111", results.get(0).getId());
    assertEquals("222", results.get(1).getId());
  }

  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<String>()).when(client).getResourceList("samples");
    List<SampleDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("samples");

    assertThrows(HttpResponseException.class, () -> client.all());
  }

  @Test
  public void testGetById() throws HttpResponseException {
    SampleDto sample = new SampleDto();
    sample.setId("22");
    doReturn(sample).when(client).getResource("sample/22");

    SampleDto result = client.byId("22");
    assertEquals("22", result.getId());
  }

  @Test
  public void testGetByIdBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResource("sample/22");

    assertThrows(HttpResponseException.class, () -> client.byId("22"));
  }

  @Test
  public void testFilterArchivedParameter() {
    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter().withArchived(true);
    assertEquals("my.pretend.url?archived=true", filter.buildUrl("my.pretend.url"));
  }

  @Test
  public void testFilterDateBeforeParameter() {
    ZonedDateTime date = ZonedDateTime.of(2015, 10, 23, 15, 21, 0, 0, ZoneId.of("Z"));
    String pattern = "^my\\.pretend\\.url\\?before=2015-10-23T15:21:00Z$";

    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter().withDateBefore(date);
    assertTrue(filter.buildUrl("my.pretend.url").matches(pattern));
  }

  @Test
  public void testFilterDateAfterParameter() {
    ZonedDateTime date = ZonedDateTime.of(2015, 10, 23, 15, 21, 0, 0, ZoneId.of("Z"));
    String pattern = "^my\\.pretend\\.url\\?after=2015-10-23T15:21:00Z$";

    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter().withDateAfter(date);
    assertTrue(filter.buildUrl("my.pretend.url").matches(pattern));
  }

  @Test
  public void testFilterProjectsParameter() {
    List<String> projects = Arrays.asList("Proj1", "Proj2");
    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter().withProjects(projects);
    String url = filter.buildUrl("my.pretend.url");
    assertTrue(url.contains("project=Proj1"));
    assertTrue(url.contains("project=Proj2"));
    assertTrue(url.matches("^my\\.pretend\\.url\\?project=Proj\\d&project=Proj\\d$"));
  }

  @Test
  public void testFilterTypesParameter() {
    List<String> types = Arrays.asList("Type1", "Type2");
    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter().withTypes(types);
    String url = filter.buildUrl("my.pretend.url");
    assertTrue(url.contains("type=Type1"));
    assertTrue(url.contains("type=Type2"));
    assertTrue(url.matches("^my\\.pretend\\.url\\?type=Type\\d&type=Type\\d$"));
  }

  @Test
  public void testFilterNoParams() {
    String url = new SampleClient.SamplesFilter().buildUrl("my.pretend.url");
    assertEquals("my.pretend.url", url);
  }

  @Test
  public void testFilterAllParams() {
    ZonedDateTime date = ZonedDateTime.of(2015, 10, 23, 15, 21, 0, 0, ZoneId.of("Z"));
    List<String> projects = Arrays.asList("Proj1");
    List<String> types = Arrays.asList("Type1");
    SampleClient.SamplesFilter filter = new SampleClient.SamplesFilter()
        .withArchived(false)
        .withDateAfter(date)
        .withDateBefore(date)
        .withProjects(projects)
        .withTypes(types);
    String url = filter.buildUrl("my.pretend.url");
    assertTrue(url.matches("^my\\.pretend\\.url\\?([^\\?&=]*=[^\\?&=]*&){4}[^\\?&=]*=[^\\?&=]*$"));
  }

  @Test
  public void testGetFiltered() throws HttpResponseException {
    SampleDto sample1 = new SampleDto();
    sample1.setId("111");
    SampleDto sample2 = new SampleDto();
    sample2.setId("222");
    List<SampleDto> list = new ArrayList<>();
    list.add(sample1);
    list.add(sample2);
    doReturn(list).when(client).getResourceList("samples?archived=true");
    SamplesFilter filter = new SamplesFilter().withArchived(true);

    List<SampleDto> results = client.allFiltered(filter);
    assertEquals(2, results.size());
    assertEquals("111", results.get(0).getId());
    assertEquals("222", results.get(1).getId());
  }

  @Test
  public void testGetFilteredButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<String>()).when(client).getResourceList("samples?archived=true");
    SamplesFilter filter = new SamplesFilter().withArchived(true);
    List<SampleDto> results = client.allFiltered(filter);
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  @Test
  public void testGetFilteredBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("samples?archived=true");
    SamplesFilter filter = new SamplesFilter().withArchived(true);

    assertThrows(HttpResponseException.class, () -> client.allFiltered(filter));
  }
}
