package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.SampleProjectDto;

public class SampleProjectClientIT {

	private static PineryClient pinery;
	
	private static String KNOWN_PROJECT_NAME;
	private static String KNOWN_PROJECT_EARLIEST;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_PROJECT_NAME = props.get("it.project.name");
    KNOWN_PROJECT_EARLIEST = props.get("it.project.earliest");
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
