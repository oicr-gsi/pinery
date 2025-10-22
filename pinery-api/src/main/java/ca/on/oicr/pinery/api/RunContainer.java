package ca.on.oicr.pinery.api;

import java.util.Set;

public interface RunContainer {

  String getInstrumentPosition();

  void setInstrumentPosition(String instrumentPosition);

  String getContainerModel();

  void setContainerModel(String containerModel);

  String getSequencingParameters();

  void setSequencingParameters(String sequencingParameters);

  Set<RunPosition> getPositions();

  void setPositions(Set<RunPosition> positions);

}
