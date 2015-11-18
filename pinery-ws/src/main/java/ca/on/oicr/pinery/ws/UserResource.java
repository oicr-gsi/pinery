package ca.on.oicr.pinery.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.service.UserService;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.UserDto;

import com.google.common.collect.Lists;

@Component
@Path("/")
@Api(value = "user")
public class UserResource {

//	private static final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Context
	private UriInfo uriInfo;

	@Autowired
	private UserService userService;

	@GET
	@Produces({ "application/json" })
	@Path("/users")
	@ApiOperation(value = "List all users", response = UserDto.class, responseContainer = "List")
	public List<UserDto> getUsers() {
		List<User> users = userService.getUsers();
		List<UserDto> result = Lists.newArrayList();
		final URI baseUri = uriInfo.getBaseUriBuilder().path("user").build();
		for (User user : users) {
			UserDto dto = Dtos.asDto(user);
			dto.setUrl(baseUri + "/" + dto.getId().toString());
			addUsers(user, dto);
			result.add(dto);
		}
		return result;
	}

	@GET
	@Produces({ "application/json" })
	@Path("/user/{id}")
	@ApiOperation(value = "Find user by ID", response = UserDto.class)
	@ApiResponse(code = 404, message = "No user found")
	public UserDto getUser(@ApiParam(value = "ID of user to fetch", required = true) @PathParam("id") Integer id) {
		User user = userService.getUser(id);
		if (user == null) {
		  throw new NotFoundException("No user found with ID: " + id);
		}
		UserDto dto = Dtos.asDto(user);
		final URI uri = uriInfo.getAbsolutePathBuilder().build();
		dto.setUrl(uri.toString());
		addUsers(user, dto);
		return dto;
	}
	
	private void addUsers(User user, UserDto dto) {
		final URI baseUri = uriInfo.getBaseUriBuilder().path("user/").build();
		if(user.getCreatedById() != null) {
			dto.setCreatedByUrl(baseUri + user.getCreatedById().toString());
		}
		if (user.getModifiedById() != null) {
			dto.setModifiedByUrl(baseUri + user.getModifiedById().toString());
		}
	}


}
