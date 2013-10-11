package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.OrderSample;

public class DefaultOrderSample extends DefaultSample implements OrderSample {

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
