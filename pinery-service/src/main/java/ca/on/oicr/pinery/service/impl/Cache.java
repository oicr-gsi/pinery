package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Assay;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.DataProvider;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import io.prometheus.metrics.core.datapoints.Timer;
import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Histogram;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Cache implements DataProvider {

  private static final Logger log = LoggerFactory.getLogger(Cache.class);

  private static final Histogram cacheUpdateTime = Histogram.builder()
      .name("pinery_cache_update_time")
      .help("Time to update the cache (in seconds)")
      .classicUpperBounds(60, 180, 300, 600, 900, 1200, 1800, 3600)
      .register();
  private static final Gauge cacheUpdateFailures = Gauge.builder()
      .name("pinery_cache_update_failures")
      .help("Number of consecutive cache update failures")
      .register();
  private static final Gauge cacheLastUpdated = Gauge.builder()
      .name("pinery_cache_last_updated")
      .help("Timestamp of the last cache update (completion)")
      .register();

  @Autowired
  private Lims lims;

  private List<Sample> samples;
  private List<SampleProject> projects;
  private List<User> users;
  private List<Order> orders;
  private List<AttributeName> sampleAttributes;
  private List<ChangeLog> changeLogs;
  private List<Run> runs;
  private List<Type> sampleTypes;
  private List<Instrument> instruments;
  private List<InstrumentModel> instrumentModels;
  private List<Box> boxes;
  private List<Assay> assays;
  private List<Requisition> requisitions;
  private List<SampleProvenance> sampleProvenance;
  private List<LaneProvenance> laneProvenance;

  @Value("${pinery.cache.enabled}")
  private boolean enabled;

  private boolean cacheComplete = false;
  private boolean updating = false;
  private Instant lastUpdated = null;

  public boolean isEnabled() {
    return enabled;
  }

  public Instant getLastUpdateTime() {
    return lastUpdated;
  }

  public void update() {
    if (!isEnabled()) {
      return;
    }
    log.debug("Update called");
    boolean doUpdate = false;
    synchronized (this) {
      if (updating) {
        while (updating) {
          try {
            log.debug("Waiting for previous update attempt to complete");
            wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
          }
        }
        log.debug("Previous update attempt completed");
        if (!cacheComplete) {
          updating = true;
          doUpdate = true;
        }
      } else {
        updating = true;
        doUpdate = true;
      }
    }

    if (doUpdate) {
      Timer cacheUpdateTimer = cacheUpdateTime.startTimer();
      try {
        log.debug("Attempting update");
        List<Requisition> newRequisitions = lims.getRequisitions();
        List<Run> newRuns = lims.getRuns(null);
        List<Order> newOrders = lims.getOrders();
        List<Box> newBoxes = lims.getBoxes();
        List<Sample> newSamples = lims.getSamples(null, null, null, null, null);
        List<SampleProject> newProjects = lims.getSampleProjects();
        List<User> newUsers = lims.getUsers();
        List<Type> newSampleTypes = lims.getTypes();
        List<ChangeLog> newChangeLogs = lims.getChangeLogs();
        List<Instrument> newInstruments = lims.getInstruments();
        List<InstrumentModel> newInstrumentModels = lims.getInstrumentModels();
        List<Assay> newAssays = lims.getAssays();
        List<AttributeName> newSampleAttributes = lims.getAttributeNames();

        List<SampleProvenance> newSampleProvenance = ProvenanceUtils.buildSampleProvenance(
            newProjects, newInstruments, newInstrumentModels, newOrders, newSamples, newRuns);
        List<LaneProvenance> newLaneProvenance = ProvenanceUtils.buildLaneProvenance(newInstruments,
            newInstrumentModels, newRuns);

        synchronized (this) {
          this.samples = newSamples;
          this.projects = newProjects;
          this.users = newUsers;
          this.orders = newOrders;
          this.runs = newRuns;
          this.sampleTypes = newSampleTypes;
          this.sampleAttributes = newSampleAttributes;
          this.changeLogs = newChangeLogs;
          this.instruments = newInstruments;
          this.instrumentModels = newInstrumentModels;
          this.boxes = newBoxes;
          this.assays = newAssays;
          this.requisitions = newRequisitions;
          this.sampleProvenance = newSampleProvenance;
          this.laneProvenance = newLaneProvenance;

          cacheComplete = true;
          lastUpdated = Instant.now();
          cacheUpdateFailures.set(0);
          cacheLastUpdated.set(System.currentTimeMillis());
          log.debug("Update successful");
        }
      } catch (RuntimeException e) {
        cacheUpdateFailures.inc();
        throw e;
      } finally {
        synchronized (this) {
          cacheUpdateTimer.observeDuration();
          log.debug("Update attempt completed; notifying others");
          updating = false;
          notifyAll();
        }
      }
    }
  }

  public synchronized void updateIfEmpty() {
    if (!enabled) {
      throw new IllegalStateException("Cannot retrieve data from disabled cache");
    }
    if (!cacheComplete) {
      update();
    }
  }

  @Override
  public synchronized Sample getSample(String id) {
    updateIfEmpty();
    return getBy(samples, Sample::getId, id);
  }

  @Override
  public synchronized List<SampleProject> getSampleProjects() {
    updateIfEmpty();
    return projects;
  }

  @Override
  public synchronized List<Sample> getSamples(
      Boolean archived,
      Set<String> projects,
      Set<String> types,
      ZonedDateTime before,
      ZonedDateTime after) {
    updateIfEmpty();

    Set<String> normalizedProjects = normalizeSet(projects);
    Set<String> normalizedTypes = normalizeSet(types);
    Date beforeDate = before == null ? null : Date.from(before.toInstant());
    Date afterDate = after == null ? null : Date.from(after.toInstant());

    return samples.stream()
        .filter(
            sample -> {
              if (archived != null && !archived.equals(sample.getArchived())) {
                return false;
              }
              if (normalizedProjects != null && !normalizedProjects.contains(sample.getProject())) {
                return false;
              }
              if (normalizedTypes != null && !normalizedTypes.contains(sample.getSampleType())) {
                return false;
              }
              if (beforeDate != null && beforeDate.after(sample.getCreated())) {
                return false;
              }
              if (afterDate != null && afterDate.before(sample.getModified())) {
                return false;
              }
              return true;
            })
        .collect(Collectors.toList());
  }

  private Set<String> normalizeSet(Set<String> original) {
    if (original == null || original.isEmpty()) {
      return null;
    }
    Set<String> filtered = original.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    return filtered.isEmpty() ? null : filtered;
  }

  @Override
  public synchronized List<User> getUsers() {
    updateIfEmpty();
    return users;
  }

  @Override
  public synchronized User getUser(Integer id) {
    updateIfEmpty();
    return getBy(users, User::getId, id);
  }

  @Override
  public synchronized List<Order> getOrders() {
    updateIfEmpty();
    return orders;
  }

  @Override
  public synchronized Order getOrder(Integer id) {
    updateIfEmpty();
    return getBy(orders, Order::getId, id);
  }

  @Override
  public synchronized List<Run> getRuns(Set<String> sampleIds) {
    updateIfEmpty();
    if (sampleIds == null) {
      return runs;
    } else {
      return runs.stream()
          .filter(
              run -> run.getSamples().stream()
                  .flatMap(pos -> pos.getRunSample().stream())
                  .anyMatch(sample -> sampleIds.contains(sample.getId())))
          .collect(Collectors.toList());
    }
  }

  @Override
  public synchronized Run getRun(Integer id) {
    updateIfEmpty();
    return getBy(runs, Run::getId, id);
  }

  @Override
  public synchronized Run getRun(String runName) {
    updateIfEmpty();
    return getBy(runs, Run::getName, runName);
  }

  @Override
  public synchronized List<Type> getTypes() {
    updateIfEmpty();
    return sampleTypes;
  }

  @Override
  public synchronized List<AttributeName> getAttributeNames() {
    updateIfEmpty();
    return sampleAttributes;
  }

  @Override
  public synchronized List<ChangeLog> getChangeLogs() {
    updateIfEmpty();
    return changeLogs;
  }

  @Override
  public synchronized ChangeLog getChangeLog(String id) {
    updateIfEmpty();
    return getBy(changeLogs, ChangeLog::getSampleId, id);
  }

  @Override
  public synchronized List<InstrumentModel> getInstrumentModels() {
    updateIfEmpty();
    return instrumentModels;
  }

  @Override
  public synchronized InstrumentModel getInstrumentModel(Integer id) {
    updateIfEmpty();
    return getBy(instrumentModels, InstrumentModel::getId, id);
  }

  @Override
  public synchronized List<Instrument> getInstruments() {
    updateIfEmpty();
    return instruments;
  }

  @Override
  public synchronized Instrument getInstrument(Integer instrumentId) {
    updateIfEmpty();
    return getBy(instruments, Instrument::getId, instrumentId);
  }

  @Override
  public synchronized List<Instrument> getInstrumentModelInstrument(Integer id) {
    updateIfEmpty();
    return instruments.stream()
        .filter(instrument -> id != null && id.equals(instrument.getModelId()))
        .collect(Collectors.toList());
  }

  @Override
  public synchronized List<Box> getBoxes() {
    updateIfEmpty();
    return boxes;
  }

  @Override
  public List<Assay> getAssays() {
    updateIfEmpty();
    return assays;
  }

  @Override
  public Assay getAssay(Integer id) {
    updateIfEmpty();
    return getBy(assays, Assay::getId, id);
  }

  @Override
  public List<Requisition> getRequisitions() {
    updateIfEmpty();
    return requisitions;
  }

  @Override
  public Requisition getRequisition(Integer id) {
    updateIfEmpty();
    return getBy(requisitions, Requisition::getId, id);
  }

  @Override
  public Requisition getRequisition(String name) {
    updateIfEmpty();
    return getBy(requisitions, Requisition::getName, name);
  }

  public synchronized List<SampleProvenance> getSampleProvenance() {
    updateIfEmpty();
    return sampleProvenance;
  }

  public synchronized List<LaneProvenance> getLaneProvenance() {
    updateIfEmpty();
    return laneProvenance;
  }

  private <K, V> V getBy(List<V> items, Function<V, K> getter, K value) {
    return items.stream()
        .filter(item -> value != null && value.equals(getter.apply(item)))
        .findAny()
        .orElse(null);
  }
}
