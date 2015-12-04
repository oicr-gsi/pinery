package ca.on.oicr.pinery.lims.flatfile.model;

import ca.on.oicr.pinery.lims.DefaultChange;

/**
 * An extension of DefaultChange for use in reading LIMS data from flat files, used to simplify compilation of Changes into ChangeLogs
 */
public class FileChange extends DefaultChange {
  
  private final Integer sampleId;
  
  public FileChange(Integer sampleId) {
    this.sampleId = sampleId;
  }

  public Integer getSampleId() {
    return sampleId;
  }
  
}
