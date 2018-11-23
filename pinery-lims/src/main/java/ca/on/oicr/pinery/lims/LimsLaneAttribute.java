package ca.on.oicr.pinery.lims;

public enum LimsLaneAttribute {

  POOL_NAME("pool_name"),
  QC_STATUS("qc_status");
  
  private final String key;
  
  private LimsLaneAttribute(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }
  
}
