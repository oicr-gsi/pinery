package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class UserFileDaoTest {
  
  @Autowired
  private UserFileDao dao;
  
  @Test
  public void testGetSingleUserAndMapping() {
    User user = dao.getUser(1);
    Assert.assertNotNull(user);
    
    Assert.assertEquals(new Integer(1), user.getId());
    Assert.assertNull(user.getTitle());
    Assert.assertEquals("Admin", user.getFirstname());
    Assert.assertEquals("Istrator", user.getLastname());
    Assert.assertEquals("Ontario Institute for Cancer Research", user.getInstitution());
    Assert.assertEquals("+1 416 555 1234", user.getPhone());
    Assert.assertEquals("admin.istrator@oicr.on.ca", user.getEmail());
    Assert.assertEquals(new Boolean(false), user.getArchived());
    Assert.assertEquals(ModelUtils.convertToDate("2015-07-14T10:51:36-04:00"), user.getCreated());
    Assert.assertEquals(new Integer(1), user.getCreatedById());
    Assert.assertEquals(ModelUtils.convertToDate("2015-07-14T11:36:37-04:00"), user.getModified());
    Assert.assertEquals(new Integer(1), user.getModifiedById());
  }
  
  @Test
  public void testGetAllUsers() {
    List<User> users = dao.getAllUsers();
    Assert.assertEquals(2, users.size());
  }
  
}
