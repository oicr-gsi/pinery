package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentModelDto {

   private String url;
   private String name;
   private String platform;
   private String createdDate;
   private Integer createdById;
   private String createdByUrl;
   private String modifiedDate;
   private Integer modifiedById;
   private String modifiedByUrl;
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

   public String getPlatform() {
      return platform;
   }

   public void setPlatform(String platform) {
      this.platform = platform;
   }

   @JsonProperty("created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @JsonProperty("created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @JsonProperty("modified_date")
   public String getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(String modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   @JsonProperty("modified_by_url")
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

   @JsonProperty("instruments_url")
   public String getInstrumentsUrl() {
      return instrumentsUrl;
   }

   public void setInstrumentsUrl(String instrumentsUrl) {
      this.instrumentsUrl = instrumentsUrl;
   }
   
   @JsonProperty("created_by_id")
   public Integer getCreatedById() {
      return createdById;
   }

   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }

   @JsonProperty("modified_by_id")
   public Integer getModifiedById() {
      return modifiedById;
   }

   public void setModifiedById(Integer modifiedById) {
      this.modifiedById = modifiedById;
   }

}
