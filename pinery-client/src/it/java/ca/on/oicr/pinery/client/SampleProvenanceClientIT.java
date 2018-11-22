package ca.on.oicr.pinery.client;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.ws.dto.SampleProvenanceDto;

public class SampleProvenanceClientIT {

    private static PineryClient pinery;
    private static List<SampleProvenanceDto> sps;
    private static Map<String, SampleProvenanceDto> spById;

    @BeforeClass
    public static void setupClass() throws FileNotFoundException, IOException, HttpResponseException {
        ItProperties props = new ItProperties();
        pinery = props.getPineryClient();

        sps = pinery.getSampleProvenance().latest();
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
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[1_1], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("1_1_2").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[1_2], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("1_2_3").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[2_1], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_1_2").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[2_1], geo_targeted_resequencing=[Illumina TruSeq Exome], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_1_3").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[2_2], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_2_4").getSampleAttributes().toString());
        assertEquals("{geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[2_2], geo_targeted_resequencing=[Illumina TruSeq Exome], geo_template_type=[Illumina PE Library], geo_tissue_origin=[nn], geo_tissue_type=[n]}",
                spById.get("2_2_5").getSampleAttributes().toString());
        assertEquals("{geo_library_source_template_type=[WG], geo_organism=[Homo sapiens], geo_prep_kit=[Kit 1], geo_run_id_and_position=[3_5], geo_targeted_resequencing=[Illumina TruSeq Exome], geo_template_type=[Illumina PE Library], geo_tissue_origin=[Ly], geo_tissue_type=[R]}",
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
        assertEquals("HiSeq", spById.get("1_1_2").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("1_2_3").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("2_1_2").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("2_1_3").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("2_2_4").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("2_2_5").getSequencerRunPlatformModel());
        assertEquals("HiSeq", spById.get("3_5_8").getSequencerRunPlatformModel());
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
        assertEquals("NoIndex", spById.get("1_1_2").getIusTag());
        assertEquals("NoIndex", spById.get("1_2_3").getIusTag());
        assertEquals("AAAAAAA", spById.get("2_1_2").getIusTag());
        assertEquals("GGGGGGG", spById.get("2_1_3").getIusTag());
        assertEquals("NoIndex", spById.get("2_2_4").getIusTag());
        assertEquals("CCCCCCC-TTTTTTT", spById.get("2_2_5").getIusTag());
        assertEquals("AATTCCGG", spById.get("3_5_8").getIusTag());
    }

    @Test
    public void checkLastModified() {
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), spById.get("1_1_2").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-08-01T20:56:20Z").toString(), spById.get("1_2_3").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), spById.get("2_1_2").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-08-01T20:56:20Z").toString(), spById.get("2_1_3").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-09-01T20:56:20Z").toString(), spById.get("2_2_4").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-10-01T20:56:20Z").toString(), spById.get("2_2_5").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2016-03-05T20:00:01Z").toString(), spById.get("3_5_8").getLastModified().toInstant().toString());
    }

    @Test
    public void checkVersion() throws HttpResponseException {
        for (SampleProvenance sp : sps) {
            System.out.println(sp.getSampleProvenanceId() + " = " + sp.getVersion());
        }
        assertEquals("bff43683a88d5eb84a234381f59bebd22b5a2b7de7e43099b3ea5a6529793a09", spById.get("1_1_2").getVersion());
        assertEquals("ac562975caacce6789bd10552f17fc6103377ece0b186b65667d6b61d71dfbfc", spById.get("1_2_3").getVersion());
        assertEquals("162715dd5c52407e544096494aeee472a6bdd6339ad8898d9caefae1e51104fa", spById.get("2_1_2").getVersion());
        assertEquals("fd238c945ebdb1200e2c348bbd5107568afcf66fdf7164b1f76490ff9e7da75d", spById.get("2_1_3").getVersion());
        assertEquals("b461d8de3ab867f3ef276056ad623d0f52a3df7827a990010218531b23a4fe15", spById.get("2_2_4").getVersion());
        assertEquals("d9c4030cac3267c20a72ac62d9eea1d12493e9ded6305f23d819a0faeaffac31", spById.get("2_2_5").getVersion());
        assertEquals("d85524f6601351a2896bcc06ead576eb0bdebdfedb9678c256d4395a54cf36ac", spById.get("3_5_8").getVersion());
    }

}
