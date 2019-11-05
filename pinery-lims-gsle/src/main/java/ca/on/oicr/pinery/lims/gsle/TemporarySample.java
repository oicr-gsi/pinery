package ca.on.oicr.pinery.lims.gsle;

public class TemporarySample {

  @Override
  public String toString() {
    return "TemporarySample [barcode="
        + barcode
        + ", id="
        + id
        + ", getBarcode()="
        + getBarcode()
        + ", getId()="
        + getId()
        + ", getClass()="
        + getClass()
        + ", hashCode()="
        + hashCode()
        + ", toString()="
        + super.toString()
        + "]";
  }

  private String barcode;
  private Integer id;

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
