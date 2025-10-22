package ca.on.oicr.pinery.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunContainer;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunContainer;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.ws.dto.Dtos;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/** @author mlaszloffy */
@RunWith(MockitoJUnitRunner.class)
public class DefaultSampleProvenanceServiceTest {

  @InjectMocks
  private DefaultSampleProvenanceService sampleProvenanceService;

  @Mock
  private CacheOrLims lims;

  List<Sample> samples;
  SampleProject project;
  Sample parentSample;
  Sample sample;
  RunSample runSample;
  RunContainer container;
  RunPosition lane;
  Run run;
  OrderSample orderSample;
  Order order;
  Instrument instrument;
  InstrumentModel instrumentModel;
  ZonedDateTime runCompletionDate = ZonedDateTime.parse("2014-01-01T00:00:00.000Z");
  SampleProvenance before;

  private final String sampleId = "1";

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);

    samples = new ArrayList<>();

    project = new DefaultSampleProject();
    project.setName("TEST_PROJECT");

    String parentSampleId = "2";
    parentSample = new DefaultSample();
    parentSample.setAttributes(Collections.<Attribute>emptySet());
    parentSample.setId(parentSampleId);
    samples.add(parentSample);

    sample = new DefaultSample();
    sample.setProject("TEST_PROJECT");
    sample.setName("TEST_SAMPLE");
    sample.setAttributes(Collections.<Attribute>emptySet());
    sample.setId(sampleId);
    sample.setModified(Date.from(ZonedDateTime.parse("2015-01-01T00:00:00.000Z").toInstant()));
    samples.add(sample);

    parentSample.setChildren(ImmutableSet.of(sampleId));
    sample.setParents(ImmutableSet.of(parentSampleId));

    runSample = new DefaultRunSample();
    runSample.setId(sampleId);
    runSample.setBarcode("ATCGATCG");
    runSample.setAttributes(Collections.<Attribute>emptySet());

    lane = new DefaultRunPosition();
    lane.setPosition(1);
    lane.setRunSample(Sets.newHashSet(runSample));

    container = new DefaultRunContainer();
    container.setPositions(Sets.newHashSet(lane));

    run = new DefaultRun();
    run.setId(1);
    run.setName("ABC_123");
    run.setContainers(Sets.newHashSet(container));
    run.setCompletionDate(Date.from(runCompletionDate.toInstant()));
    run.setInstrumentId(1);

    orderSample = new DefaultOrderSample();
    orderSample.setId(sampleId);
    orderSample.setBarcode("ATCGATCG");
    orderSample.setAttributes(Collections.<Attribute>emptySet());

    order = new DefaultOrder();
    order.setSample(ImmutableSet.of(orderSample));

    instrument = new DefaultInstrument();
    instrument.setId(1);
    instrument.setModelId(1);
    instrument.setName("NOVA1");

    instrumentModel = new DefaultInstrumentModel();
    instrumentModel.setId(1);
    instrumentModel.setMultipleContainers(false);
    instrumentModel.setName("NovaSeq");

    when(lims.getSampleProjects()).thenReturn(Lists.newArrayList(project));
    when(lims.getSamples(null, null, null, null, null)).thenReturn(samples);
    when(lims.getRuns(null)).thenReturn(Lists.newArrayList(run));
    when(lims.getOrders()).thenReturn(Lists.newArrayList(order));
    when(lims.getInstruments()).thenReturn(Lists.newArrayList(instrument));
    when(lims.getInstrumentModels()).thenReturn(Lists.newArrayList(instrumentModel));
    before = Dtos.asDto(getSampleProvenanceById("1_1_1"));
  }

  @Test
  public void testVersionCalculate_sampleLastModifiedChange() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    sample.setModified(Date.from(expectedDate.toInstant()));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertEquals(runCompletionDate, after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_runLastModifiedChange() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    run.setModified(Date.from(expectedDate.toInstant()));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertEquals(runCompletionDate, after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_runSampleLastModifiedChange() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    runSample.setModified(Date.from(expectedDate.toInstant()));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertEquals(runCompletionDate, after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());
  }

  // @Test
  public void testVersionCalculate_laneLastModifiedChange() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    // lane.setModified(expectedDate.toDate());

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertEquals(runCompletionDate, after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_sampleChange() {
    String expected = "NEW_SAMPLE_NAME";
    sample.setName(expected);

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expected, after.getSampleName());
    assertNotEquals(before.getVersion(), after.getVersion());
    assertEquals(runCompletionDate, after.getCreatedDate());
  }

  @Test
  public void testVersionCalculate_projectChange() {
    String expected = "NEW_PROJECT_NAME";
    project.setName(expected);
    sample.setProject(expected);

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expected, after.getStudyTitle());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_sequencerRunChange() {
    String expected = "NEW_SEQUENCER_RUN_NAME";
    run.setName(expected);

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expected, after.getSequencerRunName());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_BarcodeChange() {
    String expected1 = "AAAA";
    runSample.setBarcode(expected1);

    SampleProvenance after1 = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expected1, after1.getIusTag());
    assertNotEquals(before.getVersion(), after1.getVersion());

    String expected2 = "TTTT";
    runSample.setBarcodeTwo(expected2);

    SampleProvenance after2 = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(expected1 + "-" + expected2, after2.getIusTag());
    assertNotEquals(after1.getVersion(), after2.getVersion());
    assertNotEquals(before.getVersion(), after2.getVersion());
  }

  @Test
  public void testVersionCalculate_laneChange() {
    int expected = 2;
    lane.setPosition(expected);

    // changing the lane would result in a change to the sampleProvenanceId
    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_2_1"));
    assertEquals(Integer.toString(expected), after.getLaneNumber());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testVersionCalculate_sampleHierarchyChange() {
    String parentExpected = "PARENT_SAMPLE";
    parentSample = new DefaultSample();
    parentSample.setName(parentExpected);
    parentSample.setId("2");
    parentSample.setAttributes(Collections.<Attribute>emptySet());
    samples.add(parentSample);
    sample.setParents(Sets.newHashSet(parentSample.getId()));

    SampleProvenance afterParent = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(parentExpected, afterParent.getRootSampleName());
    assertEquals(parentExpected, afterParent.getParentSampleName());
    assertNotEquals(before.getVersion(), afterParent.getVersion());

    String rootExpected = "ROOT_SAMPLE";
    Sample rootSample = new DefaultSample();
    rootSample.setName(rootExpected);
    rootSample.setId("3");
    rootSample.setAttributes(Collections.<Attribute>emptySet());
    samples.add(rootSample);
    parentSample.setParents(Sets.newHashSet(rootSample.getId()));

    SampleProvenance afterRoot = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(rootExpected, afterRoot.getRootSampleName());
    assertEquals(parentExpected + ":" + rootExpected, afterRoot.getParentSampleName());
    assertNotEquals(afterParent.getVersion(), afterRoot.getVersion());
    assertNotEquals(before.getVersion(), afterRoot.getVersion());
  }

  @Test
  public void testMultipleParents() {
    String parent1 = "PARENT_SAMPLE1";
    String parent2 = "PARENT_SAMPLE2";
    String root = "ROOT_SAMPLE";

    Sample p1 = new DefaultSample();
    p1.setName(parent1);
    p1.setId("10");
    p1.setAttributes(Collections.<Attribute>emptySet());
    samples.add(p1);

    Sample p2 = new DefaultSample();
    p2.setName(parent2);
    p2.setId("11");
    p2.setAttributes(Collections.<Attribute>emptySet());
    samples.add(p2);

    sample.setParents(Sets.newHashSet(p1.getId(), p2.getId()));

    Sample r = new DefaultSample();
    r.setName(root);
    r.setId("12");
    r.setAttributes(Collections.<Attribute>emptySet());
    samples.add(r);
    p1.setParents(Sets.newHashSet(r.getId()));
    p2.setParents(Sets.newHashSet(r.getId()));

    SampleProvenance afterParent = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(root, afterParent.getRootSampleName());
    assertTrue(
        (parent1 + ":" + parent2 + ":" + root).equals(afterParent.getParentSampleName())
            || (parent2 + ":" + parent1 + ":" + root).equals(afterParent.getParentSampleName()));
    assertNotEquals(before.getVersion(), afterParent.getVersion());
  }

  @Test
  public void testMultipleRootSamples() {
    String root1 = "ROOT_SAMPLE1";
    String root2 = "ROOT_SAMPLE2";

    Sample r1 = new DefaultSample();
    r1.setName(root1);
    r1.setId("10");
    r1.setAttributes(Collections.<Attribute>emptySet());
    samples.add(r1);

    Sample r2 = new DefaultSample();
    r2.setName(root2);
    r2.setId("11");
    r2.setAttributes(Collections.<Attribute>emptySet());
    samples.add(r2);

    sample.setParents(Sets.newHashSet(r1.getId(), r2.getId()));

    SampleProvenance afterParent = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertTrue(
        root2.equals(afterParent.getRootSampleName())
            || root1.equals(afterParent.getRootSampleName()));
    assertTrue(
        (root1 + ":" + root2).equals(afterParent.getParentSampleName())
            || (root2 + ":" + root1).equals(afterParent.getParentSampleName()));
    assertNotEquals(before.getVersion(), afterParent.getVersion());
  }

  @Test
  public void testSampleMaterialAttributeChange() {
    Attribute a = new DefaultAttribute();
    a.setName("Tissue Type");
    a.setValue("R");
    sample.setAttributes(Sets.newHashSet(a));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(
        "{geo_run_id_and_position=[1_1], geo_tissue_type=[R]}",
        after.getSampleAttributes().toString());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testSampleImmaterialAttributeChange() {
    Attribute a = new DefaultAttribute();
    a.setName("Not in SampleAttribute enum");
    a.setValue("...");
    sample.setAttributes(Sets.newHashSet(a));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals("{geo_run_id_and_position=[1_1]}", after.getSampleAttributes().toString());

    // Immaterial attribute change, versions should NOT change
    assertEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testRunSampleMaterialAttributeChange() {
    Attribute a = new DefaultAttribute();
    a.setName("Targeted Resequencing");
    a.setValue("123");
    runSample.setAttributes(Sets.newHashSet(a));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(
        "{geo_run_id_and_position=[1_1], geo_targeted_resequencing=[123]}",
        after.getSampleAttributes().toString());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testRunSampleImmaterialAttributeChange() {
    Attribute a = new DefaultAttribute();
    a.setName("Targeted Resequencinggggg");
    a.setValue("123");
    runSample.setAttributes(Sets.newHashSet(a));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals("{geo_run_id_and_position=[1_1]}", after.getSampleAttributes().toString());

    // Immaterial attribute change, versions should NOT change
    assertEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testTargetResequencingOrderSampleAttribute() {
    assertEquals("{geo_run_id_and_position=[1_1]}", before.getSampleAttributes().toString());

    Attribute a = new DefaultAttribute();
    a.setName("Targeted Resequencing");
    a.setValue("123");
    orderSample.setAttributes(Sets.newHashSet(a));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(
        "{geo_run_id_and_position=[1_1], geo_targeted_resequencing=[123]}",
        after.getSampleAttributes().toString());
    assertNotEquals(before.getVersion(), after.getVersion());
  }

  @Test
  public void testSampleAttributeOverrides() {
    Attribute attr1 = new DefaultAttribute();
    attr1.setName("Tissue Type");
    attr1.setValue("Blood");

    Attribute attr2 = new DefaultAttribute();
    attr2.setName("Tissue Origin");
    attr2.setValue("R");
    parentSample.setAttributes(ImmutableSet.of(attr1, attr2));

    Attribute childAttribute = new DefaultAttribute();
    childAttribute.setName("Tissue Type");
    childAttribute.setValue("Ly");
    sample.setAttributes(ImmutableSet.of(childAttribute));

    SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    assertEquals(
        "{geo_run_id_and_position=[1_1], geo_tissue_origin=[R], geo_tissue_type=[Ly]}",
        after.getSampleAttributes().toString());
  }

  private SampleProvenance getSampleProvenanceById(String sampleProvenanceId) {
    for (SampleProvenance sp : sampleProvenanceService.getSampleProvenance()) {
      if (sp.getSampleProvenanceId().equals(sampleProvenanceId)) {
        return sp;
      }
    }
    return null;
  }
}
