package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.TypeDto;
import java.util.List;

public class SampleTypeClient extends ResourceClient<TypeDto> {

  public SampleTypeClient(PineryClient mainClient) {
    super(TypeDto.class, TypeDto[].class, mainClient);
  }

  /**
   * @return a list of all sample types in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<TypeDto> all() throws HttpResponseException {
    return getResourceList("sample/types");
  }
}
