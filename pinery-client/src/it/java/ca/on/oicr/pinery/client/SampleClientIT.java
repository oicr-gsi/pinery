package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.pinery.client.SampleClient.SamplesFilter;
import ca.on.oicr.ws.dto.SampleDto;

public class SampleClientIT {
	
	private static PineryClient pinery;
	
	private static Integer KNOWN_SAMPLE_ID;
	private static String KNOWN_SAMPLE_NAME;
	private static String KNOWN_SAMPLE_CREATE_DATE;
	private static boolean KNOWN_SAMPLE_ARCHIVED;
	private static String KNOWN_SAMPLE_TYPE;
	
	@BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_SAMPLE_ID = props.getInt("it.sample.id");
    KNOWN_SAMPLE_NAME = props.get("it.sample.name");
    KNOWN_SAMPLE_CREATE_DATE = props.get("it.sample.createDate");
    KNOWN_SAMPLE_ARCHIVED = props.getBoolean("it.sample.archived");
    KNOWN_SAMPLE_TYPE = props.get("it.sample.type");
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<SampleDto> samples = pinery.getSample().all();
		assertTrue(samples.size() > 1);
		assertKnownSampleInList(samples);
	}
	
	@Test
	public void getById() throws HttpResponseException {
		SampleDto sample = pinery.getSample().byId(KNOWN_SAMPLE_ID);
		assertIsKnownSample(sample);
	}
	
	@Test
	public void getAllFilteredByTime() throws HttpResponseException {
		DateTime before = new DateTime(KNOWN_SAMPLE_CREATE_DATE);
		before = before.plusHours(1);
		DateTime after = before.minusDays(32);
		
		List<SampleDto> samples = pinery.getSample().allFiltered(
				new SamplesFilter()
					.withDateBefore(before)
					.withDateAfter(after)
				);
		
		assertTrue(samples.size() == 2);
		assertKnownSampleInList(samples);
	}
	
	@Test
	public void getAllFilteredByArchivedAndType() throws HttpResponseException {
		List<SampleDto> samples = pinery.getSample().allFiltered(
				new SamplesFilter()
					.withArchived(KNOWN_SAMPLE_ARCHIVED)
					.withTypes(Arrays.asList(KNOWN_SAMPLE_TYPE, "Illumina PE Library"))
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
	
}
