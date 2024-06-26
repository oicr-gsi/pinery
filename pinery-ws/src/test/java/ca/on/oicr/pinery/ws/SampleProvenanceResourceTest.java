package ca.on.oicr.pinery.ws;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Status;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultPreparationKit;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultSampleProvenance;
import ca.on.oicr.pinery.lims.DefaultStatus;
import ca.on.oicr.pinery.lims.LimsSampleAttribute;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.pinery.ws.util.VersionTransformer;
import ca.on.oicr.ws.dto.SampleProvenanceDto;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/** @author mlaszloffy */
public class SampleProvenanceResourceTest extends AbstractResourceTest {

  @Autowired // mock defined in TestConfig class
  private SampleProvenanceService sampleProvenanceService;

  @Test
  public void testEmptyResultSet() throws Exception {
    List<SampleProvenance> sps = Collections.emptyList();

    when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

    getMockMvc()
        .perform(
            get("/provenance/latest/sample-provenance").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("[ ]"))
        .andDo(print())
        .andReturn();
  }

  @Test
  public void testJodaTimeObjectMapper() throws Exception {
    List<SampleProvenance> sps = new ArrayList<>();
    SampleProvenanceDto sp = new SampleProvenanceDto();
    sp.setLastModified(ZonedDateTime.parse("2016-01-01T00:00:00.000Z"));
    sps.add(sp);

    when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

    getMockMvc()
        .perform(
            get("/provenance/latest/sample-provenance").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.[*].lastModified", everyItem(equalTo("2016-01-01T00:00:00Z"))));
  }

  @Test
  public void testJsonArraySize() throws Exception {
    List<SampleProvenance> sps = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SampleProvenanceDto sp = new SampleProvenanceDto();
      sps.add(sp);
    }

    when(sampleProvenanceService.getSampleProvenance()).thenReturn(sps);

    getMockMvc()
        .perform(
            get("/provenance/latest/sample-provenance").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(100)));
  }

  /**
   * Test that the v1 sample provenance object (and version hash) has not changed.
   * This method
   * should be replicated for each new provenance version added
   */
  @Test
  public void testV1ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v1");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "c06f3bd04de538704689c77a95353c5e1ba3b512e53382662267703159bc65f7", sp.getVersion());
  }

  @Test
  public void testV2ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v2");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "1ae45fa6e7a3160f0df1f1899449bf043efa415ca6f8e1617b5329ef707e15f5", sp.getVersion());
  }

  @Test
  public void testV3ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v3");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "7cbb2609e0a387d90d8a3a754dcd131f7f013aa6df9d74a21c70a39b3b4330af", sp.getVersion());
  }

  @Test
  public void testV4ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v4");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "6afbb7e2e805eb185702d59b40c191600f5e4ae28f8283d20884552a43b31576", sp.getVersion());
  }

  @Test
  public void testV5ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v5");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "57423efd8747ea9c32d61511e6a9e6a9c11ef7601a8806c1bfb5153174a72db3", sp.getVersion());
  }

  @Test
  public void testV6ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v6");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "0c28a978c36929861383b39b584a2e5291e5a8494b79ee71cc19fb71174a36c8", sp.getVersion());
  }

  @Test
  public void testV7ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v7");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "1b29d66ed00c9476d78aba3072459b9a7d2763bbc97502820f960c5f5cb97e1f", sp.getVersion());
  }

  @Test
  public void testV8ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v8");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "27e0beaf1caf31127329a2d82792b82eb647fb9161d97a6968f0f9f8474ada1a", sp.getVersion());
  }

  @Test
  public void testV9ProvenanceTransform() {
    VersionTransformer<SampleProvenance, ? extends SampleProvenance> transformer = SampleProvenanceResource.transformers
        .get("v9");
    SampleProvenance sp = transformer.transform(makeBaseSampleProvenance());
    // This hash must never change
    assertEquals(
        "a0aa98fcc0a800dc02a3f8d3d3bf725c232d34c03e5f4e69ea35e4a1d0b8a2d6", sp.getVersion());
  }

  private SampleProvenance makeBaseSampleProvenance() {
    DefaultSampleProvenance sp = new DefaultSampleProvenance();

    sp.setSample(makeBaseSample());
    sp.setRunSample(makeBaseRunSample());
    sp.setLane(makeBaseLane());
    sp.setSequencerRun(makeBaseRun());
    sp.setInstrument(makeBaseInstrument());
    sp.setInstrumentModel(makeBaseInstrumentModel());
    sp.setSampleProject(makeBaseProject());
    sp.setAdditionalSampleAttributes(
        ImmutableMap.<LimsSampleAttribute, Set<String>>of(
            LimsSampleAttribute.TARGETED_RESEQUENCING, ImmutableSet.of("tarseq")));
    sp.setParentSamples(makeBaseSampleParents());

    return sp;
  }

  private Sample makeBaseSample() {
    Sample s = new DefaultSample();
    s.setId("LDI100");
    s.setName("Dilution");
    s.setArchived(false);
    s.setConcentration(12.34F);
    s.setVolume(23.45F);
    s.setDescription("a dilution");
    s.setProject("PROJ");
    s.setSampleType("Illumina PE Library Seq");
    s.setStorageLocation("Over there");
    s.setTubeBarcode("11112222");
    s.setParents(Sets.newHashSet("LIB100"));

    PreparationKit kit = new DefaultPreparationKit();
    kit.setName("Kit");
    kit.setDescription("a kit");
    s.setPreparationKit(kit);

    Status status = new DefaultStatus();
    status.setName("Ready");
    status.setState("Ready");
    s.setStatus(status);

    // Should include all attributes from LimsSampleAttribute enum except
    // TEMPLATE_TYPE, RUN_ID_AND_POSITION, PREPARATION_KIT, RUN_PURPOSE: constructed
    // separately
    // LIBRARY_SIZE: doesn't seem to actually be set anywhere
    Set<Attribute> attrs = Sets.newHashSet();
    attrs.add(makeAttribute("Tissue Type", "P"));
    attrs.add(makeAttribute("Tissue Origin", "Ly"));
    attrs.add(makeAttribute("Organism", "Homo sapiens"));
    attrs.add(makeAttribute("Source Template Type", "EX"));
    attrs.add(makeAttribute("STR", "Not Submitted"));
    attrs.add(makeAttribute("Group ID", "33"));
    attrs.add(makeAttribute("Group Description", "thirty three"));
    attrs.add(makeAttribute("Tissue Preparation", "FFPE"));
    attrs.add(makeAttribute("Qubit (ng/uL)", "11.22"));
    attrs.add(makeAttribute("Region", "someplace"));
    attrs.add(makeAttribute("Nanodrop (ng/uL)", "22.33"));
    attrs.add(makeAttribute("Receive Date", "2018-11-01"));
    attrs.add(makeAttribute("External Name", "Sammy"));
    attrs.add(makeAttribute("Tube ID", "Samsam"));
    attrs.add(makeAttribute("Targeted Resequencing", "tarseq"));
    attrs.add(makeAttribute("Purpose", "Library"));
    attrs.add(makeAttribute("qPCR %", "50"));
    attrs.add(makeAttribute("Subproject", "Sub"));
    attrs.add(makeAttribute("Institute", "Institute"));
    attrs.add(makeAttribute("UMIs", "True"));
    attrs.add(makeAttribute("RIN", "5.7"));
    attrs.add(makeAttribute("DV200", "91.7"));
    attrs.add(makeAttribute("Sex", "FEMALE"));
    attrs.add(makeAttribute("Target Cell Recovery", "56.78"));
    attrs.add(makeAttribute("Cell Viability", "98.76"));
    attrs.add(makeAttribute("Spike-In", "ERCC Mix 1"));
    attrs.add(makeAttribute("Spike-In Dilution Factor", "1:1000"));
    attrs.add(makeAttribute("Spike-In-Volume (uL)", "12.34"));
    attrs.add(makeAttribute("Sequencing Control Type", "Positive"));
    attrs.add(makeAttribute("Barcode Kit", "Kit xyz"));
    attrs.add(makeAttribute("Timepoint", "first collection"));
    attrs.add(makeAttribute("Batch ID", "2023-09-21_u1_s1_k1-234"));
    s.setAttributes(attrs);

    return s;
  }

  private Attribute makeAttribute(String name, String value) {
    Attribute attr = new DefaultAttribute();
    attr.setName(name);
    attr.setValue(value);
    return attr;
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

  private Instrument makeBaseInstrument() {
    Instrument i = new DefaultInstrument();
    i.setId(123);
    i.setName("Instrument");
    i.setModelId(456);
    i.setCreated(makeDate("2018-11-01T09:56:01Z"));
    return i;
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

  private SampleProject makeBaseProject() {
    SampleProject p = new DefaultSampleProject();
    p.setName("PROJ");
    p.setCount(10);
    p.setArchivedCount(2);
    p.setEarliest(makeDate("2018-10-01T09:30:41Z"));
    p.setLatest(makeDate("2018-11-01T10:09:12Z"));
    p.setActive(true);
    return p;
  }

  private Date makeDate(String dateString) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    try {
      return formatter.parse(dateString);
    } catch (ParseException e) {
      throw new IllegalArgumentException("Bad date format");
    }
  }

  private List<Sample> makeBaseSampleParents() {
    List<Sample> parents = Lists.newArrayList(
        makeEmptySample("library"),
        makeEmptySample("aliquot"),
        makeEmptySample("stock"),
        makeEmptySample("tissue"),
        makeEmptySample("identity"));
    return parents;
  }

  private Sample makeEmptySample(String name) {
    Sample s = new DefaultSample();
    s.setName(name);
    return s;
  }
}
