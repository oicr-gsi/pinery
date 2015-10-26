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

import ca.on.oicr.ws.dto.SampleProjectDto;

public class ProjectClientTest {
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  private PineryClient pineryClientMock;
  private ProjectClient client;
  
  @Before
  public void setup() {
    pineryClientMock = mock(PineryClient.class);
    client = spy(new ProjectClient(pineryClientMock));
  }
  
  @Test
  public void testGetAll() throws HttpResponseException {
    SampleProjectDto proj1 = new SampleProjectDto();
    proj1.setName("proj1");
    SampleProjectDto proj2 = new SampleProjectDto();
    proj2.setName("proj2");
    List<SampleProjectDto> list = new ArrayList<>();
    list.add(proj1);
    list.add(proj2);
    doReturn(list).when(client).getResourceList("sample/projects");
    
    List<SampleProjectDto> results = client.all();
    assertEquals(2, results.size());
    assertEquals("proj1", results.get(0).getName());
    assertEquals("proj2", results.get(1).getName());
  }
  
  @Test
  public void testGetAllButNoneAvailable() throws HttpResponseException {
    doReturn(new ArrayList<SampleProjectDto>()).when(client).getResourceList("sample/projects");
    List<SampleProjectDto> results = client.all();
    assertNotNull(results);
    assertEquals(0, results.size());
  }
  
  @Test
  public void testGetAllBadStatus() throws HttpResponseException {
    doThrow(new HttpResponseException()).when(client).getResourceList("sample/projects");
    
    exception.expect(HttpResponseException.class);
    client.all();
  }
  
}
