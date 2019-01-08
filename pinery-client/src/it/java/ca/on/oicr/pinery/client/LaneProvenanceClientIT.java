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

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.ws.dto.LaneProvenanceDto;

public class LaneProvenanceClientIT {

    private static PineryClient pinery;
    private static List<LaneProvenanceDto> lps;
    private static Map<String, LaneProvenanceDto> lpById;

    @BeforeClass
    public static void setupClass() throws FileNotFoundException, IOException, HttpResponseException {
        ItProperties props = new ItProperties();
        pinery = props.getPineryClient();

        lps = pinery.getLaneProvenance().version("v1");
        Collections.sort(lps, new Comparator<LaneProvenanceDto>() {
            @Override
            public int compare(LaneProvenanceDto o1, LaneProvenanceDto o2) {
                return o1.getLaneProvenanceId().compareTo(o2.getLaneProvenanceId());
            }
        });
        lpById = new HashMap<>();
        for (LaneProvenanceDto lp : lps) {
            lpById.put(lp.getLaneProvenanceId(), lp);
        }
    }

    @AfterClass
    public static void cleanUp() {
        pinery.close();
    }

    @Test
    public void testGetAll() {
        assertEquals(5, lps.size());
    }

    @Test
    public void testGetSequencerRunName() {
        assertEquals("Run1", lpById.get("1_1").getSequencerRunName());
        assertEquals("Run1", lpById.get("1_2").getSequencerRunName());
        assertEquals("Run2", lpById.get("2_1").getSequencerRunName());
        assertEquals("Run2", lpById.get("2_2").getSequencerRunName());
        assertEquals("Run3", lpById.get("3_5").getSequencerRunName());
    }

    @Test
    public void testGetSequencerRunAttributes() {
        System.out.println("testGetSequencerRunAttributes");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getSequencerRunAttributes());
        }
        assertEquals("{instrument_name=[h001], run_bases_mask=[y51,y51], run_dir=[/test/dir/1]}", lpById.get("1_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h001], run_bases_mask=[y51,y51], run_dir=[/test/dir/1]}", lpById.get("1_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_bases_mask=[y151,I8,y151], run_dir=[/test/dir/2]}", lpById.get("2_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_bases_mask=[y151,I8,y151], run_dir=[/test/dir/2]}", lpById.get("2_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_bases_mask=[y75,I6,y75], run_dir=[/test/dir/3]}", lpById.get("3_5").getSequencerRunAttributes().toString());
    }

    @Test
    public void testGetSequencerRunPlatformName() {
        assertEquals("HiSeq", lpById.get("1_1").getSequencerRunPlatformModel());
        assertEquals("HiSeq", lpById.get("1_2").getSequencerRunPlatformModel());
        assertEquals("HiSeq", lpById.get("2_1").getSequencerRunPlatformModel());
        assertEquals("HiSeq", lpById.get("2_2").getSequencerRunPlatformModel());
        assertEquals("HiSeq", lpById.get("3_5").getSequencerRunPlatformModel());
    }

    @Test
    public void testGetLaneNumber() {
        assertEquals("1", lpById.get("1_1").getLaneNumber());
        assertEquals("2", lpById.get("1_2").getLaneNumber());
        assertEquals("1", lpById.get("2_1").getLaneNumber());
        assertEquals("2", lpById.get("2_2").getLaneNumber());
        assertEquals("5", lpById.get("3_5").getLaneNumber());
    }

    @Test
    public void testGetLaneAttributes() {
        assertEquals("{}", lpById.get("1_1").getLaneAttributes().toString());
        assertEquals("{}", lpById.get("1_2").getLaneAttributes().toString());
        assertEquals("{}", lpById.get("2_1").getLaneAttributes().toString());
        assertEquals("{}", lpById.get("2_2").getLaneAttributes().toString());
        assertEquals("{}", lpById.get("3_5").getLaneAttributes().toString());
    }

    @Test
    public void checkLastModified() {
        System.out.println("checkLastModified");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getLastModified());
        }
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), lpById.get("1_1").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), lpById.get("1_2").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), lpById.get("2_1").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-14T14:51:36Z").toString(), lpById.get("2_2").getLastModified().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2016-03-03T20:00:01Z").toString(), lpById.get("3_5").getLastModified().toInstant().toString());
    }

    @Test
    public void checkCreatedDate() {
        System.out.println("checkCreatedDate");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getCreatedDate());
        }
        assertEquals(ZonedDateTime.parse("2015-07-11T14:51:36Z").toString(), lpById.get("1_1").getCreatedDate().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-11T14:51:36Z").toString(), lpById.get("1_2").getCreatedDate().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-11T14:51:36Z").toString(), lpById.get("2_1").getCreatedDate().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2015-07-11T14:51:36Z").toString(), lpById.get("2_2").getCreatedDate().toInstant().toString());
        assertEquals(ZonedDateTime.parse("2016-03-01T20:00:01Z").toString(), lpById.get("3_5").getCreatedDate().toInstant().toString());
    }

    @Test
    public void checkVersion() throws HttpResponseException {
        System.out.println("checkVersion");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getVersion());
        }
        assertEquals("9c189f79b14939e754387b226122fd91f5fe6bdda268791945ebbb34751fa9fc", lpById.get("1_1").getVersion());
        assertEquals("d55fdada5383f60ca4a176f249f049b8df63c79567bbd3dd8d8f1859f279567f", lpById.get("1_2").getVersion());
        assertEquals("7b6e3ca94927071fc5d1042a0668bed582911725ae40b0a805f16049008d9ab6", lpById.get("2_1").getVersion());
        assertEquals("2dab64b79479422c92d4d0e16aa5ee006ddf28fcb5fb0a8eef548d6e2d6d261a", lpById.get("2_2").getVersion());
        assertEquals("29d03544d5c40e01e6d67941a8941b02a53e3b05c659912a2e7a491927bf46d5", lpById.get("3_5").getVersion());
    }

}
