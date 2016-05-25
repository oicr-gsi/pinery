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
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getSequencerRunAttributes());
        }
        assertEquals("{instrument_name=[h001]}", lpById.get("1_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h001]}", lpById.get("1_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", lpById.get("2_1").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", lpById.get("2_2").getSequencerRunAttributes().toString());
        assertEquals("{instrument_name=[h002]}", lpById.get("3_5").getSequencerRunAttributes().toString());
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
    public void checkVersion() throws HttpResponseException {
        for (LaneProvenance lp : lps) {
            System.out.println(lp.getLaneProvenanceId() + " = " + lp.getVersion());
        }
        assertEquals("eb7daffa04c0670261cd2526da42c65c92f2de352927e3bdb0a35d0df9e5a8fd", lpById.get("1_1").getVersion());
        assertEquals("ff5b845d470f9f1c96cb85bdb9424206aeec17016cc6809ef602da9ea5cc9896", lpById.get("1_2").getVersion());
        assertEquals("2cd37abf28f063187387631ae1f5bac71f23588286e67e0df7225f7a5e4712aa", lpById.get("2_1").getVersion());
        assertEquals("a9121dcb119c60665087e5808a357e6a99791d1172146afcdbebf9b167e7642a", lpById.get("2_2").getVersion());
        assertEquals("1fe648ec704433a3a2f7e5602ba12092532fba9188c0048dde620ff42f34a87c", lpById.get("3_5").getVersion());
    }

}
