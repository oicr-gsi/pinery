package ca.on.oicr.pinery.lims.miso;

import ca.on.oicr.pinery.lims.DefaultRunPosition;

public class MisoRunPosition extends DefaultRunPosition implements ChildObject {
  
  private int runId;
  
  public MisoRunPosition() {
    
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
