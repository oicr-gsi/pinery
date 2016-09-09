package ca.on.oicr.pinery.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.pinery.lims.DefaultRunSample;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.ws.dto.Dtos;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import static org.junit.Assert.assertNull;

/**
 *
 * @author mlaszloffy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class DefaultSampleProvenanceServiceTest {

    @Autowired
    private SampleProvenanceService sampleProvenanceService;

    @Autowired
    private Lims lims;

    List<Sample> samples;
    SampleProject project;
    Sample sample;
    RunSample runSample;
    RunPosition lane;
    Run run;
    DateTime runCompletionDate = DateTime.parse("2014-01-01T00:00:00.000Z");
    SampleProvenance before;

    @Before
    public void setup() {
        samples = new ArrayList<>();

        project = new DefaultSampleProject();
        project.setName("TEST_PROJECT");

        sample = new DefaultSample();
        sample.setName("TEST_SAMPLE");
        sample.setProject("TEST_PROJECT");
        sample.setAttributes(Collections.<Attribute> emptySet());
        sample.setId("1");
        sample.setModified(DateTime.parse("2015-01-01T00:00:00.000Z").toDate());
        samples.add(sample);

        runSample = new DefaultRunSample();
        runSample.setId("1");
        runSample.setAttributes(Collections.<Attribute> emptySet());

        lane = new DefaultRunPosition();
        lane.setPosition(1);
        lane.setRunSample(Sets.newHashSet(runSample));

        run = new DefaultRun();
        run.setId(1);
        run.setName("ABC_123");
        run.setSample(Sets.newHashSet(lane));
        run.setCompletionDate(runCompletionDate.toDate());

        when(lims.getSampleProjects()).thenReturn(Lists.newArrayList(project));
        when(lims.getSamples(null, null, null, null, null)).thenReturn(samples);
        when(lims.getRuns()).thenReturn(Lists.newArrayList(run));

        before = Dtos.asDto(getSampleProvenanceById("1_1_1"));
    }

    @Test
    public void testVersionCalculate_sampleLastModifiedChange() {
        DateTime expectedDate = DateTime.parse("2016-01-01T00:00:00.000Z");
        sample.setModified(expectedDate.toDate());

        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals(expectedDate, after.getLastModified());
        assertEquals(runCompletionDate, after.getCreatedDate());

        //modification date should not change the version
        assertEquals(before.getVersion(), after.getVersion());
    }

    @Test
    public void testVersionCalculate_runLastModifiedChange() {
        DateTime expectedDate = DateTime.parse("2016-01-01T00:00:00.000Z");
        run.setModified(expectedDate.toDate());

        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals(expectedDate, after.getLastModified());
        assertEquals(runCompletionDate, after.getCreatedDate());

        //modification date should not change the version
        assertEquals(before.getVersion(), after.getVersion());
    }

    @Test
    public void testVersionCalculate_runSampleLastModifiedChange() {
        DateTime expectedDate = DateTime.parse("2016-01-01T00:00:00.000Z");
        runSample.setModified(expectedDate.toDate());

        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals(expectedDate, after.getLastModified());
        assertEquals(runCompletionDate, after.getCreatedDate());

        //modification date should not change the version
        assertEquals(before.getVersion(), after.getVersion());
    }

    //@Test
    public void testVersionCalculate_laneLastModifiedChange() {
        DateTime expectedDate = DateTime.parse("2016-01-01T00:00:00.000Z");
        //lane.setModified(expectedDate.toDate());

        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals(expectedDate, after.getLastModified());
        assertEquals(runCompletionDate, after.getCreatedDate());

        //modification date should not change the version
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

        //changing the lane would result in a change to the sampleProvenanceId
        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_2_1"));
        assertEquals(Integer.toString(expected), after.getLaneNumber());
        assertNotEquals(before.getVersion(), after.getVersion());
    }

    @Test
    public void testVersionCalculate_sampleHierarchyChange() {
        String parentExpected = "PARENT_SAMPLE";
        Sample parentSample = new DefaultSample();
        parentSample.setName(parentExpected);
        parentSample.setId("2");
        parentSample.setAttributes(Collections.<Attribute> emptySet());
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
        rootSample.setAttributes(Collections.<Attribute> emptySet());
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
        p1.setAttributes(Collections.<Attribute> emptySet());
        samples.add(p1);

        Sample p2 = new DefaultSample();
        p2.setName(parent2);
        p2.setId("11");
        p2.setAttributes(Collections.<Attribute> emptySet());
        samples.add(p2);

        sample.setParents(Sets.newHashSet(p1.getId(), p2.getId()));

        Sample r = new DefaultSample();
        r.setName(root);
        r.setId("12");
        r.setAttributes(Collections.<Attribute> emptySet());
        samples.add(r);
        p1.setParents(Sets.newHashSet(r.getId()));
        p2.setParents(Sets.newHashSet(r.getId()));

        SampleProvenance afterParent = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals(root, afterParent.getRootSampleName());
        assertTrue((parent1 + ":" + parent2 + ":" + root).equals(afterParent.getParentSampleName())
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
        r1.setAttributes(Collections.<Attribute> emptySet());
        samples.add(r1);

        Sample r2 = new DefaultSample();
        r2.setName(root2);
        r2.setId("11");
        r2.setAttributes(Collections.<Attribute> emptySet());
        samples.add(r2);

        sample.setParents(Sets.newHashSet(r1.getId(), r2.getId()));

        SampleProvenance afterParent = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertTrue(root2.equals(afterParent.getRootSampleName())
            || root1.equals(afterParent.getRootSampleName()));
        assertTrue((root1 + ":" + root2).equals(afterParent.getParentSampleName())
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
        assertEquals("{geo_run_id_and_position=[1_1], geo_tissue_type=[R]}", after.getSampleAttributes().toString());
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

        //Immaterial attribute change, versions should NOT change
        assertEquals(before.getVersion(), after.getVersion());
    }

    @Test
    public void testRunSampleMaterialAttributeChange() {
        Attribute a = new DefaultAttribute();
        a.setName("Targeted Resequencing");
        a.setValue("123");
        runSample.setAttributes(Sets.newHashSet(a));

        SampleProvenance after = Dtos.asDto(getSampleProvenanceById("1_1_1"));
        assertEquals("{geo_run_id_and_position=[1_1], geo_targeted_resequencing=[123]}", after.getSampleAttributes().toString());
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

        //Immaterial attribute change, versions should NOT change
        assertEquals(before.getVersion(), after.getVersion());
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