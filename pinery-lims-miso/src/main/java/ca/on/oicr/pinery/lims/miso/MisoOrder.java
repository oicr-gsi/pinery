package ca.on.oicr.pinery.lims.miso;

import java.util.HashSet;
import java.util.Set;

import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.lims.DefaultOrder;

public class MisoOrder extends DefaultOrder implements ParentObject<MisoOrderSample> {

  public MisoOrder() {
    
  }

  @Override
  public void addChild(MisoOrderSample child) {
    if (this.getSamples() == null) {
      Set<OrderSample> set = new HashSet<>();
      set.add(child);
      this.setSample(set);
    }
    else {
      this.getSamples().add(child);
    }
  }

}
