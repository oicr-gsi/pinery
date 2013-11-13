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
      return "DefaultRunSample [barcode=" + barcode + ", url=" + url + ", id=" + id + ", getBarcode()=" + getBarcode() + ", getUrl()="
            + getUrl() + ", getId()=" + getId() + "]";
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
