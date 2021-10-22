package ca.on.oicr.pinery.lims.flatfile.dao;

import static org.junit.Assert.*;

import ca.on.oicr.pinery.api.Assay;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class AssayFileDaoTest {

  @Autowired private AssayFileDao dao;

  @Test
  public void testGetAll() throws Exception {
    List<Assay> assays = dao.getAll();
    assertNotNull(assays);
    assertEquals(2, assays.size());
  }

  @Test
  public void testGet() throws Exception {
    Assay assay = dao.get(1);
    assertNotNull(assay);
    assertEquals(1, assay.getId().intValue());
    assertNotNull(assay.getMetrics());
    assertEquals(2, assay.getMetrics().size());
  }
}
