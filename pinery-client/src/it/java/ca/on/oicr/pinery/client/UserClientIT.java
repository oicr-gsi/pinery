package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.UserDto;

public class UserClientIT {
	
	private static PineryClient pinery;
	
	private static final Integer KNOWN_USER_ID = 105;
	private static final String KNOWN_USER_LAST_NAME = "Cooke";
	private static final String KNOWN_USER_CREATED = "2015-07-14T10:51:36-04:00";
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getById() throws HttpResponseException {
		UserDto user = pinery.getUser().byId(KNOWN_USER_ID);
		assertIsKnownUser(user);
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<UserDto> users = pinery.getUser().all();
		assertTrue(users.size() > 1);
		boolean userFound = false;
		for (UserDto user : users) {
			if (KNOWN_USER_ID.equals(user.getId())) {
				userFound = true;
				assertIsKnownUser(user);
				break;
			}
		}
		assertTrue(userFound);
	}
	
	private void assertIsKnownUser(UserDto user) {
		assertEquals(KNOWN_USER_ID, user.getId());
		assertEquals(KNOWN_USER_LAST_NAME, user.getLastname());
		assertEquals(KNOWN_USER_CREATED, user.getCreatedDate());
	}

}
