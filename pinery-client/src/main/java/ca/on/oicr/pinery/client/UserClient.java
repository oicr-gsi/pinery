package ca.on.oicr.pinery.client;

import java.util.List;

import ca.on.oicr.ws.dto.UserDto;


public class UserClient extends ResourceClient<UserDto> {

	public UserClient(PineryClient mainClient) {
		super(UserDto.class, UserDto[].class, mainClient);
	}
	
	public List<UserDto> all() throws HttpResponseException {
		return getResourceList("users");
	}
	
	public UserDto byId(int id) throws HttpResponseException {
		return getResource("user/"+id);
	}

}
