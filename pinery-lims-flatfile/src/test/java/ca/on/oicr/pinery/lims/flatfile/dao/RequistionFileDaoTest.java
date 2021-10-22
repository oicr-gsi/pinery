package ca.on.oicr.pinery.lims.flatfile.dao;

import static org.junit.Assert.*;

import ca.on.oicr.pinery.api.Requisition;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class RequistionFileDaoTest {

  @Autowired private RequisitionFileDao dao;

  @Test
  public void testGetAll() throws Exception {
    List<Requisition> reqs = dao.getAll();
    assertNotNull(reqs);
    assertEquals(2, reqs.size());
  }

  @Test
  public void testGet() throws Exception {
    Requisition req = dao.get(1);
    assertNotNull(req);
    assertEquals(1, req.getId().intValue());
    assertNotNull(req.getSampleIds());
    assertEquals(2, req.getSampleIds().size());
    assertNotNull(req.getSignOffs());
    assertEquals(1, req.getSignOffs().size());
  }
}
