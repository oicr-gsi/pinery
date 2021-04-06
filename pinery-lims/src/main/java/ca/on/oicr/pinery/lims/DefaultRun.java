package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.Status;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class DefaultRun implements Run {

  private String state;
  private String name;
  private String barcode;
  private String barcodeTwo;
  private Set<RunPosition> sample;
  private String createdByUrl;
  private Date createdDate;
  private Integer id;
  private Integer createdById;
  private Integer instrumentId;
  private String instrumentName;
  private String readLength;
  private Date startDate;
  private Date completionDate;
  private Date modified;
  private Integer modifiedById;
  private String runDirectory;
  private String runBasesMask;
  private String sequencingParameters;
  private String chemistry;
  private String workflowType;
  private String containerModel;
  private String sequencingKit;
  private Status status;
  private Boolean dataReview;
  private LocalDate dataReviewDate;

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getBarcode() {
    return barcode;
  }

  @Override
  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  @Override
  public Set<RunPosition> getSamples() {
    return sample;
  }

  @Override
  public void setSample(Set<RunPosition> sample) {
    this.sample = sample;
  }

  @Override
  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Integer getCreatedById() {
    return createdById;
  }

  @Override
  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  @Override
  public Integer getInstrumentId() {
    return instrumentId;
  }

  @Override
  public void setInstrumentId(Integer instrumentId) {
    this.instrumentId = instrumentId;
  }

  @Override
  public String getBarcodeTwo() {
    return barcodeTwo;
  }

  @Override
  public void setBarcodeTwo(String barcodeTwo) {
    this.barcodeTwo = barcodeTwo;
  }

  @Override
  public String getInstrumentName() {
    return instrumentName;
  }

  @Override
  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  @Override
  public Date getStartDate() {
    return startDate;
  }

  @Override
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  @Override
  public Date getCompletionDate() {
    return completionDate;
  }

  @Override
  public void setCompletionDate(Date completionDate) {
    this.completionDate = completionDate;
  }

  @Override
  public Date getModified() {
    return modified != null ? new Date(modified.getTime()) : null;
  }

  @Override
  public void setModified(Date modified) {
    this.modified = modified != null ? new Date(modified.getTime()) : null;
  }

  @Override
  public Integer getModifiedById() {
    return modifiedById;
  }

  @Override
  public void setModifiedById(Integer modifiedById) {
    this.modifiedById = modifiedById;
  }

  @Override
  public String getReadLength() {
    return readLength;
  }

  @Override
  public void setReadLength(String readLength) {
    this.readLength = readLength;
  }

  @Override
  public String getRunDirectory() {
    return runDirectory;
  }

  @Override
  public void setRunDirectory(String runDirectory) {
    this.runDirectory = runDirectory;
  }

  @Override
  public String getRunBasesMask() {
    return runBasesMask;
  }

  @Override
  public void setRunBasesMask(String runBasesMask) {
    this.runBasesMask = runBasesMask;
  }

  @Override
  public String getSequencingParameters() {
    return sequencingParameters;
  }

  @Override
  public void setSequencingParameters(String sequencingParameters) {
    this.sequencingParameters = sequencingParameters;
  }

  @Override
  public String getChemistry() {
    return chemistry;
  }

  @Override
  public void setChemistry(String chemistry) {
    this.chemistry = chemistry;
  }

  @Override
  public String getWorkflowType() {
    return workflowType;
  }

  @Override
  public void setWorkflowType(String workflowType) {
    this.workflowType = workflowType;
  }

  @Override
  public String getContainerModel() {
    return containerModel;
  }

  @Override
  public void setContainerModel(String containerModel) {
    this.containerModel = containerModel;
  }

  @Override
  public String getSequencingKit() {
    return sequencingKit;
  }

  @Override
  public void setSequencingKit(String sequencingKit) {
    this.sequencingKit = sequencingKit;
  }

  @Override
  public Status getStatus() {
    return status;
  }

  @Override
  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public Boolean getDataReview() {
    return dataReview;
  }

  @Override
  public void setDataReview(Boolean dataReview) {
    this.dataReview = dataReview;
  }

  @Override
  public LocalDate getDataReviewDate() {
    return dataReviewDate;
  }

  @Override
  public void setDataReviewDate(LocalDate dataReviewDate) {
    this.dataReviewDate = dataReviewDate;
  }

  @Override
  public String toString() {
    return "DefaultRun [state="
        + state
        + ", name="
        + name
        + ", barcode="
        + barcode
        + ", barcodeTwo="
        + barcodeTwo
        + ", sample="
        + sample
        + ", createdByUrl="
        + createdByUrl
        + ", createdDate="
        + createdDate
        + ", id="
        + id
        + ", createdById="
        + createdById
        + ", instrumentId="
        + instrumentId
        + ", instrumentName="
        + instrumentName
        + ", readLength="
        + readLength
        + ", startDate="
        + startDate
        + ", completionDate="
        + completionDate
        + ", modified="
        + modified
        + ", modifiedById="
        + modifiedById
        + ", runDirectory="
        + runDirectory
        + ", runBasesMask="
        + runBasesMask
        + ", sequencingParameters="
        + sequencingParameters
        + ", workflowType="
        + workflowType
        + ", containerModel="
        + containerModel
        + ", sequencingKit="
        + sequencingKit
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
    result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
    result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
    result = prime * result + ((createdByUrl == null) ? 0 : createdByUrl.hashCode());
    result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
    result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
    result = prime * result + ((modified == null) ? 0 : modified.hashCode());
    result = prime * result + ((modifiedById == null) ? 0 : modifiedById.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((readLength == null) ? 0 : readLength.hashCode());
    result = prime * result + ((sample == null) ? 0 : sample.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((runDirectory == null) ? 0 : runDirectory.hashCode());
    result = prime * result + ((runBasesMask == null) ? 0 : runBasesMask.hashCode());
    result =
        prime * result + ((sequencingParameters == null) ? 0 : sequencingParameters.hashCode());
    result = prime * result + ((workflowType == null) ? 0 : workflowType.hashCode());
    result = prime * result + ((containerModel == null) ? 0 : containerModel.hashCode());
    result = prime * result + ((sequencingKit == null) ? 0 : sequencingKit.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((dataReview == null) ? 0 : dataReview.hashCode());
    result = prime * result + ((dataReviewDate == null) ? 0 : dataReviewDate.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultRun other = (DefaultRun) obj;
    if (barcode == null) {
      if (other.barcode != null) return false;
    } else if (!barcode.equals(other.barcode)) return false;
    if (barcodeTwo == null) {
      if (other.barcodeTwo != null) return false;
    } else if (!barcodeTwo.equals(other.barcodeTwo)) return false;
    if (completionDate == null) {
      if (other.completionDate != null) return false;
    } else if (!completionDate.equals(other.completionDate)) return false;
    if (createdById == null) {
      if (other.createdById != null) return false;
    } else if (!createdById.equals(other.createdById)) return false;
    if (createdByUrl == null) {
      if (other.createdByUrl != null) return false;
    } else if (!createdByUrl.equals(other.createdByUrl)) return false;
    if (createdDate == null) {
      if (other.createdDate != null) return false;
    } else if (!createdDate.equals(other.createdDate)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (instrumentId == null) {
      if (other.instrumentId != null) return false;
    } else if (!instrumentId.equals(other.instrumentId)) return false;
    if (instrumentName == null) {
      if (other.instrumentName != null) return false;
    } else if (!instrumentName.equals(other.instrumentName)) return false;
    if (modified == null) {
      if (other.modified != null) return false;
    } else if (!modified.equals(other.modified)) return false;
    if (modifiedById == null) {
      if (other.modifiedById != null) return false;
    } else if (!modifiedById.equals(other.modifiedById)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (readLength == null) {
      if (other.readLength != null) return false;
    } else if (!readLength.equals(other.readLength)) return false;
    if (sample == null) {
      if (other.sample != null) return false;
    } else if (!sample.equals(other.sample)) return false;
    if (startDate == null) {
      if (other.startDate != null) return false;
    } else if (!startDate.equals(other.startDate)) return false;
    if (state == null) {
      if (other.state != null) return false;
    } else if (!state.equals(other.state)) return false;
    if (runDirectory == null) {
      if (other.runDirectory != null) return false;
    } else if (!runDirectory.equals(other.runDirectory)) return false;
    if (runBasesMask == null) {
      if (other.runBasesMask != null) return false;
    } else if (!runBasesMask.equals(other.runBasesMask)) return false;
    if (sequencingParameters == null) {
      if (other.sequencingParameters != null) return false;
    } else if (!sequencingParameters.equals(other.sequencingParameters)) return false;
    if (workflowType == null) {
      if (other.workflowType != null) return false;
    } else if (!workflowType.equals(other.workflowType)) return false;
    if (containerModel == null) {
      if (other.containerModel != null) return false;
    } else if (!containerModel.equals(other.containerModel)) return false;
    if (sequencingKit == null) {
      if (other.sequencingKit != null) return false;
    } else if (!sequencingKit.equals(other.sequencingKit)) return false;
    if (status == null) {
      if (other.status != null) return false;
    } else if (!status.equals(other.status)) return false;
    if (dataReview == null) {
      if (other.dataReview != null) return false;
    } else if (!dataReview.equals(other.dataReview)) return false;
    if (dataReviewDate == null) {
      if (other.dataReviewDate != null) return false;
    } else if (!dataReviewDate.equals(other.dataReviewDate)) return false;
    return true;
  }
}
