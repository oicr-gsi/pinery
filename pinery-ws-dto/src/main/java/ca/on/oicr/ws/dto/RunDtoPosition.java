package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RunDtoPosition {

   private Set<RunDtoSample> runSample;
   private Integer position;
   private String poolName;
   private String poolDescription;
   private String poolBarcode;

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

   @JsonProperty("pool_name")
   public String getPoolName() {
     return poolName;
   }

   public void setPoolName(String poolName) {
     this.poolName = poolName;
   }

   @JsonProperty("pool_barcode")
   public String getPoolBarcode() {
     return poolBarcode;
   }

   public void setPoolBarcode(String poolBarcode) {
     this.poolBarcode = poolBarcode;
   }

   @JsonProperty("pool_description")
   public String getPoolDescription() {
     return poolDescription;
   }

   public void setPoolDescription(String poolDescription) {
     this.poolDescription = poolDescription;
   }

   @Override
   public String toString() {
      return "RunDtoPosition [runSample=" + runSample + ", position=" + position + ", hashCode()=" + hashCode() + ", getPosition()="
            + getPosition() + ", getRunSamples()=" + getSamples() + ", getPoolName()=" + getPoolName()
            + ", getPoolBarcode()=" + getPoolBarcode() + ", getPoolDescription()=" + getPoolDescription() + ", getClass()=" + getClass()
            + ", toString()=" + super.toString() + "]";
   }

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
      RunDtoPosition other = (RunDtoPosition) obj;
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

}
