package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.TypeDto;

public class SampleTypeClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final String KNOWN_TYPE_NAME = "Illumina PE Library";
	private static final String KNOWN_TYPE_EARLIEST = "2010-08-03T07:40:59-04:00";
	
	public SampleTypeClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
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
