package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RunDtoPosition {

   private Set<RunDtoSample> runSample;
   private Integer position;

   public Integer getPosition() {
      return position;
   }

   public void setPosition(Integer position) {
      this.position = position;
   }

   public Set<RunDtoSample> getSamples() {
      return runSample;
   }

   public void setSamples(Set<RunDtoSample> runSample) {
      this.runSample = runSample;
   }

   @Override
   public String toString() {
      return "RunDtoPosition [runSample=" + runSample + ", position=" + position + ", hashCode()=" + hashCode() + ", getPosition()="
            + getPosition() + ", getRunSamples()=" + getSamples() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
   }

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
      RunDtoPosition other = (RunDtoPosition) obj;
      if (position == null) {
         if (other.position != null) return false;
      } else if (!position.equals(other.position)) return false;
      if (runSample == null) {
         if (other.runSample != null) return false;
      } else if (!runSample.equals(other.runSample)) return false;
      return true;
   }

}
