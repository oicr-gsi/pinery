package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class InstrumentFileDaoTest {

  @Autowired private InstrumentFileDao dao;

  @Test
  public void testGetSingleInstrumentAndMapping() {
    Instrument ins = dao.getInstrument(1);
    Assert.assertNotNull(ins);

    Assert.assertEquals(Integer.valueOf(1), ins.getId());
    Assert.assertEquals("s001", ins.getName());
    Assert.assertEquals(Integer.valueOf(1), ins.getModelId());
  }

  @Test
  public void testGetAllInstruments() {
    List<Instrument> instruments = dao.getAllInstruments();
    Assert.assertEquals(4, instruments.size());
  }

  @Test
  public void testGetInstrumentsByModelId() {
    List<Instrument> instruments = dao.getInstrumentModelInstruments(1);
    Assert.assertEquals(2, instruments.size());
  }

  @Test
  public void testGetSingleModelAndMapping() {
    InstrumentModel model = dao.getInstrumentModel(2);
    Assert.assertNotNull(model);

    Assert.assertEquals(Integer.valueOf(2), model.getId());
    Assert.assertEquals("HiSeq", model.getName());
    Assert.assertEquals(Integer.valueOf(1), model.getModifiedById());
  }

  @Test
  public void testGetAllModels() {
    List<InstrumentModel> models = dao.getAllInstrumentModels();
    Assert.assertEquals(2, models.size());
  }
}
