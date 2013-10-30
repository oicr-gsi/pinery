package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.OrderSample;

public class DefaultOrderSample extends DefaultSample implements OrderSample {

   @Override
   public String toString() {
      return "DefaultOrderSample [barcode=" + barcode + ", hashCode()=" + hashCode() + ", getBarcode()=" + getBarcode()
            + ", getOrCreateStatus()=" + getOrCreateStatus() + ", getStatus()=" + getStatus() + ", getPreparationKit()="
            + getPreparationKit() + ", getOrCreatePreparationKit()=" + getOrCreatePreparationKit() + ", getUrl()=" + getUrl()
            + ", getName()=" + getName() + ", getDescription()=" + getDescription() + ", getId()=" + getId() + ", getSampleType()="
            + getSampleType() + ", getTissueType()=" + getTissueType() + ", getProject()=" + getProject() + ", getAttributes()="
            + getAttributes() + ", getArchived()=" + getArchived() + ", getCreated()=" + getCreated() + ", getModified()=" + getModified()
            + ", getTubeBarcode()=" + getTubeBarcode() + ", getStorageLocation()=" + getStorageLocation() + ", getVolume()=" + getVolume()
            + ", getConcentration()=" + getConcentration() + ", getParents()=" + getParents() + ", getChildren()=" + getChildren()
            + ", getCreatedById()=" + getCreatedById() + ", getModifiedById()=" + getModifiedById() + ", getClass()=" + getClass()
            + ", toString()=" + super.toString() + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultOrderSample other = (DefaultOrderSample) obj;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      return true;
   }

   private String barcode;

   @Override
   public String getBarcode() {
      return barcode;
   }

   @Override
   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }
}
