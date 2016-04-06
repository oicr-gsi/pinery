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
        assertEquals("AAAAAAA-GGGGGGG", spById.get("2_2_4").getIusTag());
        assertEquals("CCCCCCC-TTTTTTT", spById.get("2_2_5").getIusTag());
        assertEquals("NoIndex", spById.get("3_5_8").getIusTag());
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
        assertEquals("97933ede20adf7225665b74d537879d1eff1ccdeae10bdda89cd5ee56781b4e0", spById.get("1_1_2").getVersion());
        assertEquals("95af0b5218f441a3652f65c12201d25bd8b7bc32f13ba938cd2800d914bbfecf", spById.get("1_2_3").getVersion());
        assertEquals("58a4077e5dddebeb9a7cddfe5ebf172f2306e9d2a6061c64812ef6495b51530f", spById.get("2_1_2").getVersion());
        assertEquals("a40c5a1e752d4738947f2404274af8f45c01072d3ad42f472af9ca62b32988ad", spById.get("2_1_3").getVersion());
        assertEquals("637b3f50363ea87c29c01378ca4d27a80169209fc59eedb3902647dd7f6e508c", spById.get("2_2_4").getVersion());
        assertEquals("2bfc8dcd227c3ec2598878f5191df778d6e9c663e835a3eb0e0da0c4d195ebbd", spById.get("2_2_5").getVersion());
        assertEquals("fc8be46310e51f9dcb6d40d70b9ba58697e841fc73142c62d9888f09d548f85d", spById.get("3_5_8").getVersion());
    }

}
