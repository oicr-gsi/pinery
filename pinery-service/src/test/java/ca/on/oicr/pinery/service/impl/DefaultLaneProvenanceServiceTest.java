package ca.on.oicr.pinery.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunContainer;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultRunContainer;
import ca.on.oicr.pinery.lims.DefaultRunPosition;
import ca.on.oicr.ws.dto.Dtos;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** @author mlaszloffy */
@RunWith(MockitoJUnitRunner.class)
public class DefaultLaneProvenanceServiceTest {

  @InjectMocks
  private DefaultLaneProvenanceService laneProvenanceService;

  @Mock
  private CacheOrLims lims;

  Instrument instrument;
  InstrumentModel instrumentModel;
  Run run;
  RunContainer container;
  RunPosition lane;
  LaneProvenance before;

  @Before
  public void setup() {
    instrument = new DefaultInstrument();
    instrument.setName("h001");
    instrument.setModelId(1);
    instrument.setId(111);

    instrumentModel = new DefaultInstrumentModel();
    instrumentModel.setName("HiSeq");
    instrumentModel.setId(1);

    lane = new DefaultRunPosition();
    lane.setPosition(1);

    container = new DefaultRunContainer();
    container.setPositions(Sets.newHashSet(lane));

    run = new DefaultRun();
    run.setId(1);
    run.setName("ABC_123");
    run.setContainers(Sets.newHashSet(container));
    run.setInstrumentId(111);
    run.setInstrumentName("asdasd");

    when(lims.getRuns(null)).thenReturn(Lists.newArrayList(run));
    when(lims.getInstruments()).thenReturn(Lists.newArrayList(instrument));
    when(lims.getInstrumentModels()).thenReturn(Lists.newArrayList(instrumentModel));

    before = Dtos.asDto(getLaneProvenanceById("1_1"));
  }

  @Test
  public void changeRunLastModified() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    run.setModified(Date.from(expectedDate.toInstant()));

    LaneProvenance after = Dtos.asDto(getLaneProvenanceById("1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertNull(after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());

    // set sequencer run "run_dir"
    run.setRunDirectory("/tmp/SR_001/");
    after = Dtos.asDto(getLaneProvenanceById("1_1"));
    assertEquals(
        "/tmp/SR_001/", Iterables.getOnlyElement(after.getSequencerRunAttributes().get("run_dir")));
    assertNotEquals(before.getVersion(), after.getVersion());

    // set sequencer run to being completed
    ZonedDateTime completionDate = ZonedDateTime.parse("2016-01-07T00:00:00.000Z");
    run.setCompletionDate(Date.from(completionDate.toInstant()));
    after = Dtos.asDto(getLaneProvenanceById("1_1"));
    assertEquals(completionDate, after.getCreatedDate());
  }

  // @Test - skipped because lane does not have a "modified" setter
  public void changeLaneLastModified() {
    ZonedDateTime expectedDate = ZonedDateTime.parse("2016-01-01T00:00:00.000Z");
    // lane.setModified(expectedDate.toDate());

    LaneProvenance after = Dtos.asDto(getLaneProvenanceById("1_1"));
    assertEquals(expectedDate, after.getLastModified());
    assertNull(after.getCreatedDate());

    // modification date should not change the version
    assertEquals(before.getVersion(), after.getVersion());

    // set sequencer run to being completed
    ZonedDateTime completionDate = ZonedDateTime.parse("2016-01-07T00:00:00.000Z");
    run.setCompletionDate(Date.from(completionDate.toInstant()));
    after = Dtos.asDto(getLaneProvenanceById("1_1"));
    assertEquals(completionDate, after.getCreatedDate());
  }

  private LaneProvenance getLaneProvenanceById(String laneProvenanceId) {
    for (LaneProvenance lp : laneProvenanceService.getLaneProvenance()) {
      if (lp.getLaneProvenanceId().equals(laneProvenanceId)) {
        return lp;
      }
    }
    return null;
  }
}
