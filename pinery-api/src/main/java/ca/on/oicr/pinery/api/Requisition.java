package ca.on.oicr.pinery.api;

import java.util.List;
import java.util.Set;

public interface Requisition {

  Integer getId();

  void setId(Integer id);

  String getName();

  void setName(String name);

  Integer getAssayId();

  void setAssayId(Integer assayId);

  Set<String> getSampleIds();

  void setSampleIds(Set<String> sampleIds);

  void addSampleId(String sampleId);

  List<SignOff> getSignOffs();

  void setSignOffs(List<SignOff> signOffs);

  void addSignOff(SignOff signOff);

  boolean isStopped();

  void setStopped(boolean stopped);
}
