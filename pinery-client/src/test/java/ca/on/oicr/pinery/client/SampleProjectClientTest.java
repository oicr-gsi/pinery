package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import ca.on.oicr.ws.dto.SampleProjectDto;

@Ignore
public class SampleProjectClientTest {

	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final String KNOWN_PROJECT_NAME = "ASHPC";
	private static final String KNOWN_PROJECT_EARLIEST = "2012-12-10T10:14:54-05:00";
	
	public SampleProjectClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<SampleProjectDto> projects = pinery.getSampleProject().all();
		assertTrue(projects.size() > 1);
		
		boolean projectFound = false;
		for (SampleProjectDto project : projects) {
			if (KNOWN_PROJECT_NAME.equals(project.getName())) {
				projectFound = true;
				assertEquals(KNOWN_PROJECT_EARLIEST, project.getEarliest());
				break;
			}
		}
		assertTrue(projectFound);
	}

}
