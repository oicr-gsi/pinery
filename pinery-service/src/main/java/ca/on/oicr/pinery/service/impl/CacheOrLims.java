package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.DataProvider;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheOrLims implements DataProvider {

  @Autowired private Lims lims;
  @Autowired private Cache cache;

  private <T> T get(Function<DataProvider, T> getter) {
    return getter.apply(isCacheEnabled() ? cache : lims);
  }

  public boolean isCacheEnabled() {
    return cache.isEnabled();
  }

  @Override
  public Sample getSample(String id) {
    return get(provider -> provider.getSample(id));
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    return get(DataProvider::getSampleProjects);
  }

  @Override
  public List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after) {
    return get(provider -> provider.getSamples(archived, projects, types, before, after));
  }

  @Override
  public List<User> getUsers() {
    return get(DataProvider::getUsers);
  }

  @Override
  public User getUser(Integer id) {
    return get(provider -> provider.getUser(id));
  }

  @Override
  public List<Order> getOrders() {
    return get(DataProvider::getOrders);
  }

  @Override
  public Order getOrder(Integer id) {
    return get(provider -> provider.getOrder(id));
  }

  @Override
  public List<Run> getRuns() {
    return get(DataProvider::getRuns);
  }

  @Override
  public Run getRun(Integer id) {
    return get(provider -> provider.getRun(id));
  }

  @Override
  public Run getRun(String runName) {
    return get(provider -> provider.getRun(runName));
  }

  @Override
  public List<Type> getTypes() {
    return get(DataProvider::getTypes);
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    return get(DataProvider::getAttributeNames);
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    return get(DataProvider::getChangeLogs);
  }

  @Override
  public ChangeLog getChangeLog(String id) {
    return get(provider -> provider.getChangeLog(id));
  }

  @Override
  public List<InstrumentModel> getInstrumentModels() {
    return get(DataProvider::getInstrumentModels);
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    return get(provider -> provider.getInstrumentModel(id));
  }

  @Override
  public List<Instrument> getInstruments() {
    return get(DataProvider::getInstruments);
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    return get(provider -> provider.getInstrument(instrumentId));
  }

  @Override
  public List<Instrument> getInstrumentModelInstrument(Integer id) {
    return get(provider -> provider.getInstrumentModelInstrument(id));
  }

  @Override
  public List<Box> getBoxes() {
    return get(DataProvider::getBoxes);
  }

  public List<SampleProvenance> getCachedSampleProvenance() {
    return cache.getSampleProvenance();
  }

  public List<LaneProvenance> getCachedLaneProvenance() {
    return cache.getLaneProvenance();
  }
}
