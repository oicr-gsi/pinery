package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.AssayDto;
import java.util.List;

public class AssayClient extends ResourceClient<AssayDto> {

  public AssayClient(PineryClient mainClient) {
    super(AssayDto.class, AssayDto[].class, mainClient);
  }

  public List<AssayDto> all() throws HttpResponseException {
    return getResourceList("assays");
  }

  public AssayDto byId(int assayId) throws HttpResponseException {
    return getResource("assay/" + assayId);
  }
}
