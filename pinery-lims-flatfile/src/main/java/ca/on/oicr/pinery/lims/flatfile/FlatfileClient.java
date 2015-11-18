package ca.on.oicr.pinery.lims.flatfile;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

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

public class FlatfileClient implements Lims {
  
  private File instrumentsFile;
  private File ordersFile;
  private File samplesFile;
  private File changesFile;
  private File runsFile;
  private File usersFile;
  
  public File getInstrumentsFile() {
    return instrumentsFile;
  }

  public void setInstrumentsFile(File instrumentsFile) {
    this.instrumentsFile = instrumentsFile;
  }

  public File getOrdersFile() {
    return ordersFile;
  }

  public void setOrdersFile(File ordersFile) {
    this.ordersFile = ordersFile;
  }

  public File getSamplesFile() {
    return samplesFile;
  }

  public void setSamplesFile(File samplesFile) {
    this.samplesFile = samplesFile;
  }

  public File getChangesFile() {
    return changesFile;
  }

  public void setChangesFile(File changesFile) {
    this.changesFile = changesFile;
  }

  public File getRunsFile() {
    return runsFile;
  }

  public void setRunsFile(File runsFile) {
    this.runsFile = runsFile;
  }

  public File getUsersFile() {
    return usersFile;
  }

  public void setUsersFile(File usersFile) {
    this.usersFile = usersFile;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Instrument> getInstruments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Instrument> getInstrumentModelInsrument(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

}
