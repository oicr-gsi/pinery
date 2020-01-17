package ca.on.oicr.pinery.lims;

public enum LimsLaneAttribute {
  POOL_NAME("pool_name"),
  QC_STATUS("qc_status"),
  RUN_PURPOSE("run_purpose");

  private final String key;

  private LimsLaneAttribute(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
