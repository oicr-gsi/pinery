package ca.on.oicr.pinery.client;

import ca.on.oicr.gsi.provenance.api.model.SampleProvenance;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.SampleProvenanceDto;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import static org.junit.Assert.*;

public class SampleProvenanceClientIT {

    private static PineryClient pinery;
    private static List<SampleProvenanceDto> sps;
    private static Map<String, SampleProvenanceDto> spById;

    @BeforeClass
    public static void setupClass() throws FileNotFoundException, IOException, HttpResponseException {
        ItProperties props = new ItProperties();
        pinery = props.getPineryClient();

        sps = pinery.getSampleProvenance().all();
        Collections.sort(sps, new Comparator<SampleProvenanceDto>() {
            @Override
            public int compare(SampleProvenanceDto o1, SampleProvenanceDto o2) {
                return o1.getSampleProvenanceId().compareTo(o2.getSampleProvenanceId());
            }
        });
        spById = new HashMap<>();
        for (SampleProvenanceDto sp : sps) {
            spById.put(sp.getSampleProvenanceId(), sp);
        }
    }

    @AfterClass
    public static void cleanUp() {
        pinery.close();
    }

    @Test
    public void testGetAll() {
        assertEquals(7, sps.size());
    }

    @Test
    public void testGetStudyTitle() {
        assertEquals("TestProject", spById.get("1_1_2").getStudyTitle());
        assertEquals("TestProject", spById.get("1_2_3").getStudyTitle());
        assertEquals("TestProject", spById.get("2_1_2").getStudyTitle());
        assertEquals("TestProject", spById.get("2_1_3").getStudyTitle());
        assertEquals("TestProject2", spById.get("2_2_4").getStudyTitle());
        assertEquals("TestProject2", spById.get("2_2_5").getStudyTitle());
        assertEquals("TestProject3", spById.get("3_5_8").getStudyTitle());
    }

    @Test
    public void testGetStudyAttributes() {
        assertEquals("{}", spById.get("1_1_2").getStudyAttributes().toString());
        assertEquals("{}", spById.get("1_2_3").getStudyAttributes().toString());
        assertEquals("{}", spById.get("2_1_2").getStudyAttributes().toString());
        assertEquals("{}", spById.get("2_1_3").getStudyAttributes().toString());
        assertEquals("{}", spById.get("2_2_4").getStudyAttributes().toString());
        assertEquals("{}", spById.get("2_2_5").getStudyAttributes().toString());
        assertEquals("{}", spById.get("3_5_8").getStudyAttributes().toString());
    }

    @Test
    public void testGetRootSampleName() {
        for (SampleProvenanceDto sp : sps) {
            assertEquals("Sample1", sp.getRootSampleName());
        }
    }

    @Test
    public void testGetParentSampleName() {
        assertEquals("Sample1", spById.get("1_1_2").getParentSampleName());
        assertEquals("Sample1", spById.get("1_2_3").getParentSampleName());
        assertEquals("Sample1", spById.get("2_1_2").getParentSampleName());
        assertEquals("Sample1", spById.get("2_1_3").getParentSampleName());
        assertEquals("Sample1", spById.get("2_2_4").getParentSampleName());
        assertEquals("Sample1", spById.get("2_2_5").getParentSampleName());
        assertEquals("Sample1_1:Sample1", spById.get("3_5_8").getParentSampleName());
    }

    @Test
    public void testGetSampleName() {
        assertEquals("Sample2", spById.get("1_1_2").getSampleName());
        assertEquals("Sample3", spById.get("1_2_3").getSampleName());
        assertEquals("Sample2", spById.get("2_1_2").getSampleName());
        assertEquals("Sample3", spById.get("2_1_3").getSampleName());
        assertEquals("Sample4", spById.get("2_2_4").getSampleName());
        assertEquals("Sample5", spById.get("2_2_5").getSampleName());
        assertEquals("Sample1_1_1", spById.get("3_5_8").getSampleName());
    }

