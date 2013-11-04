package ca.on.oicr.ws.dto;

public class OrderPositionsDto {

   private Integer position;
   private String sampleUrl;
   private Integer sampleId;
   private String barcode;

   public Integer getPosition() {
      return position;
   }

   public void setPosition(Integer position) {
      this.position = position;
   }

   public String sampleUrl() {
      return sampleUrl;
   }

   public void setSampleUrl(String sampleUrl) {
      this.sampleUrl = sampleUrl;
   }

   public Integer getSampleId() {
      return sampleId;
   }

   public void setSampleId(Integer sampleId) {
      this.sampleId = sampleId;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

}
