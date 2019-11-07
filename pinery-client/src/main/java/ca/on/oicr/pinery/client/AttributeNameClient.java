package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.AttributeNameDto;
import java.util.List;

public class AttributeNameClient extends ResourceClient<AttributeNameDto> {

  public AttributeNameClient(PineryClient mainClient) {
    super(AttributeNameDto.class, AttributeNameDto[].class, mainClient);
  }

  /**
   * @return a list of all attribute names in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK.
   */
  public List<AttributeNameDto> all() throws HttpResponseException {
    return getResourceList("sample/attributenames");
  }
}
