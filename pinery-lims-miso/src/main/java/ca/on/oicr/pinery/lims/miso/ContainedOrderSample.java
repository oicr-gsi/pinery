package ca.on.oicr.pinery.lims.miso;

import ca.on.oicr.pinery.lims.DefaultOrderSample;

public class ContainedOrderSample extends DefaultOrderSample {
  
  private int parentId;

  public ContainedOrderSample() {
    
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

}
