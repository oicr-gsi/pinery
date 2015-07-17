package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.ChangeDto;
import ca.on.oicr.ws.dto.ChangeLogDto;

public class ChangeLogClientTest {
	
	private String pineryUrl = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final Integer KNOWN_SAMPLE_ID = 2204;
	private static final String KNOWN_CHANGE_ACTION = "storage_location: J_H_x_y_z_oicr00000872_A10 -> E_D_1_1_6_oicr00000872_A10";
	private static final String KNOWN_CHANGE_DATE = "2010-08-27T15:56:28-04:00";
	private static final String KNOWN_CHANGELOG_SAMPLE_RELATIVE_URL = "sample/2204";
	
	private String knownSampleUrl;
	
	public ChangeLogClientTest() {
		String urlArg = System.getProperty("pinery-url");
		if (urlArg != null) pineryUrl = urlArg;
		pinery = new PineryClient(pineryUrl);
		knownSampleUrl = pineryUrl + (pineryUrl.endsWith("/") ? "" : "/") + KNOWN_CHANGELOG_SAMPLE_RELATIVE_URL;
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() {
		List<ChangeLogDto> logs = pinery.getChangeLog().all();
		assertTrue(logs.size() > 1);
		assertKnownChangeLogInList(logs);
	}
	
	@Test
	public void getForSample() {
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
			if (knownSampleUrl.equals(log.getSampleUrl())) {
				logFound = true;
				assertIsKnownChangeLog(log);
				break;
			}
		}
		assertTrue(logFound);
	}

}
