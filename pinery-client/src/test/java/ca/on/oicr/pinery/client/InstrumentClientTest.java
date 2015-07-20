package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.InstrumentDto;

public class InstrumentClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final Integer KNOWN_INSTRUMENT_ID = 205213;
	private static final Integer KNOWN_INSTRUMENT_MODEL_ID = 6;
	private static final String KNOWN_INSTRUMENT_NAME = "D00353";
	
	public InstrumentClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getById() throws HttpResponseException {
		InstrumentDto instrument = pinery.getInstrument().byId(KNOWN_INSTRUMENT_ID);
		assertIsKnownInstrument(instrument);
	}
	
	@Test
	public void getByModel() throws HttpResponseException {
		List<InstrumentDto> instruments = pinery.getInstrument().byModel(KNOWN_INSTRUMENT_MODEL_ID);
		assertKnownInstrumentInList(instruments);
	}
	
	@Test
	public void getAll() throws HttpResponseException {
		List<InstrumentDto> instruments = pinery.getInstrument().all();
		assertTrue(instruments.size() > 1);
		assertKnownInstrumentInList(instruments);
	}
	
	private void assertIsKnownInstrument(InstrumentDto instrument) {
		assertEquals(KNOWN_INSTRUMENT_ID, instrument.getId());
		assertEquals(KNOWN_INSTRUMENT_NAME, instrument.getName());
	}
	
	private void assertKnownInstrumentInList(List<InstrumentDto> instruments) {
		boolean instrumentFound = false;
		for (InstrumentDto instrument : instruments) {
			if (KNOWN_INSTRUMENT_ID.equals(instrument.getId())) {
				instrumentFound = true;
				assertIsKnownInstrument(instrument);
				break;
			}
		}
		assertTrue(instrumentFound);
	}

}
