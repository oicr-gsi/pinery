package ca.on.oicr.pinery.lims;

public enum LimsSequencerRunAttribute {

  INSTRUMENT_NAME("instrument_name"),
  RUN_BASES_MASK("run_bases_mask"),
  RUN_DIRECTORY("run_dir"),
  SEQUENCING_PARAMETERS("sequencing_parameters"),
  WORKFLOW_TYPE("workflow_type");
  
  private final String key;
  
  private LimsSequencerRunAttribute(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }
}
