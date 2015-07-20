package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;

public class SequencerRunClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final Integer KNOWN_RUN_ID = 22;
	private static final String KNOWN_RUN_NAME = "081114_i320_30KHK_LT";
	private static final String KNOWN_RUN_BARCODE = "30KHK_1";
	private static final Integer KNOWN_RUN_LANE = 4;
	private static final Integer KNOWN_RUN_LANE_SAMPLE_ID = 131;

	public SequencerRunClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getById() throws HttpResponseException {
		RunDto run = pinery.getSequencerRun().byId(KNOWN_RUN_ID);
		assertIsKnownRun(run);
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<RunDto> runs = pinery.getSequencerRun().all();
		assertTrue(runs.size() > 1);
		boolean runFound = false;
		for (RunDto run : runs) {
			if (KNOWN_RUN_ID.equals(run.getId())) {
				runFound = true;
				assertIsKnownRun(run);
				break;
			}
		}
		assertTrue(runFound);
	}
	
	private void assertIsKnownRun(RunDto run) {
		assertEquals(KNOWN_RUN_ID, run.getId());
		assertEquals(KNOWN_RUN_NAME, run.getName());
		assertEquals(KNOWN_RUN_BARCODE, run.getBarcode());
		Set<RunDtoPosition> lanes = run.getPositions();
		boolean sampleFound = false;
		for (RunDtoPosition lane : lanes) {
			if (KNOWN_RUN_LANE.equals(lane.getPosition())) {
				Set<RunDtoSample> samples = lane.getSamples();
				for (RunDtoSample sample : samples) {
					if (KNOWN_RUN_LANE_SAMPLE_ID.equals(sample.getId())) {
						sampleFound = true;
						break;
					}
				}
				break;
			}
		}
		assertTrue(sampleFound);
	}

}
