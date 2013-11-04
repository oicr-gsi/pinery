package ca.on.oicr.pinery.lims.gsle;

public class Temporary {

   @Override
   public String toString() {
      return "Temporary [id=" + orderId + ", name=" + name + ", value=" + value + "]";
   }

   private Integer orderId;
   private Integer sampleId;
   private String barcode;
   private String name;
   private String value;
   private String sampleUrl;

   public Integer getOrderId() {
      return orderId;
   }

   public void setOrderId(Integer orderId) {
      this.orderId = orderId;
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

   public void setIdSampleString(String idSampleString) {
      if (idSampleString != null) {
         setSampleId(Integer.parseInt(idSampleString));
      }
   }

   public String getSampleUrl() {
      return sampleUrl;
   }

   public void setSampleUrl(String sampleUrl) {
      this.sampleUrl = sampleUrl;
   }

}
