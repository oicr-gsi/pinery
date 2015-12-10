package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RunDto {

   private String state;
   private String name;
   private String barcode;
   private String instrumentName;
   private Set<RunDtoPosition> positions;
   private Integer createdById;
   private String createdByUrl;
   private String createdDate;
   private Integer id;
   private String url;
   private Integer instrumentId;
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

   @JsonProperty("created_by_id")
   public Integer getCreatedById() {
      return createdById;
   }

   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }

   @JsonProperty("created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
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

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @JsonProperty("instrument_name")
   public String getInstrumentName() {
      return instrumentName;
   }

   public void setInstrumentName(String instrumentName) {
      this.instrumentName = instrumentName;
   }

   @JsonProperty("instrument_id")
   public Integer getInstrumentId() {
      return instrumentId;
   }

   public void setInstrumentId(Integer instrumentId) {
      this.instrumentId = instrumentId;
   }

   @JsonProperty("instrument_url")
   public String getInstrumentUrl() {
      return instrumentUrl;
   }

   public void setInstrumentUrl(String instrumentUrl) {
      this.instrumentUrl = instrumentUrl;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
      result = prime * result + ((createdByUrl == null) ? 0 : createdByUrl.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
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
      if (createdById == null) {
         if (other.createdById != null) return false;
      } else if (!createdById.equals(other.createdById)) return false;
      if (createdByUrl == null) {
         if (other.createdByUrl != null) return false;
      } else if (!createdByUrl.equals(other.createdByUrl)) return false;
      if (createdDate == null) {
         if (other.createdDate != null) return false;
      } else if (!createdDate.equals(other.createdDate)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (instrumentId == null) {
         if (other.instrumentId != null) return false;
      } else if (!instrumentId.equals(other.instrumentId)) return false;
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
            + positions + ", createdById=" + createdById + ", createdByUrl=" + createdByUrl + ", createdDate=" + createdDate + ", id=" + id + ", url=" + url + ", instrumentId=" + instrumentId
            + ", instrumentUrl=" + instrumentUrl + "]";
   }

}
