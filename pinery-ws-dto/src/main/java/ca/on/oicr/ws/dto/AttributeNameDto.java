package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class AttributeNameDto {

   private String name;
   private Integer count;
   private Integer archivedCount;
   private String earliest;
   private String latest;

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

}
