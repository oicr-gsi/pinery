package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class ChangeFileDaoTest {
  
  @Autowired
  private ChangeFileDao dao;
  
  @Test
  public void testGetSingleChangeLogAndMapping() {
    ChangeLog log = dao.getSampleChanges(2);
    Assert.assertNotNull(log);
    
    Assert.assertEquals(Integer.valueOf(2), log.getSampleId());
    Assert.assertEquals(2, log.getChanges().size());
    
    boolean createFound = false;
    boolean changeFound = false;
    for (Change change : log.getChanges()) {
      switch (change.getAction()) {
      case "Created":
        createFound = true;
        Assert.assertEquals(ModelUtils.convertToDate("2012-07-01T16:53:19-04:00"), change.getCreated());
        Assert.assertEquals(Integer.valueOf(2), change.getCreatedById());
        break;
      case "Something changed":
        changeFound = true;
        break;
      default:
        throw new AssertionError("Unexpected Change found: " + change.getAction());
      }
    }
    Assert.assertTrue(createFound);
    Assert.assertTrue(changeFound);
  }
  
  @Test
  public void testGetAllChangeLogs() {
    List<ChangeLog> logs = dao.getAllChanges();
    Assert.assertEquals(6, logs.size());
  }
  
}
