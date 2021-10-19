package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface RunSample extends Sample {

  public String getBarcode();

  public void setBarcode(String barcode);

  public String getBarcodeTwo();

  public void setBarcodeTwo(String barcodeTwo);

  public String getRunPurpose();

  public void setRunPurpose(String runPurpose);

  public Boolean getDataReview();

  public void setDataReview(Boolean dataReview);

  public LocalDate getDataReviewDate();

  public void setDataReviewDate(LocalDate dataReviewDate);

  public Integer getDataReviewerId();

  public void setDataReviewerId(Integer dataReviewerId);
}
