package ca.on.oicr.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "instrument_model")
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
   @JsonProperty("instrument_url")
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

   @XmlElement(name = "created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @XmlElement(name = "created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @XmlElement(name = "modified_date")
   public String getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(String modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   @XmlElement(name = "modified_by_url")
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

   @XmlElement(name = "instruments_url")
   public String getInstrumentsUrl() {
      return instrumentsUrl;
   }

   public void setInstrumentsUrl(String instrumentsUrl) {
      this.instrumentsUrl = instrumentsUrl;
   }

}
