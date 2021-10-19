package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.RunSample;
import java.time.LocalDate;

public class DefaultRunSample extends DefaultSample implements RunSample {

  private String barcode;
  private String barcodeTwo;
  private String runPurpose;
  private Boolean dataReview;
  private LocalDate dataReviewDate;
  private Integer dataReviewerId;

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
  public String getRunPurpose() {
    return runPurpose;
  }

  @Override
  public void setRunPurpose(String runPurpose) {
    this.runPurpose = runPurpose;
  }

  @Override
  public Boolean getDataReview() {
    return dataReview;
  }

  @Override
  public void setDataReview(Boolean dataReview) {
    this.dataReview = dataReview;
  }

  @Override
  public LocalDate getDataReviewDate() {
    return dataReviewDate;
  }

  @Override
  public void setDataReviewDate(LocalDate dataReviewDate) {
    this.dataReviewDate = dataReviewDate;
  }

  @Override
  public Integer getDataReviewerId() {
    return dataReviewerId;
  }

  @Override
  public void setDataReviewerId(Integer dataReviewerId) {
    this.dataReviewerId = dataReviewerId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
    result = prime * result + ((runPurpose == null) ? 0 : runPurpose.hashCode());
    result = prime * result + ((dataReview == null) ? 0 : dataReview.hashCode());
    result = prime * result + ((dataReviewDate == null) ? 0 : dataReviewDate.hashCode());
    result = prime * result + ((dataReviewerId == null) ? 0 : dataReviewerId.hashCode());
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
    if (barcodeTwo == null) {
      if (other.barcodeTwo != null) return false;
    } else if (!barcodeTwo.equals(other.barcodeTwo)) return false;
    if (runPurpose == null) {
      if (other.runPurpose != null) return false;
    } else if (!runPurpose.equals(other.runPurpose)) return false;
    if (dataReview == null) {
      if (other.dataReview != null) return false;
    } else if (!dataReview.equals(other.dataReview)) return false;
    if (dataReviewDate == null) {
      if (other.dataReviewDate != null) return false;
    } else if (!dataReviewDate.equals(other.dataReviewDate)) return false;
    if (dataReviewerId == null) {
      if (other.dataReviewerId != null) return false;
    } else if (!dataReviewerId.equals(other.dataReviewerId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "DefaultRunSample [barcode="
        + barcode
        + ", barcodeTwo="
        + barcodeTwo
        + ", runPurpose="
        + runPurpose
        + "]";
  }
}
