package ca.on.oicr.pinery.ws;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultLaneProvenance;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.LaneProvenanceDto;
import com.google.common.collect.Sets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/** @author mlaszloffy */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-spring-servlet.xml")
public class LaneProvenanceResourceTest extends AbstractResourceTest {

  @Autowired private WebApplicationContext wac;

  @Autowired private LaneProvenanceService laneProvenanceService;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @After
  public void resetMocks() {
    Mockito.reset(laneProvenanceService);
  }

  @Test
  public void testEmptyResultSet() throws Exception {
    List<LaneProvenance> lps = Collections.emptyList();

    when(laneProvenanceService.getLaneProvenance()).thenReturn(lps);

    mockMvc
        .perform(get("/provenance/latest/lane-provenance").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string("[ ]"))
        .andDo(print())
        .andReturn();
  }

  @Test
  public void testJodaTimeObjectMapper() throws Exception {
    List<LaneProvenance> lps = new ArrayList<>();
    LaneProvenanceDto lp = new LaneProvenanceDto();
    lp.setLastModified(ZonedDateTime.parse("2016-01-01T00:00:00Z"));
    lps.add(lp);

    when(laneProvenanceService.getLaneProvenance()).thenReturn(lps);

    mockMvc
        .perform(get("/provenance/latest/lane-provenance").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.[*].lastModified", everyItem(equalTo("2016-01-01T00:00:00Z"))));
  }

  @Test
  public void testJsonArraySize() throws Exception {
    List<LaneProvenance> lps = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      LaneProvenanceDto lp = new LaneProvenanceDto();
      lp.setLaneNumber(Integer.toString(i));
      lps.add(lp);
    }

    when(laneProvenanceService.getLaneProvenance()).thenReturn(lps);

    mockMvc
        .perform(get("/provenance/latest/lane-provenance").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(100)));
  }

  /**
   * Test that the v1 lane provenance object (and version hash) has not changed. This method should
   * be replicated for each new provenance version added
   */
  @Test
  public void testV1ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v1");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "0c342e4dd3f44daaa1c5920c3968e4fd6a803213a7a587cab0b15bb5762d1048", lp.getVersion());
  }

  @Test
  public void testV2ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v2");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "9ad4e9fdeb49b2254d91283ff380331757118bf71381a7a6851efc276910b658", lp.getVersion());
  }

  @Test
  public void testV3ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v3");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "0b348aa6bb7755a301da710794afe9fe3b5ba097f4fa7e0cf0a70922078e4e95", lp.getVersion());
  }

  @Test
  public void testV4ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v4");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "0b348aa6bb7755a301da710794afe9fe3b5ba097f4fa7e0cf0a70922078e4e95", lp.getVersion());
  }

  @Test
  public void testV5ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v5");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "8ed7a679a7027964844433713ff08119229b4c9be5d296a54c20b1a3d0594447", lp.getVersion());
  }

  @Test
  public void testV6ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v6");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "8ed7a679a7027964844433713ff08119229b4c9be5d296a54c20b1a3d0594447", lp.getVersion());
  }

  @Test
  public void testV7ProvenanceTransform() {
    VersionTransformer<LaneProvenance, ? extends LaneProvenance> transformer =
        LaneProvenanceResource.transformers.get("v7");
    LaneProvenance lp = transformer.transform(makeBaseLaneProvenance());
    // This hash must never change
    assertEquals(
        "110ea1253a65abb6253116a6caa1f9eb4b349a115429af312cc387f6a107fdf0", lp.getVersion());
  }

  private LaneProvenance makeBaseLaneProvenance() {
    DefaultLaneProvenance lp = new DefaultLaneProvenance();
    lp.setInstrumentModel(makeBaseInstrumentModel());
    lp.setInstrument(makeBaseInstrument());
    lp.setSequencerRun(makeBaseRun());
    lp.setLane(makeBaseLane());
    return lp;
  }

  private InstrumentModel makeBaseInstrumentModel() {
    InstrumentModel m = new DefaultInstrumentModel();
    m.setId(456);
    m.setName("Model");
    m.setCreated(makeDate("2018-11-01T09:54:23Z"));
    m.setCreatedById(3);
    m.setModified(makeDate("2018-11-01T09:54:23Z"));
    m.setModifiedById(3);
    return m;
  }

  private Instrument makeBaseInstrument() {
    Instrument i = new DefaultInstrument();
    i.setId(123);
    i.setName("Instrument");
    i.setModelId(456);
    i.setCreated(makeDate("2018-11-01T09:56:01Z"));
    return i;
  }

  private RunPosition makeBaseLane() {
    RunPosition l = new DefaultRunPosition();
    l.setPosition(1);
    l.setPoolName("Pool");
    l.setPoolDescription("a pool");
    l.setPoolBarcode("12345678");
    l.setPoolCreated(makeDate("2018-11-01T10:22:55Z"));
    l.setPoolCreatedById(3);
    l.setRunSample(Sets.newHashSet(makeBaseRunSample()));
    l.setAnalysisSkipped(true);
    l.setQcStatus("Failed: Other problem");
    l.setRunPurpose("Production");
    return l;
  }

  private RunSample makeBaseRunSample() {
    RunSample s = new DefaultRunSample();
    s.setId("LDI100");
    s.setBarcode("ACGTACGT");
    s.setBarcodeTwo("TGCATGCA");
    s.setRunPurpose("QC");

    Attribute tarseq = new DefaultAttribute();
    tarseq.setName("Targeted Resequencing");
    tarseq.setValue("tarseq");
    Set<Attribute> atts = Sets.newHashSet(tarseq);
    s.setAttributes(atts);

    return s;
  }

  private Run makeBaseRun() {
    Run r = new DefaultRun();
    r.setId(34);
    r.setName("Run");
    r.setState("Completed");
    r.setStartDate(makeDate("2018-11-01T00:00:00Z"));
    r.setCompletionDate(makeDate("2018-11-01T00:00:00Z"));
    r.setBarcode("ABCDEFXX");
    r.setSample(Sets.newHashSet(makeBaseLane()));
    r.setReadLength("2x101");
    r.setRunBasesMask("y101,I9,y101");
    r.setRunDirectory("/path/to/run");
    r.setInstrumentId(123);
    r.setCreatedDate(makeDate("2018-11-01T10:36:34Z"));
    r.setCreatedById(3);
    r.setModified(makeDate("2018-11-01T10:36:34Z"));
    r.setModifiedById(3);
    r.setSequencingParameters("V4 2x126");
    r.setWorkflowType("NovaSeqXp");
    r.setContainerModel("S4");
    r.setSequencingKit("SomeKit");
    return r;
  }

  private Date makeDate(String dateString) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    try {
      return formatter.parse(dateString);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Bad date format");
    }
  }
}
