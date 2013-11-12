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
      return true;
   }

   @Override
   public String toString() {
      return "DefaultRunPosition [runSample=" + runSample + ", position=" + position + ", getPosition()=" + getPosition()
            + ", getRunSample()=" + getRunSample() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
            + super.toString() + "]";
   }

   private Set<RunSample> runSample;
   private Integer position;

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

}
