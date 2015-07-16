package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.pinery.client.PineryClient;
import ca.on.oicr.pinery.client.SampleClient.SamplesFilter;
import ca.on.oicr.ws.dto.SampleDto;

public class SampleClientTest {
	
	private static final String PINERY_URL = "http://localhost:8888/pinery-ws/";
	
	private static final Integer KNOWN_SAMPLE_ID = 22;
	private static final String KNOWN_SAMPLE_NAME = "C-M";
	private static final String KNOWN_SAMPLE_CREATE_DATE = "2008-10-01T11:51:53-04:00";
	private static final boolean KNOWN_SAMPLE_ARCHIVED = false;
	private static final String KNOWN_SAMPLE_TYPE = "Default";
	
	private static PineryClient CLIENT;
	
	public SampleClientTest() {
		CLIENT = new PineryClient(PINERY_URL);
	}
	
	@Test
	public void getAll() {
		List<SampleDto> samples = CLIENT.getSample().all();
		assertKnownSampleInList(samples);
	}
	
	@Test
	public void getById() {
		SampleDto sample = CLIENT.getSample().byId(KNOWN_SAMPLE_ID);
		assertIsKnownSample(sample);
	}
	
	@Test
	public void getAllFilteredByTime() {
		DateTime before = new DateTime(KNOWN_SAMPLE_CREATE_DATE);
		before = before.plusHours(1);
		DateTime after = before.minusHours(2);
		
		List<SampleDto> samples = CLIENT.getSample().allFiltered(
				new SamplesFilter()
					.withDateBefore(before)
					.withDateAfter(after)
				);
		
		assertTrue(samples.size() == 8);
		assertKnownSampleInList(samples);
	}
	
	@Test
	public void getAllFilteredByArchivedAndType() {
		List<SampleDto> samples = CLIENT.getSample().allFiltered(
				new SamplesFilter()
					.withArchived(KNOWN_SAMPLE_ARCHIVED)
					.withTypes(Arrays.asList(KNOWN_SAMPLE_TYPE, "mRNA"))
				);
		
		assertKnownSampleInList(samples);
	}
	
	private void assertIsKnownSample(SampleDto sample) {
		assertEquals(KNOWN_SAMPLE_ID, sample.getId());
		assertEquals(KNOWN_SAMPLE_NAME, sample.getName());
		assertEquals(KNOWN_SAMPLE_CREATE_DATE, sample.getCreatedDate());
	}
	
	private void assertKnownSampleInList(List<SampleDto> samples) {
		boolean sampleFound = false;
		for (SampleDto sample : samples) {
			if (sample.getId().equals(KNOWN_SAMPLE_ID)) {
				sampleFound = true;
				assertIsKnownSample(sample);
				break;
			}
		}
		assertTrue(sampleFound);
	}
	
	@AfterClass
	public static void cleanUp() {
		CLIENT.close();
	}
}
