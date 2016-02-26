package ca.on.oicr.pinery.lims.miso;

import ca.on.oicr.pinery.lims.DefaultChange;

public class MisoChange extends DefaultChange {

  private int sampleId;

  public int getSampleId() {
    return sampleId;
  }

  public void setSampleId(int sampleId) {
    this.sampleId = sampleId;
  }

}
