package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Requisition;
import ca.on.oicr.pinery.api.RequisitionPause;
import ca.on.oicr.pinery.api.SignOff;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DefaultRequisition implements Requisition {

  private Integer id;
  private String name;
  private Integer assayId;
  private Set<String> sampleIds;
  private Set<String> supplementalSampleIds;
  private List<SignOff> signOffs;
  private boolean stopped = false;
  private String stopReason;
  private List<RequisitionPause> pauses;

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
  public Integer getAssayId() {
    return assayId;
  }

  @Override
  public void setAssayId(Integer assayId) {
    this.assayId = assayId;
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
  public int hashCode() {
    return Objects.hash(assayId, id, name, sampleIds, signOffs, stopped, stopReason);
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
    return Objects.equals(assayId, other.assayId)
        && Objects.equals(id, other.id)
        && Objects.equals(name, other.name)
        && Objects.equals(sampleIds, other.sampleIds)
        && Objects.equals(signOffs, other.signOffs)
        && Objects.equals(stopped, other.stopped)
        && Objects.equals(stopReason, other.stopReason);
  }
}
