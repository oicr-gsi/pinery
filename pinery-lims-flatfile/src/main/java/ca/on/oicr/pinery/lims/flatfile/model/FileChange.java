package ca.on.oicr.pinery.lims.flatfile.model;

import ca.on.oicr.pinery.lims.DefaultChange;

/**
 * An extension of DefaultChange for use in reading LIMS data from flat files, used to simplify compilation of Changes into ChangeLogs
 */
public class FileChange extends DefaultChange {
  
  private final String sampleId;
  
  public FileChange(String sampleId) {
    this.sampleId = sampleId;
  }

  public String getSampleId() {
    return sampleId;
  }
  
}
