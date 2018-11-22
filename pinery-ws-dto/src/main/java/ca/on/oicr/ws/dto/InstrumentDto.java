package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentDto {

   private String url;
   private String name;
   private String createdDate;
   private Integer id;
   private Integer modelId;
   private String modelUrl;

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

   @JsonProperty("created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   @JsonProperty("model_id")
   public Integer getModelId() {
      return modelId;
   }

   public void setModelId(Integer modelId) {
      this.modelId = modelId;
   }

   @JsonProperty("model_url")
  public String getModelUrl() {
    return modelUrl;
  }

  public void setModelUrl(String modelUrl) {
    this.modelUrl = modelUrl;
  }

}
