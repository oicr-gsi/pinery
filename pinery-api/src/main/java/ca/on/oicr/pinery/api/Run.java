package ca.on.oicr.pinery.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface Run {

  public String getState();

  public void setState(String state);

  public String getName();

  public void setName(String name);

  public String getBarcode();

  public void setBarcode(String barcode);

  public String getBarcodeTwo();

  public void setBarcodeTwo(String barcodeTwo);

  public Set<RunPosition> getSamples();

  public void setSample(Set<RunPosition> samples);

  public Date getCreatedDate();

  public void setCreatedDate(Date createdDate);

  public Integer getId();

  public void setId(Integer id);

  public Integer getCreatedById();

  public void setCreatedById(Integer createdById);

  public Integer getInstrumentId();

  public void setInstrumentId(Integer instrumentId);

  public String getInstrumentName();

  public void setInstrumentName(String instrumentName);

  public String getReadLength();

  public void setReadLength(String readLength);

  public Date getStartDate();

  public void setStartDate(Date startDate);

  public Date getCompletionDate();

  public void setCompletionDate(Date endDate);

  public Date getModified();

  public void setModified(Date modified);

  public Integer getModifiedById();

  public void setModifiedById(Integer modifiedById);

  public String getRunDirectory();

  public void setRunDirectory(String runDirectory);

  public String getRunBasesMask();

  public void setRunBasesMask(String runBasesMask);

  public String getSequencingParameters();

  public void setSequencingParameters(String sequencingParameters);

  public String getChemistry();

  public void setChemistry(String chemistry);

  public String getWorkflowType();

  public void setWorkflowType(String workflowType);

  public String getContainerModel();

  public void setContainerModel(String containerModel);

  public String getSequencingKit();

  public void setSequencingKit(String sequencingKit);

  public Status getStatus();

  public void setStatus(Status status);

  public Boolean getDataReview();

  public void setDataReview(Boolean dataReview);

  public LocalDate getDataReviewDate();

  public void setDataReviewDate(LocalDate dataReviewDate);

  public Integer getDataReviewerId();

  public void setDataReviewerId(Integer dataReviewerId);
}
