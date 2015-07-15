package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.pinery.client.PineryClient;
import ca.on.oicr.ws.dto.SampleDto;

public class SampleClientTest {
	
	private static final String PINERY_URL = "http://localhost:8888/pinery-ws/";
	
	private static final Integer KNOWN_SAMPLE_ID = 22;
	private static final String KNOWN_SAMPLE_NAME = "C-M";
	private static final String KNOWN_SAMPLE_CREATE_DATE = "2008-10-01T11:51:53-04:00";
	
	private static PineryClient CLIENT;
	
	public SampleClientTest() {
		CLIENT = new PineryClient(PINERY_URL);
	}
	
	@Test
	public void getAll() {
		List<SampleDto> samples = CLIENT.getSample().all();
		assertFalse(samples.isEmpty());
		
		boolean sampleFound = false;
		for (SampleDto sample : samples) {
			if (sample.getId().equals(KNOWN_SAMPLE_ID)) {
				sampleFound = true;
				assertEquals(KNOWN_SAMPLE_NAME, sample.getName());
				assertEquals(KNOWN_SAMPLE_CREATE_DATE, sample.getCreatedDate());
				break;
			}
		}
		assertTrue(sampleFound);
	}
	
	@Test
	public void getById() {
		SampleDto sample = CLIENT.getSample().byId(KNOWN_SAMPLE_ID);
		
		assertEquals(KNOWN_SAMPLE_ID, sample.getId());
		assertEquals(KNOWN_SAMPLE_NAME, sample.getName());
		assertEquals(KNOWN_SAMPLE_CREATE_DATE, sample.getCreatedDate());
	}
	
	@AfterClass
	public static void cleanUp() {
		CLIENT.close();
	}
}
