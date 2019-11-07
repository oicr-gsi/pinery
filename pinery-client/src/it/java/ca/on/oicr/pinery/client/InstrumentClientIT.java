package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.InstrumentDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class InstrumentClientIT {

  private static PineryClient pinery;

  private static Integer KNOWN_INSTRUMENT_ID;
  private static Integer KNOWN_INSTRUMENT_MODEL_ID;
  private static String KNOWN_INSTRUMENT_NAME;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_INSTRUMENT_ID = props.getInt("it.instrument.id");
    KNOWN_INSTRUMENT_MODEL_ID = props.getInt("it.instrument.modelId");
    KNOWN_INSTRUMENT_NAME = props.get("it.instrument.name");
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
