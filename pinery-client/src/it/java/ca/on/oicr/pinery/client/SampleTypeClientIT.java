package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.TypeDto;

public class SampleTypeClientIT {
	
	private static PineryClient pinery;
	
	private static String KNOWN_TYPE_NAME;
	private static String KNOWN_TYPE_EARLIEST;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_TYPE_NAME = props.get("it.sampleType.name");
    KNOWN_TYPE_EARLIEST = props.get("it.sampleType.earliest");
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<TypeDto> types = pinery.getSampleType().all();
		assertTrue(types.size() > 1);
		boolean typeFound = false;
		for (TypeDto type : types) {
			if (KNOWN_TYPE_NAME.equals(type.getName())) {
				typeFound = true;
				assertEquals(KNOWN_TYPE_EARLIEST, type.getEarliest());
				break;
			}
		}
		assertTrue(typeFound);
	}

}
