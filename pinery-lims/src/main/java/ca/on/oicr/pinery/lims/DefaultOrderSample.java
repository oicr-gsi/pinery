package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.OrderSample;

public class DefaultOrderSample extends DefaultSample implements OrderSample {

   protected String barcode;
   protected String barcodeTwo;

   @Override
   public String getBarcode() {
      return barcode;
   }

   @Override
   public void setBarcode(String barcode) {
      this.barcode = barcode;
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
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((archived == null) ? 0 : archived.hashCode());
      result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
      result = prime * result + ((children == null) ? 0 : children.hashCode());
      result = prime * result + ((concentration == null) ? 0 : concentration.hashCode());
      result = prime * result + ((created == null) ? 0 : created.hashCode());
      result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((modified == null) ? 0 : modified.hashCode());
      result = prime * result + ((modifiedById == null) ? 0 : modifiedById.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((parents == null) ? 0 : parents.hashCode());
      result = prime * result + ((preparationKit == null) ? 0 : preparationKit.hashCode());
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      result = prime * result + ((sampleType == null) ? 0 : sampleType.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((storageLocation == null) ? 0 : storageLocation.hashCode());
      result = prime * result + ((tissueType == null) ? 0 : tissueType.hashCode());
      result = prime * result + ((tubeBarcode == null) ? 0 : tubeBarcode.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      result = prime * result + ((volume == null) ? 0 : volume.hashCode());
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultOrderSample other = (DefaultOrderSample) obj;
      if (archived == null) {
         if (other.archived != null) return false;
      } else if (!archived.equals(other.archived)) return false;
      if (attributes == null) {
         if (other.attributes != null) return false;
      } else if (!attributes.equals(other.attributes)) return false;
      if (children == null) {
         if (other.children != null) return false;
      } else if (!children.equals(other.children)) return false;
      if (concentration == null) {
         if (other.concentration != null) return false;
      } else if (!concentration.equals(other.concentration)) return false;
      if (created == null) {
         if (other.created != null) return false;
      } else if (!created.equals(other.created)) return false;
      if (createdById == null) {
         if (other.createdById != null) return false;
      } else if (!createdById.equals(other.createdById)) return false;
      if (description == null) {
         if (other.description != null) return false;
      } else if (!description.equals(other.description)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (modified == null) {
         if (other.modified != null) return false;
      } else if (!modified.equals(other.modified)) return false;
      if (modifiedById == null) {
         if (other.modifiedById != null) return false;
      } else if (!modifiedById.equals(other.modifiedById)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (parents == null) {
         if (other.parents != null) return false;
      } else if (!parents.equals(other.parents)) return false;
      if (preparationKit == null) {
         if (other.preparationKit != null) return false;
      } else if (!preparationKit.equals(other.preparationKit)) return false;
      if (project == null) {
         if (other.project != null) return false;
      } else if (!project.equals(other.project)) return false;
      if (sampleType == null) {
         if (other.sampleType != null) return false;
      } else if (!sampleType.equals(other.sampleType)) return false;
      if (status == null) {
         if (other.status != null) return false;
      } else if (!status.equals(other.status)) return false;
      if (storageLocation == null) {
         if (other.storageLocation != null) return false;
      } else if (!storageLocation.equals(other.storageLocation)) return false;
      if (tissueType == null) {
         if (other.tissueType != null) return false;
      } else if (!tissueType.equals(other.tissueType)) return false;
      if (tubeBarcode == null) {
         if (other.tubeBarcode != null) return false;
      } else if (!tubeBarcode.equals(other.tubeBarcode)) return false;
      if (url == null) {
         if (other.url != null) return false;
      } else if (!url.equals(other.url)) return false;
      if (volume == null) {
         if (other.volume != null) return false;
      } else if (!volume.equals(other.volume)) return false;
      if (barcode == null) {
         if (other.barcode != null) return false;
      } else if (!barcode.equals(other.barcode)) return false;
      return true;
   }

}
