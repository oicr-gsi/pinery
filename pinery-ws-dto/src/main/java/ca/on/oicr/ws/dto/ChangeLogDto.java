package ca.on.oicr.ws.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ChangeLogDto {

   private String sampleId;
   private String sampleUrl;
   private List<ChangeDto> changes;

   @JsonProperty("sample_id")
   public String getSampleId() {
      return sampleId;
   }

   public void setSampleId(String sampleId) {
      this.sampleId = sampleId;
   }

   @JsonProperty("sample_url")
   public String getSampleUrl() {
      return sampleUrl;
   }

   public void setSampleUrl(String sampleUrl) {
      this.sampleUrl = sampleUrl;
   }

   public List<ChangeDto> getChanges() {
      return changes;
   }

   public void setChanges(List<ChangeDto> changes) {
      this.changes = changes;
   }

}
