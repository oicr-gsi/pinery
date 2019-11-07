package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.AttributeNameDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AttributeNameClientIT {

  private static PineryClient pinery;

  private static String KNOWN_ATTRIBUTE_NAME;
  private static String KNOWN_ATTRIBUTE_EARLIEST;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_ATTRIBUTE_NAME = props.get("it.attribute.name");
    KNOWN_ATTRIBUTE_EARLIEST = props.get("it.attribute.earliest");
  }

  @AfterClass
  public static void cleanUp() {
    pinery.close();
  }

  @Test
  public void getAll() throws HttpResponseException {
    List<AttributeNameDto> attributes = pinery.getAttributeName().all();
    assertTrue(attributes.size() > 1);
    boolean attributeFound = false;
    for (AttributeNameDto attribute : attributes) {
      if (KNOWN_ATTRIBUTE_NAME.equals(attribute.getName())) {
        attributeFound = true;
        assertEquals(KNOWN_ATTRIBUTE_EARLIEST, attribute.getEarliest());
        break;
      }
    }
    assertTrue(attributeFound);
  }
}
