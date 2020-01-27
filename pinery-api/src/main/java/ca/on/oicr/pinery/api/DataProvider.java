package ca.on.oicr.pinery.api;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface DataProvider {

  public Sample getSample(String id);

  /**
   * Returns a list of project names. Useful for retrieving samples that belong to a particular
   * project.
   *
   * @return
   */
  public List<SampleProject> getSampleProjects();

  public List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after);

  public List<User> getUsers();

  public User getUser(Integer id);

  public List<Order> getOrders();

  public Order getOrder(Integer id);

  public List<Run> getRuns();

  public Run getRun(Integer id);

  public Run getRun(String runName);

  public List<Type> getTypes();

  public List<AttributeName> getAttributeNames();

  public List<ChangeLog> getChangeLogs();

  public ChangeLog getChangeLog(String id);

  public List<InstrumentModel> getInstrumentModels();

  public InstrumentModel getInstrumentModel(Integer id);

  public List<Instrument> getInstruments();

  public Instrument getInstrument(Integer instrumentId);

  public List<Instrument> getInstrumentModelInstrument(Integer id);

  public List<Box> getBoxes();
}
