package ca.on.oicr.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "sequencer_run")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class RunDto {

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      result = prime * result + ((createdByUrl == null) ? 0 : createdByUrl.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
      result = prime * result + ((instrumentUrl == null) ? 0 : instrumentUrl.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((positions == null) ? 0 : positions.hashCode());
      result = prime * result + ((state == null) ? 0 : state.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      RunDto other = (RunDto) obj;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      if (createdByUrl == null) {
         if (other.createdByUrl != null) return false;
      } else if (!createdByUrl.equals(other.createdByUrl)) return false;
      if (createdDate == null) {
         if (other.createdDate != null) return false;
      } else if (!createdDate.equals(other.createdDate)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (instrumentName == null) {
         if (other.instrumentName != null) return false;
      } else if (!instrumentName.equals(other.instrumentName)) return false;
      if (instrumentUrl == null) {
         if (other.instrumentUrl != null) return false;
      } else if (!instrumentUrl.equals(other.instrumentUrl)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (positions == null) {
         if (other.positions != null) return false;
      } else if (!positions.equals(other.positions)) return false;
      if (state == null) {
         if (other.state != null) return false;
      } else if (!state.equals(other.state)) return false;
      if (url == null) {
         if (other.url != null) return false;
      } else if (!url.equals(other.url)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "RunDto [state=" + state + ", name=" + name + ", barcode=" + barcode + ", instrumentName=" + instrumentName + ", positions="
            + positions + ", createdByUrl=" + createdByUrl + ", createdDate=" + createdDate + ", id=" + id + ", url=" + url
            + ", instrumentUrl=" + instrumentUrl + ", hashCode()=" + hashCode() + ", getState()=" + getState() + ", getName()=" + getName()
            + ", getBarcode()=" + getBarcode() + ", getPositions()=" + getPositions() + ", getCreatedByUrl()=" + getCreatedByUrl()
            + ", getCreatedDate()=" + getCreatedDate() + ", getId()=" + getId() + ", getUrl()=" + getUrl() + ", getInstrument_Url()="
            + getInstrument_Url() + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
   }

   private String state;
   private String name;
   private String barcode;
   @JsonProperty("instrument_name")
   private String instrumentName;
   private Set<RunDtoPosition> positions;
   @JsonProperty("created_by_url")
   private String createdByUrl;
   @JsonProperty("created_date")
   private String createdDate;
   private Integer id;
   private String url;
   @JsonProperty("instrument_url")
   private String instrumentUrl;

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public Set<RunDtoPosition> getPositions() {
      return positions;
   }

   public void setPositions(Set<RunDtoPosition> positions) {
      this.positions = positions;
   }

   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
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

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getInstrument_Url() {
      return instrumentUrl;
   }

   public void setInstrument_Url(String instrumentUrl) {
      this.instrumentUrl = instrumentUrl;
   }

}
