package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.BoxDto;
import java.util.List;

public class BoxClient extends ResourceClient<BoxDto> {

  public BoxClient(PineryClient mainClient) {
    super(BoxDto.class, BoxDto[].class, mainClient);
  }

  /**
   * @return a list of all boxes in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<BoxDto> all() throws HttpResponseException {
    return getResourceList("boxes");
  }
}
