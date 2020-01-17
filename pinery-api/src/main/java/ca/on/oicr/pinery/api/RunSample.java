package ca.on.oicr.pinery.api;

public interface RunSample extends Sample {

  public String getBarcode();

  public void setBarcode(String barcode);

  public String getBarcodeTwo();

  public void setBarcodeTwo(String barcodeTwo);

  public String getRunPurpose();

  public void setRunPurpose(String runPurpose);
}
