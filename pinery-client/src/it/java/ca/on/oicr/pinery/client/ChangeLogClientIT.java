package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.ChangeDto;
import ca.on.oicr.ws.dto.ChangeLogDto;

public class ChangeLogClientIT {
	
	private static PineryClient pinery;
	
	private static Integer KNOWN_SAMPLE_ID;
	private static String KNOWN_CHANGE_ACTION;
	private static String KNOWN_CHANGE_DATE;
	private static String KNOWN_CHANGELOG_SAMPLE_RELATIVE_URL;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_SAMPLE_ID = props.getInt("it.changelog.sampleId");
    KNOWN_CHANGE_ACTION = props.get("it.changelog.changeAction");
    KNOWN_CHANGE_DATE = props.get("it.changelog.changeDate");
    KNOWN_CHANGELOG_SAMPLE_RELATIVE_URL = "sample//" + KNOWN_SAMPLE_ID;
  }
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<ChangeLogDto> logs = pinery.getChangeLog().all();
		assertTrue(logs.size() > 1);
		assertKnownChangeLogInList(logs);
	}
	
	@Test
	public void getForSample() throws HttpResponseException {
		ChangeLogDto log = pinery.getChangeLog().forSample(KNOWN_SAMPLE_ID);
		assertIsKnownChangeLog(log);
	}
	
	private void assertIsKnownChangeLog(ChangeLogDto log) {
		boolean changeFound = false;
		List<ChangeDto> changes = log.getChanges();
		for (ChangeDto change : changes) {
			if (KNOWN_CHANGE_ACTION.equals(change.getAction())) {
				changeFound = true;
				assertEquals(KNOWN_CHANGE_DATE, change.getCreatedDate());
				break;
			}
		}
		assertTrue(changeFound);
	}
	
	private void assertKnownChangeLogInList(List<ChangeLogDto> logs) {
		boolean logFound = false;
		for (ChangeLogDto log : logs) {
			if (log.getSampleUrl().matches(KNOWN_CHANGELOG_SAMPLE_RELATIVE_URL+ "$")) {
				logFound = true;
				assertIsKnownChangeLog(log);
				break;
			}
		}
		assertTrue(logFound);
	}

}
