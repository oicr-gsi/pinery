package ca.on.oicr.pinery.ws;

import static ca.on.oicr.pinery.ws.util.PineryUtils.*;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.service.UserService;
import ca.on.oicr.pinery.ws.component.RestException;
import ca.on.oicr.ws.dto.Dtos;
import ca.on.oicr.ws.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = {"Users"})
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	@ApiOperation(value = "List all users", response = UserDto.class, responseContainer = "List")
	public List<UserDto> getUsers(UriComponentsBuilder uriBuilder) {
		List<User> users = userService.getUsers();
		List<UserDto> result = Lists.newArrayList();
		for (User user : users) {
			UserDto dto = Dtos.asDto(user);
			addUrls(dto, uriBuilder);
			result.add(dto);
		}
		return result;
	}

	@GetMapping("/user/{id}")
	@ApiOperation(value = "Find user by ID", response = UserDto.class)
	@ApiResponses({@ApiResponse(code = 404, message = "No user found")})
	public UserDto getUser(UriComponentsBuilder uriBuilder, @ApiParam(value = "ID of user to fetch") @PathVariable("id") Integer id) {
		User user = userService.getUser(id);
		if (user == null) {
		  throw new RestException(HttpStatus.NOT_FOUND, "No user found with ID: " + id);
		}
		UserDto dto = Dtos.asDto(user);
		addUrls(dto, uriBuilder);
		return dto;
	}
	
	private void addUrls(UserDto dto, UriComponentsBuilder uriBuilder) {
		URI baseUri = getBaseUri(uriBuilder);
		dto.setUrl(buildUserUrl(baseUri, dto.getId()));
		if(dto.getCreatedById() != null) {
			dto.setCreatedByUrl(buildUserUrl(baseUri, dto.getCreatedById()));
		}
		if (dto.getModifiedById() != null) {
			dto.setModifiedByUrl(buildUserUrl(baseUri, dto.getModifiedById()));
		}
	}


}
