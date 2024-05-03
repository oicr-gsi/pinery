package ca.on.oicr.pinery.api;

import java.util.List;
import java.util.Set;

public interface Requisition {

  Integer getId();

  void setId(Integer id);

  String getName();

  void setName(String name);

  Set<Integer> getAssayIds();

  void setAssayIds(Set<Integer> assayIds);

  void addAssayId(Integer assayId);

  Set<String> getSampleIds();

  void setSampleIds(Set<String> sampleIds);

  void addSampleId(String sampleId);

  Set<String> getSupplementalSampleIds();

  void setSupplementalSampleIds(Set<String> supplementalSampleIds);

  void addSupplementalSampleId(String supplementalSampleId);

  List<SignOff> getSignOffs();

  void setSignOffs(List<SignOff> signOffs);

  void addSignOff(SignOff signOff);

  boolean isStopped();

  void setStopped(boolean stopped);

  String getStopReason();

  void setStopReason(String stopReason);

  List<RequisitionPause> getPauses();

  void setPauses(List<RequisitionPause> pauses);

  void addPause(RequisitionPause pause);
}
