package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.Set;

public interface RunPosition {

  public Integer getPosition();

  public void setPosition(Integer position);

  public Integer getPoolId();

  public void setPoolId(Integer poolId);

  public String getPoolName();

  public void setPoolName(String poolName);

  public String getPoolBarcode();

  public void setPoolBarcode(String poolBarcode);

  public String getPoolDescription();

  public void setPoolDescription(String poolDescription);

  public Status getPoolStatus();

  public void setPoolStatus(Status poolStatus);

  public Integer getPoolCreatedById();

  public void setPoolCreatedById(Integer poolCreatedById);

  public Date getPoolCreated();

  public void setPoolCreated(Date poolCreated);

  public Integer getPoolModifiedById();

  public void setPoolModifiedById(Integer poolModifiedById);

  public Date getPoolModified();

  public void setPoolModified(Date poolModified);

  public Set<RunSample> getRunSample();

  public void setRunSample(Set<RunSample> setRunSample);

  public String getQcStatus();

  public void setQcStatus(String qcStatus);

  public Boolean isAnalysisSkipped();

  public void setAnalysisSkipped(Boolean analysisSkipped);

  public String getRunPurpose();

  public void setRunPurpose(String runPurpose);
}
