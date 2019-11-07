package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.SampleProjectDto;
import java.util.List;

public class ProjectClient extends ResourceClient<SampleProjectDto> {

  public ProjectClient(PineryClient mainClient) {
    super(SampleProjectDto.class, SampleProjectDto[].class, mainClient);
  }

  /**
   * @return a list of all sample projects in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<SampleProjectDto> all() throws HttpResponseException {
    return getResourceList("sample/projects");
  }
}
