package ca.on.oicr.ws.dto;

public class OrderDtoSample extends SampleDto {

   @Override
   public String toString() {
      return "OrderDtoSample [barcode=" + barcode + ", hashCode()=" + hashCode() + ", getBarcode()=" + getBarcode() + ", toString()="
            + super.toString() + ", getUrl()=" + getUrl() + ", getName()=" + getName() + ", getDescription()=" + getDescription()
            + ", getId()=" + getId() + ", getTubeBarcode()=" + getTubeBarcode() + ", getStorageLocation()=" + getStorageLocation()
            + ", getCreatedDate()=" + getCreatedDate() + ", getModifiedDate()=" + getModifiedDate() + ", getArchived()=" + getArchived()
            + ", getPreparationKit()=" + getPreparationKit() + ", getVolume()=" + getVolume() + ", getConcentration()="
            + getConcentration() + ", getProjectName()=" + getProjectName() + ", getSampleType()=" + getSampleType() + ", getAttributes()="
            + getAttributes() + ", getStatus()=" + getStatus() + ", getChildren()=" + getChildren() + ", getParents()=" + getParents()
            + ", getCreatedByUrl()=" + getCreatedByUrl() + ", getModifiedByUrl()=" + getModifiedByUrl() + ", getClass()=" + getClass()
            + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (!super.equals(obj)) return false;
      if (getClass() != obj.getClass()) return false;
      OrderDtoSample other = (OrderDtoSample) obj;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      return true;
   }

   private String barcode;
   private String url;

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }
}
