package ca.on.oicr.pinery.lims.miso;

import ca.on.oicr.pinery.lims.DefaultOrderSample;

public class MisoOrderSample extends DefaultOrderSample implements ChildObject {
  
  private int orderId;

  public MisoOrderSample() {
    
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Override
  public Integer getParentId() {
    return getOrderId();
  }

}
