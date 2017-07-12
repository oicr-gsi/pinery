package ca.on.oicr.pinery.lims;

import java.util.Set;

import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;

public class DefaultRunPosition implements RunPosition {

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((position == null) ? 0 : position.hashCode());
      result = prime * result + ((runSample == null) ? 0 : runSample.hashCode());
      result = prime * result + ((poolName == null) ? 0 : poolName.hashCode());
      result = prime * result + ((poolBarcode == null) ? 0 : poolBarcode.hashCode());
      result = prime * result + ((poolDescription == null) ? 0 : poolDescription.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultRunPosition other = (DefaultRunPosition) obj;
      if (position == null) {
         if (other.position != null) return false;
      } else if (!position.equals(other.position)) return false;
      if (runSample == null) {
         if (other.runSample != null) return false;
      } else if (!runSample.equals(other.runSample)) return false;
      if (poolName == null) {
         if (other.poolName != null) return false;
      } else if (!poolName.equals(other.poolName)) return false;
      if (poolBarcode == null) {
         if (other.poolBarcode != null) return false;
      } else if (!poolBarcode.equals(other.poolBarcode)) return false;
      if (poolDescription == null) {
         if (other.poolDescription != null) return false;
      } else if (!poolDescription.equals(other.poolDescription)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "DefaultRunPosition [runSample=" + runSample + ", position=" + position + ", getPosition()=" + getPosition()
            + ", getRunSample()=" + getRunSample() + ", getPoolName()=" + getPoolName() + ", getPoolBarcode()=" + getPoolBarcode()
            + ", getPoolDescription()=" + getPoolDescription() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
            + ", toString()=" + super.toString() + "]";
   }

   private Set<RunSample> runSample;
   private Integer position;
   private String poolName;
   private String poolDescription;
   private String poolBarcode;

   @Override
   public Integer getPosition() {
      return position;
   }

   @Override
   public void setPosition(Integer position) {
      this.position = position;
   }

   @Override
   public Set<RunSample> getRunSample() {
      return runSample;
   }

   @Override
   public void setRunSample(Set<RunSample> runSample) {
      this.runSample = runSample;
   }

  @Override
  public String getPoolName() {
    return poolName;
  }

  @Override
  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  @Override
  public String getPoolBarcode() {
    return poolBarcode;
  }

  @Override
  public void setPoolBarcode(String poolBarcode) {
    this.poolBarcode = poolBarcode;
  }

  @Override
  public String getPoolDescription() {
    return poolDescription;
  }

  @Override
  public void setPoolDescription(String poolDescription) {
    this.poolDescription = poolDescription;
  }

}
