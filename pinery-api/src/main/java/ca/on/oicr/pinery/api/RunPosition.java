package ca.on.oicr.pinery.api;

import java.util.Date;
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
   
   public Integer getPoolCreatedById();
   
   public void setPoolCreatedById(Integer poolCreatedById);
   
   public Date getPoolCreated();
   
   public void setPoolCreated(Date poolCreated);

   public Set<RunSample> getRunSample();

   public void setRunSample(Set<RunSample> setRunSample);

   public String getQcStatus();
   
   public void setQcStatus(String qcStatus);
   
   public Boolean isAnalysisSkipped();
   
   public void setAnalysisSkipped(Boolean analysisSkipped);
}
