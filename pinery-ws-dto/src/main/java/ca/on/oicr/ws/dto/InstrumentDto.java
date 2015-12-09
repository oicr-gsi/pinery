package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class InstrumentDto {

   private String url;
   private String name;
   private String createdDate;
   private Integer id;
   private String instrumentModel;

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

   @JsonProperty("instrument_model")
   public String getInstrumentModel() {
      return instrumentModel;
   }

   public void setInstrumentModel(String instrumentModel) {
      this.instrumentModel = instrumentModel;
   }

}
