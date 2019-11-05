package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.LaneProvenanceDto;
import java.util.List;

public class LaneProvenanceClient extends ResourceClient<LaneProvenanceDto> {

  public LaneProvenanceClient(PineryClient mainClient) {
    super(LaneProvenanceDto.class, LaneProvenanceDto[].class, mainClient);
  }

  /**
   * @return a list of all lane provenance in the latest provenance version
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<LaneProvenanceDto> latest() throws HttpResponseException {
    return getResourceList("provenance/latest/lane-provenance");
  }

  /**
   * @return a list of all lane provenance in the specified provenance version
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<LaneProvenanceDto> version(String version) throws HttpResponseException {
    return getResourceList(
        version == null ? "lane-provenance" : "provenance/" + version + "/lane-provenance");
  }
}
