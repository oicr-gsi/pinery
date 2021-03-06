package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunDtoPosition {

  private Set<RunDtoSample> runSample;
  private Integer position;
  private Integer poolId;
  private String poolName;
  private String poolDescription;
  private String poolBarcode;
  private StatusDto poolStatus;
  private Integer poolCreatedById;
  private String poolCreated;
  private Integer poolModifiedById;
  private String poolModified;
  private String qcStatus;
  private Boolean analysisSkipped;
  private String runPurpose;

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Integer getPoolId() {
    return poolId;
  }

  public void setPoolId(Integer poolId) {
    this.poolId = poolId;
  }

  public Set<RunDtoSample> getSamples() {
    return runSample;
  }

  public void setSamples(Set<RunDtoSample> runSample) {
    this.runSample = runSample;
  }

  @JsonProperty("pool_name")
  public String getPoolName() {
    return poolName;
  }

  public void setPoolName(String poolName) {
    this.poolName = poolName;
  }

  @JsonProperty("pool_barcode")
  public String getPoolBarcode() {
    return poolBarcode;
  }

  public void setPoolBarcode(String poolBarcode) {
    this.poolBarcode = poolBarcode;
  }

  @JsonProperty("pool_description")
  public String getPoolDescription() {
    return poolDescription;
  }

  public void setPoolDescription(String poolDescription) {
    this.poolDescription = poolDescription;
  }

  @JsonProperty("pool_status")
  public StatusDto getPoolStatus() {
    return poolStatus;
  }

  public void setPoolStatus(StatusDto poolStatus) {
    this.poolStatus = poolStatus;
  }

  @JsonProperty("pool_created_by_id")
  public Integer getPoolCreatedById() {
    return poolCreatedById;
  }

  public void setPoolCreatedById(Integer poolCreatedById) {
    this.poolCreatedById = poolCreatedById;
  }

  @JsonProperty("pool_created_date")
  public String getPoolCreated() {
    return poolCreated;
  }

  public void setPoolCreated(String poolCreated) {
    this.poolCreated = poolCreated;
  }

  @JsonProperty("pool_modified_by_id")
  public Integer getPoolModifiedById() {
    return poolModifiedById;
  }

  public void setPoolModifiedById(Integer poolModifiedById) {
    this.poolModifiedById = poolModifiedById;
  }

  @JsonProperty("pool_modified_date")
  public String getPoolModified() {
    return poolModified;
  }

  public void setPoolModified(String poolModified) {
    this.poolModified = poolModified;
  }

  @JsonProperty("qc_status")
  public String getQcStatus() {
    return qcStatus;
  }

  public void setQcStatus(String qcStatus) {
    this.qcStatus = qcStatus;
  }

  @JsonProperty("analysis_skipped")
  public Boolean isAnalysisSkipped() {
    return analysisSkipped;
  }

  public void setAnalysisSkipped(Boolean analysisSkipped) {
    this.analysisSkipped = analysisSkipped;
  }

  public String getRunPurpose() {
    return runPurpose;
  }

  public void setRunPurpose(String runPurpose) {
    this.runPurpose = runPurpose;
  }

  @Override
  public String toString() {
    return "RunDtoPosition [runSample="
        + runSample
        + ", position="
        + position
        + ", hashCode()="
        + hashCode()
        + ", getPosition()="
        + getPosition()
        + ", getRunSamples()="
        + getSamples()
        + ", getPoolName()="
        + getPoolName()
        + ", getPoolBarcode()="
        + getPoolBarcode()
        + ", getPoolDescription()="
        + getPoolDescription()
        + ", getPoolStatus()="
        + getPoolStatus()
        + ", getPoolCreatedById()="
        + getPoolCreatedById()
        + ", getPoolCreated()="
        + getPoolCreated()
        + ", getQcStatus()="
        + getQcStatus()
        + ", isAnalysisSkipped()="
        + isAnalysisSkipped()
        + ", getRunPurpose()="
        + getRunPurpose()
        + ", getClass()="
        + getClass()
        + ", toString()="
        + super.toString()
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    result = prime * result + ((runSample == null) ? 0 : runSample.hashCode());
    result = prime * result + ((poolName == null) ? 0 : poolName.hashCode());
    result = prime * result + ((poolBarcode == null) ? 0 : poolBarcode.hashCode());
    result = prime * result + ((poolDescription == null) ? 0 : poolDescription.hashCode());
    result = prime * result + ((poolStatus == null) ? 0 : poolStatus.hashCode());
    result = prime * result + ((poolCreatedById == null) ? 0 : poolCreatedById.hashCode());
    result = prime * result + ((poolCreated == null) ? 0 : poolCreated.hashCode());
    result = prime * result + ((qcStatus == null) ? 0 : qcStatus.hashCode());
    result = prime * result + ((analysisSkipped == null) ? 0 : analysisSkipped.hashCode());
    result = prime * result + ((runPurpose == null) ? 0 : runPurpose.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RunDtoPosition other = (RunDtoPosition) obj;
    if (position == null) {
      if (other.position != null) return false;
    } else if (!position.equals(other.position)) return false;
    if (runSample == null) {
      if (other.runSample != null) return false;
    } else if (!runSample.equals(other.runSample)) return false;
    if (poolName == null) {
      if (other.poolName != null) return false;
    } else if (!poolName.equals(other.poolName)) return false;
    if (poolBarcode == null) {
      if (other.poolBarcode != null) return false;
    } else if (!poolBarcode.equals(other.poolBarcode)) return false;
    if (poolDescription == null) {
      if (other.poolDescription != null) return false;
    } else if (!poolDescription.equals(other.poolDescription)) return false;
    if (poolStatus == null) {
      if (other.poolStatus != null) return false;
    } else if (!poolStatus.equals(other.poolStatus)) return false;
    if (poolCreatedById == null) {
      if (other.poolCreatedById != null) return false;
    } else if (!poolCreatedById.equals(other.poolCreatedById)) return false;
    if (poolCreated == null) {
      if (other.poolCreated != null) return false;
    } else if (!poolCreated.equals(other.poolCreated)) return false;
    if (qcStatus == null) {
      if (other.qcStatus != null) return false;
    } else if (!qcStatus.equals(other.qcStatus)) return false;
    if (analysisSkipped == null) {
      if (other.analysisSkipped != null) return false;
    } else if (!analysisSkipped.equals(other.analysisSkipped)) return false;
    if (runPurpose == null) {
      if (other.runPurpose != null) return false;
    } else if (!runPurpose.equals(other.runPurpose)) return false;
    return true;
  }
}
