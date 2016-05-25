package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.LaneProvenanceDto;
import java.util.List;

public class LaneProvenanceClient extends ResourceClient<LaneProvenanceDto> {

    private static final String resourceDir = "lane-provenance/";

    public LaneProvenanceClient(PineryClient mainClient) {
        super(LaneProvenanceDto.class, LaneProvenanceDto[].class, mainClient);
    }

    /**
     * @return a list of all lane provenance
     *
     * @throws HttpResponseException on any HTTP Status other than 200 OK
     */
    public List<LaneProvenanceDto> all() throws HttpResponseException {
        return getResourceList("lane-provenance");
    }

}
