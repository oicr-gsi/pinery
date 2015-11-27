package ca.on.oicr.pinery.lims.flatfile;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ca.on.oicr.pinery.api.AttributeName;
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
import ca.on.oicr.pinery.lims.flatfile.dao.InstrumentDao;

public class FlatfileClient implements Lims {
  
  @Autowired
  private InstrumentDao instrumentDao;

  public InstrumentDao getInstrumentDao() {
    return instrumentDao;
  }

  public void setInstrumentDao(InstrumentDao instrumentDao) {
    this.instrumentDao = instrumentDao;
  }

  @Override
  public List<String> getProjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Sample getSample(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Sample> getSamples(Boolean archived, Set<String> projects,
      Set<String> types, DateTime before, DateTime after) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getUsers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUser(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Order> getOrders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Order getOrder(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Run> getRuns() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Run getRun(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Run getRun(String runName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Type> getTypes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ChangeLog getChangeLog(Integer id) {
    // TODO Auto-generated method stub
    return null;
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
  public List<Instrument> getInstrumentModelInsrument(Integer id) { // TODO: fix typo in interface
    return instrumentDao.getInstrumentModelInstruments();
  }

}
