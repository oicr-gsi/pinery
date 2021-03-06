package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Status;
import java.util.Date;
import java.util.Set;

public class DefaultRunPosition implements RunPosition {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((analysisSkipped == null) ? 0 : analysisSkipped.hashCode());
    result = prime * result + ((poolBarcode == null) ? 0 : poolBarcode.hashCode());
    result = prime * result + ((poolCreated == null) ? 0 : poolCreated.hashCode());
    result = prime * result + ((poolCreatedById == null) ? 0 : poolCreatedById.hashCode());
    result = prime * result + ((poolModified == null) ? 0 : poolModified.hashCode());
    result = prime * result + ((poolModifiedById == null) ? 0 : poolModifiedById.hashCode());
    result = prime * result + ((poolDescription == null) ? 0 : poolDescription.hashCode());
    result = prime * result + ((poolId == null) ? 0 : poolId.hashCode());
    result = prime * result + ((poolName == null) ? 0 : poolName.hashCode());
    result = prime * result + ((poolStatus == null) ? 0 : poolStatus.hashCode());
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    result = prime * result + ((qcStatus == null) ? 0 : qcStatus.hashCode());
    result = prime * result + ((runSample == null) ? 0 : runSample.hashCode());
    result = prime * result + ((runPurpose == null) ? 0 : runPurpose.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultRunPosition other = (DefaultRunPosition) obj;
    if (analysisSkipped == null) {
      if (other.analysisSkipped != null) return false;
    } else if (!analysisSkipped.equals(other.analysisSkipped)) return false;
    if (poolBarcode == null) {
      if (other.poolBarcode != null) return false;
    } else if (!poolBarcode.equals(other.poolBarcode)) return false;
    if (poolCreated == null) {
      if (other.poolCreated != null) return false;
    } else if (!poolCreated.equals(other.poolCreated)) return false;
    if (poolCreatedById == null) {
      if (other.poolCreatedById != null) return false;
    } else if (!poolCreatedById.equals(other.poolCreatedById)) return false;
    if (poolModified == null) {
      if (other.poolModified != null) return false;
    } else if (!poolModified.equals(other.poolModified)) return false;
    if (poolModifiedById == null) {
      if (other.poolModifiedById != null) return false;
    } else if (!poolModifiedById.equals(other.poolModifiedById)) return false;
    if (poolDescription == null) {
      if (other.poolDescription != null) return false;
    } else if (!poolDescription.equals(other.poolDescription)) return false;
    if (poolName == null) {
      if (other.poolName != null) return false;
    } else if (!poolName.equals(other.poolName)) return false;
    if (poolStatus == null) {
      if (other.poolStatus != null) return false;
    } else if (!poolStatus.equals(other.poolStatus)) return false;
    if (poolId == null) {
      if (other.poolId != null) return false;
    } else if (!poolId.equals(other.poolId)) return false;
    if (position == null) {
      if (other.position != null) return false;
    } else if (!position.equals(other.position)) return false;
    if (qcStatus == null) {
      if (other.qcStatus != null) return false;
    } else if (!qcStatus.equals(other.qcStatus)) return false;
    if (runSample == null) {
      if (other.runSample != null) return false;
    } else if (!runSample.equals(other.runSample)) return false;
    if (runPurpose == null) {
      if (other.runPurpose != null) return false;
    } else if (!runPurpose.equals(other.runPurpose)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "DefaultRunPosition [runSample="
        + runSample
        + ", position="
        + position
        + ", poolId="
        + poolId
        + ", poolName="
        + poolName
        + ", poolDescription="
        + poolDescription
        + ", poolBarcode="
        + poolBarcode
        + ", poolStatus="
        + poolStatus
        + ", poolCreatedById="
        + poolCreatedById
        + ", poolCreated="
        + poolCreated
        + ", poolModifiedById="
        + poolModifiedById
        + ", poolModified="
        + poolModified
        + ", qcStatus="
        + qcStatus
        + ", analysisSkipped="
        + analysisSkipped
        + ", runPurpose="
        + runPurpose
        + ", hashCode()="
        + hashCode()
        + ", toString()="
        + super.toString()
        + "]";
  }

  private Set<RunSample> runSample;
  private Integer position;
  private Integer poolId;
  private String poolName;
  private String poolDescription;
  private String poolBarcode;
  private Status poolStatus;
  private Integer poolCreatedById;
  private Date poolCreated;
  private Integer poolModifiedById;
  private Date poolModified;
  private String qcStatus;
  private Boolean analysisSkipped;
  private String runPurpose;

  @Override
  public Integer getPosition() {
    return position;
  }

  @Override
  public void setPosition(Integer position) {
    this.position = position;
  }

  @Override
  public Set<RunSample> getRunSample() {
    return runSample;
  }

  @Override
  public void setRunSample(Set<RunSample> runSample) {
    this.runSample = runSample;
  }

  @Override
  public Integer getPoolId() {
    return poolId;
  }

  @Override
  public void setPoolId(Integer poolId) {
    this.poolId = poolId;
  }

  @Override
  public String getPoolName() {
    return poolName;
  }

  @Override
  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  @Override
  public String getPoolBarcode() {
    return poolBarcode;
  }

  @Override
  public void setPoolBarcode(String poolBarcode) {
    this.poolBarcode = poolBarcode;
  }

  @Override
  public String getPoolDescription() {
    return poolDescription;
  }

  @Override
  public void setPoolDescription(String poolDescription) {
    this.poolDescription = poolDescription;
  }

  @Override
  public Status getPoolStatus() {
    return poolStatus;
  }

  @Override
  public void setPoolStatus(Status poolStatus) {
    this.poolStatus = poolStatus;
  }

  @Override
  public Integer getPoolCreatedById() {
    return poolCreatedById;
  }

  @Override
  public void setPoolCreatedById(Integer poolCreatedById) {
    this.poolCreatedById = poolCreatedById;
  }

  @Override
  public Date getPoolCreated() {
    return poolCreated;
  }

  @Override
  public void setPoolCreated(Date poolCreated) {
    this.poolCreated = poolCreated;
  }

  @Override
  public Integer getPoolModifiedById() {
    return poolModifiedById;
  }

  @Override
  public void setPoolModifiedById(Integer poolModifiedById) {
    this.poolModifiedById = poolModifiedById;
  }

  @Override
  public Date getPoolModified() {
    return poolModified;
  }

  @Override
  public void setPoolModified(Date poolModified) {
    this.poolModified = poolModified;
  }

  @Override
  public String getQcStatus() {
    return qcStatus;
  }

  @Override
  public void setQcStatus(String qcStatus) {
    this.qcStatus = qcStatus;
  }

  @Override
  public Boolean isAnalysisSkipped() {
    return analysisSkipped;
  }

  @Override
  public void setAnalysisSkipped(Boolean analysisSkipped) {
    this.analysisSkipped = analysisSkipped;
  }

  @Override
  public String getRunPurpose() {
    return runPurpose;
  }

  @Override
  public void setRunPurpose(String runPurpose) {
    this.runPurpose = runPurpose;
  }
}
