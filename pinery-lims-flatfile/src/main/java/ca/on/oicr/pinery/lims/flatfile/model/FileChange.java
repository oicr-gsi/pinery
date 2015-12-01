package ca.on.oicr.pinery.lims.flatfile.model;

import ca.on.oicr.pinery.lims.DefaultChange;

public class FileChange extends DefaultChange {
  
  private final Integer sampleId;
  
  public FileChange(Integer sampleId) {
    this.sampleId = sampleId;
  }

  public Integer getSampleId() {
    return sampleId;
  }
  
}
