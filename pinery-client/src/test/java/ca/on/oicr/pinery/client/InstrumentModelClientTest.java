package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.InstrumentModelDto;

public class InstrumentModelClientTest {
	
	private static final String PINERY_URL_DEFAULT = "http://localhost:8888/pinery-ws/";
	private static PineryClient pinery;
	
	private static final Integer KNOWN_MODEL_ID = 6;
	private static final String KNOWN_MODEL_NAME = "HiSeq";

	public InstrumentModelClientTest() {
		String urlArg = System.getProperty("pinery-url");
		pinery = new PineryClient(urlArg == null ? PINERY_URL_DEFAULT : urlArg);
	}
	
	@AfterClass
	public static void cleanUp() {
		pinery.close();
	}
	
	@Test
	public void getById() {
		InstrumentModelDto model = pinery.getInstrumentModel().byId(KNOWN_MODEL_ID);
		assertIsKnownInstrumentModel(model);
	}
	
	@Test
	public void getAll() {
		List<InstrumentModelDto> models = pinery.getInstrumentModel().all();
		assertTrue(models.size() > 1);
		boolean modelFound = false;
		for (InstrumentModelDto model : models) {
			if (KNOWN_MODEL_ID.equals(model.getId())) {
				modelFound = true;
				assertIsKnownInstrumentModel(model);
				break;
			}
		}
		assertTrue(modelFound);
	}
	
	private void assertIsKnownInstrumentModel(InstrumentModelDto model) {
		assertEquals(KNOWN_MODEL_ID, model.getId());
		assertEquals(KNOWN_MODEL_NAME, model.getName());
	}

}
