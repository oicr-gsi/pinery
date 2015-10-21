package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PineryClient.class)
public class PineryClientTest {
  
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  @Test
  public void testCallPinery() throws HttpResponseException {
    Client clientMock = makeMockClient();
    WebTarget targetMock = mock(WebTarget.class);
    targetMock.request();
    Invocation.Builder builderMock = mock(Invocation.Builder.class);
    Response responseMock = Response.ok("response").build();
    
    when(clientMock.target("http://pretend.pinery.server/test")).thenReturn(targetMock);
    when(targetMock.request()).thenReturn(builderMock);
    when(builderMock.get()).thenReturn(responseMock);
    
    Response response = null;
    try (PineryClient client = new PineryClient("http://pretend.pinery.server/")) {
      response = client.callPinery("test");
    }
    assertNotNull(response);
    assertEquals(response.getStatus(), 200);
  }
  
  private Client makeMockClient() {
    Client clientMock = mock(Client.class);
    when(clientMock.register(ResteasyJacksonProvider.class)).thenReturn(clientMock);
    
    PowerMockito.mockStatic(PineryClient.class);
    when(PineryClient.getInsecureClient()).thenReturn(clientMock);
    when(PineryClient.getSecureClient()).thenReturn(clientMock);
    
    return clientMock;
  }
  
  @Test
  public void testOpenClose() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertTrue(client.isOpen());
    assertFalse(client.isClosed());
    
    client.close();
    assertFalse(client.isOpen());
    assertTrue(client.isClosed());
  }
  
  @Test
  public void testGetSample() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getSample());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getSample();
  }
  
  @Test
  public void testGetSampleProject() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getSampleProject());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getSampleProject();
  }
  
  @Test
  public void testGetSampleType() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getSampleType());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getSampleType();
  }
  
  @Test
  public void testGetSequencerRun() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getSequencerRun());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getSequencerRun();
  }
  
  @Test
  public void testGetUser() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getUser());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getUser();
  }
  
  @Test
  public void testGetAttributeName() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getAttributeName());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getAttributeName();
  }
  
  @Test
  public void testGetChangeLog() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getChangeLog());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getChangeLog();
  }
  
  @Test
  public void testGetInstrument() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getInstrument());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getInstrument();
  }
  
  @Test
  public void testGetInstrumentModel() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getInstrumentModel());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getInstrumentModel();
  }
  
  @Test
  public void testGetOrder() {
    makeMockClient();
    PineryClient client = new PineryClient("http://pretend.pinery.server/", true);
    assertNotNull(client.getOrder());
    
    client.close();
    exception.expect(IllegalStateException.class);
    client.getOrder();
  }
  
}
