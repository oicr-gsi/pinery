package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.BoxPosition;

public class DefaultBoxPosition implements BoxPosition {
  
  private String position;
  private String sampleId;

  @Override
  public String getPosition() {
    return position;
  }

  @Override
  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String getSampleId() {
    return sampleId;
  }

  @Override
  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    result = prime * result + ((sampleId == null) ? 0 : sampleId.hashCode());
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
    DefaultBoxPosition other = (DefaultBoxPosition) obj;
    if (position == null) {
      if (other.position != null)
        return false;
    }
    else if (!position.equals(other.position))
      return false;
    if (sampleId == null) {
      if (other.sampleId != null)
        return false;
    }
    else if (!sampleId.equals(other.sampleId))
      return false;
    return true;
  }

}
