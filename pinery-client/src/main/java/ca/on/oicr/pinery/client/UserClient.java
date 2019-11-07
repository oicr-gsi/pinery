package ca.on.oicr.pinery.client;

import ca.on.oicr.ws.dto.UserDto;
import java.util.List;

public class UserClient extends ResourceClient<UserDto> {

  public UserClient(PineryClient mainClient) {
    super(UserDto.class, UserDto[].class, mainClient);
  }

  /**
   * @return a list of all users in the database
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public List<UserDto> all() throws HttpResponseException {
    return getResourceList("users");
  }

  /**
   * Retrieves a single user by ID
   *
   * @param id LIMS ID of the user to retrieve
   * @return the user
   * @throws HttpResponseException on any HTTP Status other than 200 OK
   */
  public UserDto byId(int id) throws HttpResponseException {
    return getResource("user/" + id);
  }
}
