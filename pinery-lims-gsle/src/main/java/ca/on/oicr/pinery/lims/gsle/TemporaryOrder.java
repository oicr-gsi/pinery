package ca.on.oicr.pinery.lims.gsle;

public class TemporaryOrder {

   private Integer orderId;
   private String sampleId;
   private String barcode;
   private String barcodeTwo;
   private String name;
   private String value;
   private String sampleUrl;

   public Integer getOrderId() {
      return orderId;
   }

   public void setOrderId(Integer orderId) {
      this.orderId = orderId;
   }

   public String getSampleId() {
      return sampleId;
   }

   public void setSampleId(String sampleId) {
      this.sampleId = sampleId;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public void setIdOrderString(String idOrderString) {
      if (idOrderString != null) {
         setOrderId(Integer.parseInt(idOrderString));
      }
   }

   public String getSampleUrl() {
      return sampleUrl;
   }

   public void setSampleUrl(String sampleUrl) {
      this.sampleUrl = sampleUrl;
   }

   public String getBarcodeTwo() {
      return barcodeTwo;
   }

   public void setBarcodeTwo(String barcodeTwo) {
      this.barcodeTwo = barcodeTwo;
   }

   @Override
   public String toString() {
      return "TemporaryOrder [orderId=" + orderId + ", sampleId=" + sampleId + ", barcode=" + barcode + ", barcodeTwo=" + barcodeTwo
            + ", name=" + name + ", value=" + value + ", sampleUrl=" + sampleUrl + "]";
   }

}
