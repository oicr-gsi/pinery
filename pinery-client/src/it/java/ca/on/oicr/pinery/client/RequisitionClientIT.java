package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.RequisitionDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequisitionClientIT {

  private static PineryClient pinery;

  private static Integer KNOWN_REQUISITION_ID;
  private static String KNOWN_REQUISITION_NAME;
  private static String[] KNOWN_REQUISITION_SAMPLE_IDS;
  private static String KNOWN_REQUISITION_SIGNOFF_NAME;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_REQUISITION_ID = props.getInt("it.requisition.id");
    KNOWN_REQUISITION_NAME = props.get("it.requisition.name");
    KNOWN_REQUISITION_SAMPLE_IDS = props.get("it.requisition.sampleIds").split(",");
    KNOWN_REQUISITION_SIGNOFF_NAME = props.get("it.requisition.signOff.name");
  }

  @AfterClass
  public static void cleanUp() {
    pinery.close();
  }

  @Test
  public void getById() throws HttpResponseException {
    RequisitionDto requisition = pinery.getRequisition().byId(KNOWN_REQUISITION_ID);
    assertIsKnownRequisition(requisition);
  }

  @Test
  public void getAll() throws HttpResponseException {
    List<RequisitionDto> reqs = pinery.getRequisition().all();
    assertTrue(reqs.size() > 1);
    RequisitionDto knownReq =
        reqs.stream().filter(x -> KNOWN_REQUISITION_ID.equals(x.getId())).findFirst().orElse(null);
    assertNotNull(knownReq);
    assertIsKnownRequisition(knownReq);
  }

  private void assertIsKnownRequisition(RequisitionDto requisition) {
    assertEquals(KNOWN_REQUISITION_ID, requisition.getId());
    assertEquals(KNOWN_REQUISITION_NAME, requisition.getName());
    for (String sampleId : KNOWN_REQUISITION_SAMPLE_IDS) {
      assertTrue(requisition.getSampleIds().contains(sampleId));
    }
    assertTrue(
        requisition.getSignOffs().stream()
            .anyMatch(x -> KNOWN_REQUISITION_SIGNOFF_NAME.equals(x.getName())));
  }
}
