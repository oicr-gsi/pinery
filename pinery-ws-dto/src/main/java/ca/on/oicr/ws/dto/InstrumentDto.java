package ca.on.oicr.ws.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class InstrumentDto {

   private String url;
   private String name;
   @JsonProperty("created_date")
   private String createdDate;
   private Integer id;
   @JsonProperty("instrument_model")
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

   public String getInstrumentModel() {
      return instrumentModel;
   }

   public void setInstrumentModel(String instrumentModel) {
      this.instrumentModel = instrumentModel;
   }

}
