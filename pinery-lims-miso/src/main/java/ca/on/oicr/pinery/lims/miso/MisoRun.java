package ca.on.oicr.pinery.lims.miso;

import java.util.HashSet;
import java.util.Set;

import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.lims.DefaultRun;

public class MisoRun extends DefaultRun implements ParentObject<MisoRunPosition> {

  public MisoRun() {
    
  }

  @Override
  public void addChild(MisoRunPosition child) {
    if (this.getSamples() == null) {
      Set<RunPosition> set = new HashSet<>();
      set.add(child);
      this.setSample(set);
    }
    else {
      this.getSamples().add(child);
    }
  }

}
