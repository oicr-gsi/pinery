package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleProjectDto {

   private String name;
   private Integer count;
   private Integer archivedCount;
   private String earliest;
   private String latest;
   private boolean active;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getCount() {
      return count;
   }

   public void setCount(Integer count) {
      this.count = count;
   }

   public String getEarliest() {
      return earliest;
   }

   public void setEarliest(String earliest) {
      this.earliest = earliest;
   }

   public String getLatest() {
      return latest;
   }

   public void setLatest(String latest) {
      this.latest = latest;
   }

   @JsonProperty("archived_count")
   public Integer getArchivedCount() {
      return archivedCount;
   }

   public void setArchivedCount(Integer archivedCount) {
      this.archivedCount = archivedCount;
   }

   public boolean isActive() {
     return active;
   }

   public void setActive(boolean active) {
     this.active = active;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((archivedCount == null) ? 0 : archivedCount.hashCode());
      result = prime * result + ((count == null) ? 0 : count.hashCode());
      result = prime * result + ((earliest == null) ? 0 : earliest.hashCode());
      result = prime * result + ((latest == null) ? 0 : latest.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + Boolean.hashCode(active);
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      SampleProjectDto other = (SampleProjectDto) obj;
      if (archivedCount == null) {
         if (other.archivedCount != null) return false;
      } else if (!archivedCount.equals(other.archivedCount)) return false;
      if (count == null) {
         if (other.count != null) return false;
      } else if (!count.equals(other.count)) return false;
      if (earliest == null) {
         if (other.earliest != null) return false;
      } else if (!earliest.equals(other.earliest)) return false;
      if (latest == null) {
         if (other.latest != null) return false;
      } else if (!latest.equals(other.latest)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (active != other.active) return false;
      return true;
   }

   @Override
   public String toString() {
      return "SampleProjectDto [name=" + name + ", count=" + count + ", archivedCount=" + archivedCount + ", earliest=" + earliest
            + ", latest=" + latest + ", active=" + active + "]";
   }

}
