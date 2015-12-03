package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class OrderFileDaoTest {
  
  @Autowired
  private OrderFileDao dao;
  
  @Test
  public void testGetSingleOrderAndMapping() {
    Order order = dao.getOrder(1);
    Assert.assertNotNull(order);
    
    Assert.assertEquals(Integer.valueOf(1), order.getId());
    Assert.assertEquals("TestProject", order.getProject());
    Assert.assertEquals("Completed", order.getStatus());
    Assert.assertEquals("Illumina HiSeq", order.getPlatform());
    Assert.assertEquals(ModelUtils.convertToDate("2011-11-03T10:50:12-04:00"), order.getCreatedDate());
    Assert.assertEquals(Integer.valueOf(2), order.getCreatedById());
    Assert.assertEquals(ModelUtils.convertToDate("2011-11-03T10:50:12-04:00"), order.getModifiedDate());
    Assert.assertEquals(Integer.valueOf(2), order.getModifiedById());
    
    boolean foundSample2 = false;
    boolean foundSample3 = false;
    for (OrderSample sample : order.getSamples()) {
      switch (sample.getId()) {
      case 2:
        foundSample2 = true;
        Assert.assertEquals("AACCGG", sample.getBarcode());
        boolean barcodeFound = false;
        boolean organismFound = false;
        for (Attribute attribute : sample.getAttributes()) {
          switch (attribute.getName()) {
          case "Barcode":
            barcodeFound = true;
            Assert.assertEquals("AACCGG", attribute.getValue());
            break;
          case "Organism":
            organismFound = true;
            Assert.assertEquals("Homo sapiens", attribute.getValue());
            break;
          default:
            throw new AssertionError("Unexpected Attribute found: " + attribute.getName());
          }
        }
        Assert.assertTrue(barcodeFound);
        Assert.assertTrue(organismFound);
        break;
      case 3:
        foundSample3 = true;
        break;
      default:
        throw new AssertionError("Unexpected sample found: ID " + sample.getId());
      }
    }
    Assert.assertTrue(foundSample2);
    Assert.assertTrue(foundSample3);
  }
  
  @Test
  public void testGetAllorders() {
    List<Order> orders = dao.getAllOrders();
    Assert.assertEquals(2, orders.size());
  }
  
}
