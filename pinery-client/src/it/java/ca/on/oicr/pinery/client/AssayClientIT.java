package ca.on.oicr.pinery.client;

import static org.junit.Assert.*;

import ca.on.oicr.ws.dto.AssayDto;
import ca.on.oicr.ws.dto.AssayMetricDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssayClientIT {

  private static PineryClient pinery;

  private static Integer KNOWN_ASSAY_ID;
  private static String KNOWN_ASSAY_NAME;
  private static String KNOWN_ASSAY_METRIC_NAME;
  private static String KNOWN_ASSAY_METRIC_CATEGORY;
  private static String KNOWN_ASSAY_METRIC_SUBCATEGORY_NAME;

  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    ItProperties props = new ItProperties();
    pinery = props.getPineryClient();
    KNOWN_ASSAY_ID = props.getInt("it.assay.id");
    KNOWN_ASSAY_NAME = props.get("it.assay.name");
    KNOWN_ASSAY_METRIC_NAME = props.get("it.assay.metric.name");
    KNOWN_ASSAY_METRIC_CATEGORY = props.get("it.assay.metric.category");
    KNOWN_ASSAY_METRIC_SUBCATEGORY_NAME = props.get("it.assay.metric.subcategory.name");
  }

  @AfterClass
  public static void cleanUp() {
    pinery.close();
  }

  @Test
  public void getById() throws HttpResponseException {
    AssayDto assay = pinery.getAssay().byId(KNOWN_ASSAY_ID);
    assertIsKnownAssay(assay);
  }

  @Test
  public void getAll() throws HttpResponseException {
    List<AssayDto> assays = pinery.getAssay().all();
    assertTrue(assays.size() > 1);
    AssayDto knownAssay =
        assays.stream().filter(x -> KNOWN_ASSAY_ID.equals(x.getId())).findFirst().orElse(null);
    assertNotNull(knownAssay);
    assertIsKnownAssay(knownAssay);
  }

  private void assertIsKnownAssay(AssayDto assay) {
    assertEquals(KNOWN_ASSAY_ID, assay.getId());
    assertEquals(KNOWN_ASSAY_NAME, assay.getName());
    assertNotNull(assay.getMetrics());
    AssayMetricDto metric =
        assay.getMetrics().stream()
            .filter(
                x -> {
                  return KNOWN_ASSAY_METRIC_NAME.equals(x.getName())
                      && KNOWN_ASSAY_METRIC_CATEGORY.equals(x.getCategory())
                      && x.getSubcategory() != null
                      && KNOWN_ASSAY_METRIC_SUBCATEGORY_NAME.equals(x.getSubcategory().getName());
                })
            .findFirst()
            .orElse(null);
    assertNotNull(metric);
  }
}
