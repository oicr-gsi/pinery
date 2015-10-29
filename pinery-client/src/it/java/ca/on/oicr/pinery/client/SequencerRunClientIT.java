package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.RunDto;
import ca.on.oicr.ws.dto.RunDtoPosition;
import ca.on.oicr.ws.dto.RunDtoSample;

public class SequencerRunClientIT {
	
	private static PineryClient pinery;
	
	private static Integer KNOWN_RUN_ID;
	private static String KNOWN_RUN_NAME;
	private static String KNOWN_RUN_BARCODE;
	private static Integer KNOWN_RUN_LANE;
	private static Integer KNOWN_RUN_LANE_SAMPLE_ID;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_RUN_ID = props.getInt("it.run.id");
    KNOWN_RUN_NAME = props.get("it.run.name");
    KNOWN_RUN_BARCODE = props.get("it.run.barcode");
    KNOWN_RUN_LANE = props.getInt("it.run.lane.number");
    KNOWN_RUN_LANE_SAMPLE_ID = props.getInt("it.run.lane.sampleId");
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
  public void getByName() throws HttpResponseException {
    RunDto run = pinery.getSequencerRun().byName(KNOWN_RUN_NAME);
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
