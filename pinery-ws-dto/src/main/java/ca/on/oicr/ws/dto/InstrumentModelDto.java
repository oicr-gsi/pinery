package ca.on.oicr.ws.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class InstrumentModelDto {

   private String url;
   private String name;
   @JsonProperty("created_date")
   private String createdDate;
   @JsonProperty("created_by_url")
   private String createdByUrl;
   @JsonProperty("modified_date")
   private String modifiedDate;
   @JsonProperty("modified_by_url")
   private String modifiedByUrl;
   @JsonProperty("instruments_url")
   private String instrumentsUrl;
   private Integer id;

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   public String getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(String modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public String getModifiedByUrl() {
      return modifiedByUrl;
   }

   public void setModifiedByUrl(String modifiedByUrl) {
      this.modifiedByUrl = modifiedByUrl;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getInstrumentsUrl() {
      return instrumentsUrl;
   }

   public void setInstrumentsUrl(String instrumentsUrl) {
      this.instrumentsUrl = instrumentsUrl;
   }

}
