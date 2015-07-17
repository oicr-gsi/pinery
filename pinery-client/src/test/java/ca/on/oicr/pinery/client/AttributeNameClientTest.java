package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.AttributeNameDto;

public class AttributeNameClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final String KNOWN_ATTRIBUTE_NAME = "Tissue Origin";
	private static final String KNOWN_ATTRIBUTE_EARLIEST = "2010-02-12T12:12:59-05:00";

	public AttributeNameClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() {
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
