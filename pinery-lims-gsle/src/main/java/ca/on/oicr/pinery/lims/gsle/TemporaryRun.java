package ca.on.oicr.pinery.lims.gsle;

public class TemporaryRun {

   @Override
   public String toString() {
      return "TemporaryRun [runId=" + runId + ", position=" + position + ", sampleId=" + sampleId + ", sampleUrl=" + sampleUrl
            + ", barcode=" + barcode + ", getRunId()=" + getRunId() + ", getPosition()=" + getPosition() + ", getSampleId()="
            + getSampleId() + ", getSampleUrl()=" + getSampleUrl() + ", getBarcode()=" + getBarcode() + ", getClass()=" + getClass()
            + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
   }

   private Integer runId;
   private Integer position;
   private Integer sampleId;
   private String sampleUrl;
   private String barcode;

   public Integer getRunId() {
      return runId;
   }

   public void setRunId(Integer runId) {
      this.runId = runId;
   }

   public Integer getPosition() {
      return position;
   }

   public void setPosition(Integer position) {
      this.position = position;
   }

   public Integer getSampleId() {
      return sampleId;
   }

   public void setSampleId(Integer sampleId) {
      this.sampleId = sampleId;
   }

   public String getSampleUrl() {
      return sampleUrl;
   }

   public void setSampleUrl(String sampleUrl) {
      this.sampleUrl = sampleUrl;
   }

   public String getBarcode() {
      return barcode;
   }

   public void setBarcode(String barcode) {
      this.barcode = barcode;
   }

   public void setIdRunString(String idRunString) {
      if (idRunString != null) {
         setRunId(Integer.parseInt(idRunString));
      }
   }

   public void setIdSampleString(String idSampleString) {
      if (idSampleString != null) {
         setSampleId(Integer.parseInt(idSampleString));
      }
   }

   public void setPositionString(String positionString) {
      if (positionString != null) {
         setPosition(Integer.parseInt(positionString));
      }
   }
}
