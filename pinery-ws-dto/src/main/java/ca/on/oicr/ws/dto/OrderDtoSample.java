package ca.on.oicr.ws.dto;

//Inheriting fields from SampleDto and adding new fields 
public class OrderDtoSample extends SampleDto {

   private String barcode;

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }
}
