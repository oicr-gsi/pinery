package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.RequisitionDto;
import java.util.List;

public class RequisitionClient extends ResourceClient<RequisitionDto> {

  public RequisitionClient(PineryClient mainClient) {
    super(RequisitionDto.class, RequisitionDto[].class, mainClient);
  }

  public List<RequisitionDto> all() throws HttpResponseException {
    return getResourceList("requisitions");
  }

  public RequisitionDto byId(int requisitionId) throws HttpResponseException {
    return getResource("requisition/" + requisitionId);
  }

  public RequisitionDto byName(String name) throws HttpResponseException {
    return getResource("requisition?name=" + name);
  }
}
