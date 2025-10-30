package ca.on.oicr.pinery.lims;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunContainer;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.Sample;

public class DefaultSampleProvenanceTest {

  @Test
  public void testLaneNumbers() {
    DefaultSampleProvenance sp = new DefaultSampleProvenance();

    InstrumentModel instrumentModel = mock(InstrumentModel.class);
    when(instrumentModel.hasMultipleContainers()).thenReturn(false);
    sp.setInstrumentModel(instrumentModel);

    Run run = mock(Run.class);
    when(run.getId()).thenReturn(1);
    sp.setSequencerRun(run);

    Sample sample = mock(Sample.class);
    when(sample.getId()).thenReturn("LDI345");
    sp.setSample(sample);

    RunPosition lane = mock(RunPosition.class);
    when(lane.getPosition()).thenReturn(2);
    sp.setLane(lane);

    RunContainer container = mock(RunContainer.class);
    when(container.getInstrumentPosition()).thenReturn("A");
    sp.setContainer(container);

    // test with single-container instrument
    assertEquals("2", sp.getLaneNumber());
    assertEquals("1_2_LDI345", sp.getSampleProvenanceId());
    assertEquals("1_2_LDI345", sp.getProvenanceId());

    // test with multi-container instrument
    when(instrumentModel.hasMultipleContainers()).thenReturn(true);
    assertEquals("A_2", sp.getLaneNumber());
    assertEquals("1_A_2_LDI345", sp.getSampleProvenanceId());
    assertEquals("1_A_2_LDI345", sp.getProvenanceId());
  }

}
