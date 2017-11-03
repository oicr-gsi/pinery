package ca.on.oicr.pinery.client;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.on.oicr.ws.dto.LaneProvenanceDto;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import static org.junit.Assert.*;

public class LaneProvenanceClientIT {

    private static PineryClient pinery;
    private static List<LaneProvenanceDto> lps;
    private static Map<String, LaneProvenanceDto> lpById;

    @BeforeClass
    public static void setupClass() throws FileNotFoundException, IOException, HttpResponseException {
        ItProperties props = new ItProperties();
        pinery = props.getPineryClient();

        lps = pinery.getLaneProvenance().all();
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
        assertEquals("{instrument_name=[h001], run_dir=[/test/dir/1]}", lpById.get("1_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h001], run_dir=[/test/dir/1]}", lpById.get("1_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_dir=[/test/dir/2]}", lpById.get("2_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_dir=[/test/dir/2]}", lpById.get("2_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002], run_dir=[/test/dir/3]}", lpById.get("3_5").getSequencerRunAttributes().toString());
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
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), lpById.get("1_1").getLastModified());
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), lpById.get("1_2").getLastModified());
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), lpById.get("2_1").getLastModified());
        assertEquals(DateTime.parse("2015-07-14T14:51:36.000Z"), lpById.get("2_2").getLastModified());
        assertEquals(DateTime.parse("2016-03-03T20:00:00.000Z"), lpById.get("3_5").getLastModified());
    }

    @Test
    public void checkCreatedDate() {
        System.out.println("checkCreatedDate");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getCreatedDate());
        }
        assertEquals(DateTime.parse("2015-07-11T14:51:36.000Z"), lpById.get("1_1").getCreatedDate());
        assertEquals(DateTime.parse("2015-07-11T14:51:36.000Z"), lpById.get("1_2").getCreatedDate());
        assertEquals(DateTime.parse("2015-07-11T14:51:36.000Z"), lpById.get("2_1").getCreatedDate());
        assertEquals(DateTime.parse("2015-07-11T14:51:36.000Z"), lpById.get("2_2").getCreatedDate());
        assertEquals(DateTime.parse("2016-03-01T20:00:00.000Z"), lpById.get("3_5").getCreatedDate());
    }

    @Test
    public void checkVersion() throws HttpResponseException {
        System.out.println("checkVersion");
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getVersion());
        }
        assertEquals("70691852aa766abb4ede87f894a04e10c2a57d25a7eb3808812c8928ae2876bd", lpById.get("1_1").getVersion());
        assertEquals("3a27d752bb54af8217a52c39c4603f3a6a17104e368f41fedac646d508d49be5", lpById.get("1_2").getVersion());
        assertEquals("23eed4bcd729321b0cd9486f0043f8ee6f80dbf693720263893aae099189a864", lpById.get("2_1").getVersion());
        assertEquals("91ec88c5a449da1e9826ac00f59bb4b790b168f1bd9817d0776201008c92b727", lpById.get("2_2").getVersion());
        assertEquals("174d643a3c3b1c4cf4e5e6befa2221410dbeadf6583f4dd05485788e94d6577e", lpById.get("3_5").getVersion());
    }

}
