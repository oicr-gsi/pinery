package ca.on.oicr.pinery.lims.miso;

import ca.on.oicr.pinery.lims.DefaultRunSample;

public class MisoRunSample extends DefaultRunSample implements ChildObject {

  private int runId;
  
  public MisoRunSample() {
    
  }

  public int getRunId() {
    return runId;
  }

  public void setRunId(int runId) {
    this.runId = runId;
  }

  @Override
  public Integer getParentId() {
    return getRunId();
  }

}
