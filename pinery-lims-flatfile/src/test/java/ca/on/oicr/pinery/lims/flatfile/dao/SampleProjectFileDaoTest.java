package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.SampleProject;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class SampleProjectFileDaoTest {

  @Autowired private SampleProjectFileDao dao;

  @Test
  public void testGetAllSampleProjects() {
    List<SampleProject> projects = dao.getAllSampleProjects();
    Assert.assertEquals(2, projects.size());
  }
}
