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
import org.junit.Test;

import ca.on.oicr.ws.dto.SampleProvenanceDto;

public class SampleProvenanceClientTest {

    private PineryClient pineryClientMock;
    private SampleProvenanceClient client;

    @Before
    public void setup() {
        pineryClientMock = mock(PineryClient.class);
        client = spy(new SampleProvenanceClient(pineryClientMock));
    }

    @Test
    public void testGetAll() throws HttpResponseException {
        SampleProvenanceDto sp1 = new SampleProvenanceDto();
        sp1.setSampleProvenanceId("111");
        SampleProvenanceDto sp2 = new SampleProvenanceDto();
        sp2.setSampleProvenanceId("222");
        List<SampleProvenanceDto> list = new ArrayList<>();
        list.add(sp1);
        list.add(sp2);
        doReturn(list).when(client).getResourceList("sample-provenance");

        List<SampleProvenanceDto> results = client.all();
        assertEquals(2, results.size());
        assertEquals("111", results.get(0).getSampleProvenanceId());
        assertEquals("111", results.get(0).getProvenanceId());
        assertEquals("222", results.get(1).getSampleProvenanceId());
        assertEquals("222", results.get(1).getProvenanceId());
    }

    @Test
    public void testGetAllButNoneAvailable() throws HttpResponseException {
        doReturn(new ArrayList<String>()).when(client).getResourceList("sample-provenance");
        List<SampleProvenanceDto> results = client.all();
        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test(expected = HttpResponseException.class)
    public void testGetAllBadStatus() throws HttpResponseException {
        doThrow(new HttpResponseException()).when(client).getResourceList("sample-provenance");

        client.all();
    }

}
