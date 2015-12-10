package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.Set;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;

public class DefaultRun implements Run {

   private String state;
   private String name;
   private String barcode;
   private String barcodeTwo;
   private Set<RunPosition> sample;
   private String createdByUrl;
   private Date createdDate;
   private Integer id;
   private Integer createdById;
   private Integer instrumentId;
   private String instrumentName;

   @Override
   public String getState() {
      return state;
   }

   @Override
   public void setState(String state) {
      this.state = state;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public void setName(String name) {
      this.name = name;

   }

   @Override
   public String getBarcode() {
      return barcode;
   }

   @Override
   public void setBarcode(String barcode) {
      this.barcode = barcode;

   }

   @Override
   public Set<RunPosition> getSamples() {
      return sample;
   }

   @Override
   public void setSample(Set<RunPosition> sample) {
      this.sample = sample;
   }

   @Override
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   @Override
   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @Override
   public Date getCreatedDate() {
      return createdDate;
   }

   @Override
   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;

   }

   @Override
   public Integer getId() {
      return id;
   }

   @Override
   public void setId(Integer id) {
      this.id = id;
   }

   @Override
   public Integer getCreatedById() {
      return createdById;
   }

   @Override
   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }

   @Override
   public Integer getInstrumentId() {
      return instrumentId;
   }

   @Override
   public void setInstrumentId(Integer instrumentId) {
      this.instrumentId = instrumentId;
   }

   @Override
   public String getBarcodeTwo() {
      return barcodeTwo;
   }

   @Override
   public void setBarcodeTwo(String barcodeTwo) {
      this.barcodeTwo = barcodeTwo;
   }

   @Override
   public String getInstrumentName() {
     return instrumentName;
   }

   @Override
   public void setInstrumentName(String instrumentName) {
     this.instrumentName = instrumentName;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
      result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
      result = prime * result + ((createdByUrl == null) ? 0 : createdByUrl.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((sample == null) ? 0 : sample.hashCode());
      result = prime * result + ((state == null) ? 0 : state.hashCode());
      result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultRun other = (DefaultRun) obj;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      if (barcodeTwo == null) {
         if (other.barcodeTwo != null) return false;
      } else if (!barcodeTwo.equals(other.barcodeTwo)) return false;
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
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (sample == null) {
         if (other.sample != null) return false;
      } else if (!sample.equals(other.sample)) return false;
      if (state == null) {
         if (other.state != null) return false;
      } else if (!state.equals(other.state)) return false;
      if (instrumentName == null) {
        if (other.instrumentName != null) return false;
     } else if (!instrumentName.equals(other.instrumentName)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "DefaultRun [state=" + state + ", name=" + name + ", barcode=" + barcode + ", barcodeTwo=" + barcodeTwo + ", sample=" + sample
            + ", createdByUrl=" + createdByUrl + ", createdDate=" + createdDate + ", id=" + id + ", createdById=" + createdById
            + ", instrumentId=" + instrumentId+ ", instrumentName=" + instrumentName + "]";
   }

}