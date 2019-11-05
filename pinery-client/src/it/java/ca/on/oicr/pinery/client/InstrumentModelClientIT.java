package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.InstrumentModelDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class InstrumentModelClientIT {

  private static PineryClient pinery;

  private static Integer KNOWN_MODEL_ID;
  private static String KNOWN_MODEL_NAME;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_MODEL_ID = props.getInt("it.instrumentModel.id");
    KNOWN_MODEL_NAME = props.get("it.instrumentModel.name");
  }

  @AfterClass
  public static void cleanUp() {
    pinery.close();
  }

  @Test
  public void getById() throws HttpResponseException {
    InstrumentModelDto model = pinery.getInstrumentModel().byId(KNOWN_MODEL_ID);
    assertIsKnownInstrumentModel(model);
  }

  @Test
  public void getAll() throws HttpResponseException {
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
