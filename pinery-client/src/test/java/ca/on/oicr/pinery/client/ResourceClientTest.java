package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ResourceClientTest {

  private static class TestResourceClient extends ResourceClient<String> {

    public TestResourceClient(PineryClient mainClient) {
      super(String.class, String[].class, mainClient);
    }
  }

  @Rule public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testGetResource() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    Response responseMock = mock(Response.class);
    when(responseMock.readEntity(String.class)).thenReturn("Polo");
    when(pineryClientMock.callPinery("Marco")).thenReturn(responseMock);

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    assertEquals("Polo", resourceClient.getResource("Marco"));
  }

  @Test
  public void testGetResourceNull() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    Response responseMock = mock(Response.class);
    when(responseMock.readEntity(String.class)).thenReturn(null);
    when(pineryClientMock.callPinery("Test")).thenReturn(responseMock);

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    exception.expect(HttpResponseException.class);
    resourceClient.getResource("Test");
  }

  @Test
  public void testGetResourceBadStatus() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    when(pineryClientMock.callPinery("Test"))
        .thenThrow(new HttpResponseException(Status.INTERNAL_SERVER_ERROR.getStatusCode()));

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    exception.expect(HttpResponseException.class);
    resourceClient.getResource("Test");
  }

  @Test
  public void testGetResourceList() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    Response responseMock = mock(Response.class);
    String[] data = {"Polo1", "Polo2"};

    when(responseMock.readEntity(String[].class)).thenReturn(data);
    when(pineryClientMock.callPinery("Marco")).thenReturn(responseMock);

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    List<String> resources = resourceClient.getResourceList("Marco");
    assertEquals(2, resources.size());
    assertEquals("Polo1", resources.get(0));
    assertEquals("Polo2", resources.get(1));
  }

  @Test
  public void testGetResourceListNull() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    Response responseMock = mock(Response.class);

    when(responseMock.readEntity(String[].class)).thenReturn(null);
    when(pineryClientMock.callPinery("Marco")).thenReturn(responseMock);

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    List<String> resources = resourceClient.getResourceList("Marco");
    assertNotNull(resources);
    assertEquals(0, resources.size());
  }

  @Test
  public void testGetResourceListBadStatus() throws HttpResponseException {
    PineryClient pineryClientMock = mock(PineryClient.class);
    when(pineryClientMock.callPinery("Test"))
        .thenThrow(new HttpResponseException(Status.INTERNAL_SERVER_ERROR.getStatusCode()));

    ResourceClient<String> resourceClient = new TestResourceClient(pineryClientMock);
    exception.expect(HttpResponseException.class);
    resourceClient.getResourceList("Test");
  }
}
