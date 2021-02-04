package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PineryClientTest {

  @Mock private Client clientMock;
  @Mock private WebTarget targetMock;

  private PineryClient sut;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(clientMock.register(ResteasyJackson2Provider.class)).thenReturn(clientMock);
    sut = new PineryClient("http://pretend.pinery.server/", clientMock);
  }

  @After
  public void tearDown() {
    sut.close();
  }

  @Test
  public void testCallPinery() throws HttpResponseException {
    Invocation.Builder builderMock = mock(Invocation.Builder.class);
    Response responseMock = Response.ok("response").build();
    when(clientMock.target("http://pretend.pinery.server/test")).thenReturn(targetMock);
    when(targetMock.request()).thenReturn(builderMock);
    when(builderMock.get()).thenReturn(responseMock);

    Response response = sut.callPinery("test");

    assertNotNull(response);
    assertEquals(200, response.getStatus());
    assertEquals("response", response.getEntity());
  }

  @Test
  public void testCallPineryNull() throws HttpResponseException {
    Invocation.Builder builderMock = mock(Invocation.Builder.class);
    Response responseMock = Response.ok(null).build();
    when(clientMock.target("http://pretend.pinery.server/test")).thenReturn(targetMock);
    when(targetMock.request()).thenReturn(builderMock);
    when(builderMock.get()).thenReturn(responseMock);

    Response response = sut.callPinery("test");

    assertNotNull(response);
    assertEquals(200, response.getStatus());
    assertNull(response.getEntity());
  }

  @Test
  public void testCallPineryStatusNotOk() throws HttpResponseException {
    Invocation.Builder builderMock = mock(Invocation.Builder.class);
    Response responseMock = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();
    when(clientMock.target("http://pretend.pinery.server/test")).thenReturn(targetMock);
    when(targetMock.request()).thenReturn(builderMock);
    when(builderMock.get()).thenReturn(responseMock);

    assertThrows(HttpResponseException.class, () -> sut.callPinery("test"));
  }

  @Test
  public void testOpenClose() {
    assertTrue(sut.isOpen());
    assertFalse(sut.isClosed());

    sut.close();
    assertFalse(sut.isOpen());
    assertTrue(sut.isClosed());
  }

  @Test
  public void testGetSample() {
    assertNotNull(sut.getSample());
    assertNotNull(sut.getSample());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getSample());
  }

  @Test
  public void testGetSampleProject() {
    assertNotNull(sut.getSampleProject());
    assertNotNull(sut.getSampleProject());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getSampleProject());
  }

  @Test
  public void testGetSampleType() {
    assertNotNull(sut.getSampleType());
    assertNotNull(sut.getSampleType());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getSampleType());
  }

  @Test
  public void testGetSequencerRun() {
    assertNotNull(sut.getSequencerRun());
    assertNotNull(sut.getSequencerRun());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getSequencerRun());
  }

  @Test
  public void testGetUser() {
    assertNotNull(sut.getUser());
    assertNotNull(sut.getUser());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getUser());
  }

  @Test
  public void testGetAttributeName() {
    assertNotNull(sut.getAttributeName());
    assertNotNull(sut.getAttributeName());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getAttributeName());
  }

  @Test
  public void testGetChangeLog() {
    assertNotNull(sut.getChangeLog());
    assertNotNull(sut.getChangeLog());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getChangeLog());
  }

  @Test
  public void testGetInstrument() {
    assertNotNull(sut.getInstrument());
    assertNotNull(sut.getInstrument());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getInstrument());
  }

  @Test
  public void testGetInstrumentModel() {
    assertNotNull(sut.getInstrumentModel());
    assertNotNull(sut.getInstrumentModel());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getInstrumentModel());
  }

  @Test
  public void testGetOrder() {
    assertNotNull(sut.getOrder());
    assertNotNull(sut.getOrder());

    sut.close();
    assertThrows(IllegalStateException.class, () -> sut.getOrder());
  }
}