    @Test
    public void testGetSampleAttributes() {
        for (SampleProvenance sp : sps) {
            System.out.println(sp.getSampleProvenanceId() + " = " + sp.getSampleAttributes());
        }
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[1_1], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("1_1_2").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[1_2], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("1_2_3").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[2_1], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_1_2").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[2_1], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_1_3").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[2_2], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_2_4").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_run_id_and_position=[2_2], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_2_5").getSampleAttributes().toString());
        assertEquals("{geo_library_source_template_type=[WG], geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[3_5], geo_template_type=[Illumina PE Library], geo_tissue_origin=[Ly], geo_tissue_type=[R]}",
                spById.get("3_5_8").getSampleAttributes().toString());
    }

    @Test
    public void testGetSequencerRunName() {
        assertEquals("Run1", spById.get("1_1_2").getSequencerRunName());
        assertEquals("Run1", spById.get("1_2_3").getSequencerRunName());
        assertEquals("Run2", spById.get("2_1_2").getSequencerRunName());
        assertEquals("Run2", spById.get("2_1_3").getSequencerRunName());
        assertEquals("Run2", spById.get("2_2_4").getSequencerRunName());
        assertEquals("Run2", spById.get("2_2_5").getSequencerRunName());
        assertEquals("Run3", spById.get("3_5_8").getSequencerRunName());
    }

    @Test
    public void testGetSequencerRunAttributes() {
        for (SampleProvenance sp : sps) {
            System.out.println(sp.getSampleProvenanceId() + " = " + sp.getSequencerRunAttributes());
        }
        assertEquals("{instrument_name=[h001]}", spById.get("1_1_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h001]}", spById.get("1_2_3").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", spById.get("2_1_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", spById.get("2_1_3").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", spById.get("2_2_4").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", spById.get("2_2_5").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", spById.get("3_5_8").getSequencerRunAttributes().toString());
    }

    @Test
    public void testGetSequencerRunPlatformName() {
        assertEquals("HiSeq", spById.get("1_1_2").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("1_2_3").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("2_1_2").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("2_1_3").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("2_2_4").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("2_2_5").getSequencerRunPlatformName());
        assertEquals("HiSeq", spById.get("3_5_8").getSequencerRunPlatformName());
    }

    @Test
    public void testGetLaneNumber() {
        assertEquals("1", spById.get("1_1_2").getLaneNumber());
        assertEquals("2", spById.get("1_2_3").getLaneNumber());
        assertEquals("1", spById.get("2_1_2").getLaneNumber());
        assertEquals("1", spById.get("2_1_3").getLaneNumber());
        assertEquals("2", spById.get("2_2_4").getLaneNumber());
        assertEquals("2", spById.get("2_2_5").getLaneNumber());
        assertEquals("5", spById.get("3_5_8").getLaneNumber());
    }

    @Test
    public void testGetLaneAttributes() {
        assertEquals("{}", spById.get("1_1_2").getLaneAttributes().toString());
        assertEquals("{}", spById.get("1_2_3").getLaneAttributes().toString());
        assertEquals("{}", spById.get("2_1_2").getLaneAttributes().toString());
        assertEquals("{}", spById.get("2_1_3").getLaneAttributes().toString());
        assertEquals("{}", spById.get("2_2_4").getLaneAttributes().toString());
        assertEquals("{}", spById.get("2_2_5").getLaneAttributes().toString());
        assertEquals("{}", spById.get("3_5_8").getLaneAttributes().toString());
    }

    @Test
    public void checkBarcodes() {
        assertEquals("", spById.get("1_1_2").getIusTag());
        assertEquals("", spById.get("1_2_3").getIusTag());
        assertEquals("AAAAAAA", spById.get("2_1_2").getIusTag());
        assertEquals("GGGGGGG", spById.get("2_1_3").getIusTag());
        assertEquals("AAAAAAA-GGGGGGG", spById.get("2_2_4").getIusTag());
        assertEquals("CCCCCCC-TTTTTTT", spById.get("2_2_5").getIusTag());
        assertEquals("", spById.get("3_5_8").getIusTag());
    }

    @Test
    public void checkLastModified() {
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), spById.get("1_1_2").getLastModified());
        assertEquals(DateTime.parse("2015-08-01T20:56:20.000Z"), spById.get("1_2_3").getLastModified());
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), spById.get("2_1_2").getLastModified());
        assertEquals(DateTime.parse("2015-08-01T20:56:20.000Z"), spById.get("2_1_3").getLastModified());
        assertEquals(DateTime.parse("2015-09-01T20:56:20.000Z"), spById.get("2_2_4").getLastModified());
        assertEquals(DateTime.parse("2015-10-01T20:56:20.000Z"), spById.get("2_2_5").getLastModified());
        assertEquals(DateTime.parse("2016-03-05T20:00:00.000Z"), spById.get("3_5_8").getLastModified());
    }

    @Test
    public void checkVersion() throws HttpResponseException {
        for (SampleProvenance sp : sps) {
            System.out.println(sp.getSampleProvenanceId() + " = " + sp.getVersion());
        }
        assertEquals("5b849ee5fb94cd20caf1067e8463f20bbd47904545bab215117bb84028ac71eb", spById.get("1_1_2").getVersion());
        assertEquals("ea656f7a8eebff6f15142877d7b2804314f3d2dc148508ea1ac1c9f02259a582", spById.get("1_2_3").getVersion());
        assertEquals("c5e90a515f477240f20ed17f387f1c9a8e1ac3580d81de2eefc19afe84fcbdb4", spById.get("2_1_2").getVersion());
        assertEquals("d367a9cfcf95e39fe9b64911a7bc17445c34c2b8aaf6f285f06866f43ebb8f5e", spById.get("2_1_3").getVersion());
        assertEquals("4331cd999d4f1d568e437a248e45434ee256d4e44fec17c05f56b98eb77e42ba", spById.get("2_2_4").getVersion());
        assertEquals("2f49bd8e5fea1899a2bf3c3f5d811a37308537c20d3e5b2e8af1bd66f9d73f4e", spById.get("2_2_5").getVersion());
        assertEquals("346efbea3eceee6a0fdd2abd144c103baba251845c41139b039f1eb7cadeda39", spById.get("3_5_8").getVersion());
    }

}
