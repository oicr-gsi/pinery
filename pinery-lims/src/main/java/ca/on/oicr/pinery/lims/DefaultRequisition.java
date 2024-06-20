package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.api.RequisitionPause;
import ca.on.oicr.pinery.api.SignOff;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DefaultRequisition implements Requisition {

  private Integer id;
  private String name;
  private Set<Integer> assayIds;
  private Set<String> sampleIds;
  private Set<String> supplementalSampleIds;
  private List<SignOff> signOffs;
  private boolean stopped = false;
  private String stopReason;
  private List<RequisitionPause> pauses;
  private Instant created;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Set<Integer> getAssayIds() {
    return assayIds;
  }

  @Override
  public void addAssayId(Integer assayId) {
    if (assayIds == null) {
      assayIds = new HashSet<>();
    }
    assayIds.add(assayId);
  }

  @Override
  public void setAssayIds(Set<Integer> assayIds) {
    this.assayIds = assayIds;
  }

  @Override
  public Set<String> getSampleIds() {
    return sampleIds;
  }

  @Override
  public void setSampleIds(Set<String> sampleIds) {
    this.sampleIds = sampleIds;
  }

  @Override
  public void addSampleId(String sampleId) {
    if (sampleIds == null) {
      sampleIds = new HashSet<>();
    }
    sampleIds.add(sampleId);
  }

  @Override
  public Set<String> getSupplementalSampleIds() {
    return supplementalSampleIds;
  }

  @Override
  public void setSupplementalSampleIds(Set<String> supplementalSampleIds) {
    this.supplementalSampleIds = supplementalSampleIds;
  }

  @Override
  public void addSupplementalSampleId(String supplementalSampleId) {
    if (supplementalSampleIds == null) {
      supplementalSampleIds = new HashSet<>();
    }
    supplementalSampleIds.add(supplementalSampleId);
  }

  @Override
  public List<SignOff> getSignOffs() {
    return signOffs;
  }

  @Override
  public void setSignOffs(List<SignOff> signOffs) {
    this.signOffs = signOffs;
  }

  @Override
  public void addSignOff(SignOff signOff) {
    if (signOffs == null) {
      signOffs = new ArrayList<>();
    }
    signOffs.add(signOff);
  }

  @Override
  public boolean isStopped() {
    return stopped;
  }

  @Override
  public void setStopped(boolean stopped) {
    this.stopped = stopped;
  }

  @Override
  public String getStopReason() {
    return stopReason;
  }

  @Override
  public void setStopReason(String stopReason) {
    this.stopReason = stopReason;
  }

  @Override
  public List<RequisitionPause> getPauses() {
    return pauses;
  }

  @Override
  public void setPauses(List<RequisitionPause> pauses) {
    this.pauses = pauses;
  }

  @Override
  public void addPause(RequisitionPause pause) {
    if (pauses == null) {
      pauses = new ArrayList<>();
    }
    pauses.add(pause);
  }

  @Override
  public Instant getCreated() {
    return created;
  }

  @Override
  public void setCreated(Instant created) {
    this.created = created;
  }

  @Override
  public int hashCode() {
    return Objects.hash(assayIds, id, name, sampleIds, signOffs, stopped, stopReason, created);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DefaultRequisition other = (DefaultRequisition) obj;
    return Objects.equals(assayIds, other.assayIds)
        && Objects.equals(id, other.id)
        && Objects.equals(name, other.name)
        && Objects.equals(sampleIds, other.sampleIds)
        && Objects.equals(signOffs, other.signOffs)
        && Objects.equals(stopped, other.stopped)
        && Objects.equals(stopReason, other.stopReason)
        && Objects.equals(created, other.created);
  }
}
