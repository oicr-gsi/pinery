package ca.on.oicr.pinery.lims.flatfile.dao;

import static org.junit.Assert.*;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class RunFileDaoTest {

  @Autowired private RunFileDao dao;

  @Test
  public void testGetRunByIdAndMapping() {
    Run run = dao.getRun(2);
    Assert.assertNotNull(run);

    Assert.assertEquals(Integer.valueOf(2), run.getId());
    Assert.assertEquals("Run2", run.getName());
    Assert.assertEquals(Integer.valueOf(4), run.getInstrumentId());
    Assert.assertEquals("Completed", run.getState());
    Assert.assertEquals("EFGH", run.getBarcode());

    boolean foundPos1 = false;
    boolean foundPos2 = false;
    for (RunPosition pos : run.getSamples()) {
      switch (pos.getPosition()) {
        case 1:
          foundPos1 = true;
          boolean foundSample2 = false;
          boolean foundSample3 = false;
          for (RunSample sample : pos.getRunSample()) {
            switch (sample.getId()) {
              case "2":
                foundSample2 = true;
                break;
              case "3":
                foundSample3 = true;
                break;
              default:
                throw new AssertionError("Unexpected RunSample found: ID " + sample.getId());
            }
          }
          Assert.assertTrue(foundSample2);
          Assert.assertTrue(foundSample3);
          break;
        case 2:
          foundPos2 = true;
          break;
        default:
          throw new AssertionError("Unexpected RunPosition found: Position " + pos.getPosition());
      }
    }
    Assert.assertTrue(foundPos1);
    Assert.assertTrue(foundPos2);
  }

  @Test
  public void testGetRunByName() {
    Run run = dao.getRun("Run1");
    Assert.assertNotNull(run);
    Assert.assertEquals(Integer.valueOf(1), run.getId());
  }

  @Test
  public void testGetAllRuns() {
    List<Run> runs = dao.getAllRuns(null);
    Assert.assertEquals(2, runs.size());
  }

  @Test
  public void testGetRunsBySamplesSingle() {
    Set<String> sampleIds = new HashSet<>();
    sampleIds.add("4");
    List<Run> runs = dao.getAllRuns(sampleIds);
    assertEquals(1, runs.size());
    for (Run run : runs) {
      assertTrue(
          run.getSamples().stream()
              .flatMap(pos -> pos.getRunSample().stream())
              .anyMatch(sample -> sampleIds.contains(sample.getId())));
    }
  }

  @Test
  public void testGetRunsBySamplesMultiple() {
    Set<String> sampleIds = new HashSet<>();
    sampleIds.add("2");
    sampleIds.add("3");
    List<Run> runs = dao.getAllRuns(sampleIds);
    assertEquals(2, runs.size());
    for (Run run : runs) {
      assertTrue(
          run.getSamples().stream()
              .flatMap(pos -> pos.getRunSample().stream())
              .anyMatch(sample -> sampleIds.contains(sample.getId())));
    }
  }
}
