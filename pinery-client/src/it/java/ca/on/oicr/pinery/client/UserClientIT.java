package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.UserDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserClientIT {

  private static PineryClient pinery;

  private static Integer KNOWN_USER_ID;
  private static String KNOWN_USER_LAST_NAME;
  private static String KNOWN_USER_CREATED;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_USER_ID = props.getInt("it.user.id");
    KNOWN_USER_LAST_NAME = props.get("it.user.lastName");
    KNOWN_USER_CREATED = props.get("it.user.createDate");
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
