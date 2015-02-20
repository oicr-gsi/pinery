package ca.on.oicr.ws.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class OrderDtoSample extends SampleDto {

   private String barcode;
   @JsonProperty("barcode_two")
   private String barcodeTwo;
   private String url;

   @Override
   public String getUrl() {
      return url;
   }

   @Override
   public void setUrl(String url) {
      this.url = url;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public String getBarcodeTwo() {
      return barcodeTwo;
   }

   public void setBarcodeTwo(String barcodeTwo) {
      this.barcodeTwo = barcodeTwo;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
      result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
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
      if (barcodeTwo == null) {
         if (other.barcodeTwo != null) return false;
      } else if (!barcodeTwo.equals(other.barcodeTwo)) return false;
      return true;
   }

   @Override
   public String toString() {
      return "OrderDtoSample [barcode=" + barcode + ", barcodeTwo=" + barcodeTwo + ", url=" + url + "]";
   }

}
