package ca.on.oicr.pinery.api;

import java.util.Set;

public interface RunPosition {

   public Integer getPosition();

   public void setPosition(Integer position);
   
   public String getPoolName();
   
   public void setPoolName(String poolName);
   
   public String getPoolBarcode();
   
   public void setPoolBarcode(String poolBarcode);
   
   public String getPoolDescription();
   
   public void setPoolDescription(String poolDescription);

   public Set<RunSample> getRunSample();

   public void setRunSample(Set<RunSample> setRunSample);
}
