package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.RunSample;

public class DefaultRunSample extends DefaultSample implements RunSample {

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
      DefaultRunSample other = (DefaultRunSample) obj;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "DefaultRunSample [barcode=" + barcode + ", url=" + url + ", name=" + name + ", description=" + description + ", id=" + id
            + ", parents=" + parents + ", children=" + children + ", sampleType=" + sampleType + ", tissueType=" + tissueType
            + ", project=" + project + ", attributes=" + attributes + ", archived=" + archived + ", created=" + created + ", createdById="
            + createdById + ", modified=" + modified + ", modifiedById=" + modifiedById + ", tubeBarcode=" + tubeBarcode + ", volume="
            + volume + ", concentration=" + concentration + ", storageLocation=" + storageLocation + ", preparationKit=" + preparationKit
            + ", status=" + status + ", getBarcode()=" + getBarcode() + ", hashCode()=" + hashCode() + ", getOrCreateStatus()="
            + getOrCreateStatus() + ", getStatus()=" + getStatus() + ", getPreparationKit()=" + getPreparationKit()
            + ", getOrCreatePreparationKit()=" + getOrCreatePreparationKit() + ", getUrl()=" + getUrl() + ", getName()=" + getName()
            + ", getDescription()=" + getDescription() + ", getId()=" + getId() + ", getSampleType()=" + getSampleType()
            + ", getTissueType()=" + getTissueType() + ", getProject()=" + getProject() + ", getAttributes()=" + getAttributes()
            + ", getArchived()=" + getArchived() + ", getCreated()=" + getCreated() + ", getModified()=" + getModified()
            + ", getTubeBarcode()=" + getTubeBarcode() + ", getStorageLocation()=" + getStorageLocation() + ", getVolume()=" + getVolume()
            + ", getConcentration()=" + getConcentration() + ", getParents()=" + getParents() + ", getChildren()=" + getChildren()
            + ", getCreatedById()=" + getCreatedById() + ", getModifiedById()=" + getModifiedById() + ", getClass()=" + getClass()
            + ", toString()=" + super.toString() + "]";
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
