package ca.on.oicr.pinery.api;

import java.util.Set;

public interface RunPosition {

   public Integer getPosition();

   public void setPosition(Integer position);

   public Set<RunSample> getRunSample();

   public void setRunSample(Set<RunSample> setRunSample);
}
