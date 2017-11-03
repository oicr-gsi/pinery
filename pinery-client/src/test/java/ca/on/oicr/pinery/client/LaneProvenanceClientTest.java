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

import ca.on.oicr.ws.dto.LaneProvenanceDto;

public class LaneProvenanceClientTest {

    private PineryClient pineryClientMock;
    private LaneProvenanceClient client;

    @Before
    public void setup() {
        pineryClientMock = mock(PineryClient.class);
        client = spy(new LaneProvenanceClient(pineryClientMock));
    }

    @Test
    public void testGetAll() throws HttpResponseException {
        LaneProvenanceDto lp1 = new LaneProvenanceDto();
        lp1.setLaneProvenanceId("1_1");
        LaneProvenanceDto lp2 = new LaneProvenanceDto();
        lp2.setLaneProvenanceId("1_2");
        List<LaneProvenanceDto> list = new ArrayList<>();
        list.add(lp1);
        list.add(lp2);
        doReturn(list).when(client).getResourceList("lane-provenance");

        List<LaneProvenanceDto> results = client.all();
        assertEquals(2, results.size());
        assertEquals("1_1", results.get(0).getLaneProvenanceId());
        assertEquals("1_1", results.get(0).getProvenanceId());
        assertEquals("1_2", results.get(1).getLaneProvenanceId());
        assertEquals("1_2", results.get(1).getProvenanceId());
    }

    @Test
    public void testGetAllButNoneAvailable() throws HttpResponseException {
        doReturn(new ArrayList<String>()).when(client).getResourceList("lane-provenance");
        List<LaneProvenanceDto> results = client.all();
        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test(expected = HttpResponseException.class)
    public void testGetAllBadStatus() throws HttpResponseException {
        doThrow(new HttpResponseException()).when(client).getResourceList("lane-provenance");

        client.all();
    }

}
