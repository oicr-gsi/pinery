package ca.on.oicr.pinery.lims.flatfile;

import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.flatfile.dao.BoxDao;
import ca.on.oicr.pinery.lims.flatfile.dao.ChangeDao;
import ca.on.oicr.pinery.lims.flatfile.dao.InstrumentDao;
import ca.on.oicr.pinery.lims.flatfile.dao.OrderDao;
import ca.on.oicr.pinery.lims.flatfile.dao.RunDao;
import ca.on.oicr.pinery.lims.flatfile.dao.SampleDao;
import ca.on.oicr.pinery.lims.flatfile.dao.SampleProjectDao;
import ca.on.oicr.pinery.lims.flatfile.dao.UserDao;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class FlatfileClient implements Lims {

  @Autowired private InstrumentDao instrumentDao;
  @Autowired private OrderDao orderDao;
  @Autowired private RunDao runDao;
  @Autowired private SampleDao sampleDao;
  @Autowired private SampleProjectDao sampleProjectDao;
  @Autowired private UserDao userDao;
  @Autowired private ChangeDao changeDao;
  @Autowired private BoxDao boxDao;

  @Override
  public Sample getSample(String id) {
    return sampleDao.getSample(id);
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    return sampleProjectDao.getAllSampleProjects();
  }

  @Override
  public List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after) {
    return sampleDao.getSamplesFiltered(archived, projects, types, before, after);
  }

  @Override
  public List<User> getUsers() {
    return userDao.getAllUsers();
  }

  @Override
  public User getUser(Integer id) {
    return userDao.getUser(id);
  }

  @Override
  public List<Order> getOrders() {
    return orderDao.getAllOrders();
  }

  @Override
  public Order getOrder(Integer id) {
    return orderDao.getOrder(id);
  }

  @Override
  public List<Run> getRuns() {
    return runDao.getAllRuns();
  }

  @Override
  public Run getRun(Integer id) {
    return runDao.getRun(id);
  }

  @Override
  public Run getRun(String runName) {
    return runDao.getRun(runName);
  }

  @Override
  public List<Type> getTypes() {
    return sampleDao.getAllSampleTypes();
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    return sampleDao.getAllSampleAttributes();
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    return changeDao.getAllChanges();
  }

  @Override
  public ChangeLog getChangeLog(String id) {
    return changeDao.getSampleChanges(id);
  }

  @Override
  public List<InstrumentModel> getInstrumentModels() {
    return instrumentDao.getAllInstrumentModels();
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    return instrumentDao.getInstrumentModel(id);
  }

  @Override
  public List<Instrument> getInstruments() {
    return instrumentDao.getAllInstruments();
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    return instrumentDao.getInstrument(instrumentId);
  }

  @Override
  public List<Instrument> getInstrumentModelInstrument(Integer id) {
    return instrumentDao.getInstrumentModelInstruments(id);
  }

  @Override
  public List<Box> getBoxes() {
    return boxDao.getAllBoxes();
  }
}
