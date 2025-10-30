package ca.on.oicr.pinery.lims;

import java.util.Set;

import ca.on.oicr.pinery.api.RunContainer;
import ca.on.oicr.pinery.api.RunPosition;

public class DefaultRunContainer implements RunContainer {

  private String instrumentPosition;
  private String containerModel;
  private String sequencingParameters;
  private Set<RunPosition> positions;

  public String getInstrumentPosition() {
    return instrumentPosition;
  }

  public void setInstrumentPosition(String instrumentPosition) {
    this.instrumentPosition = instrumentPosition;
  }

  public String getContainerModel() {
    return containerModel;
  }

  public void setContainerModel(String containerModel) {
    this.containerModel = containerModel;
  }

  public String getSequencingParameters() {
    return sequencingParameters;
  }

  public void setSequencingParameters(String sequencingParameters) {
    this.sequencingParameters = sequencingParameters;
  }

  public Set<RunPosition> getPositions() {
    return positions;
  }

  public void setPositions(Set<RunPosition> positions) {
    this.positions = positions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((instrumentPosition == null) ? 0 : instrumentPosition.hashCode());
    result = prime * result + ((containerModel == null) ? 0 : containerModel.hashCode());
    result = prime * result + ((sequencingParameters == null) ? 0 : sequencingParameters.hashCode());
    result = prime * result + ((positions == null) ? 0 : positions.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DefaultRunContainer other = (DefaultRunContainer) obj;
    if (instrumentPosition == null) {
      if (other.instrumentPosition != null)
        return false;
    } else if (!instrumentPosition.equals(other.instrumentPosition))
      return false;
    if (containerModel == null) {
      if (other.containerModel != null)
        return false;
    } else if (!containerModel.equals(other.containerModel))
      return false;
    if (sequencingParameters == null) {
      if (other.sequencingParameters != null)
        return false;
    } else if (!sequencingParameters.equals(other.sequencingParameters))
      return false;
    if (positions == null) {
      if (other.positions != null)
        return false;
    } else if (!positions.equals(other.positions))
      return false;
    return true;
  }

}
