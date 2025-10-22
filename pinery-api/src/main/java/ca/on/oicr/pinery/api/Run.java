package ca.on.oicr.pinery.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface Run {

  String getState();

  void setState(String state);

  String getName();

  void setName(String name);

  String getBarcode();

  void setBarcode(String barcode);

  String getBarcodeTwo();

  void setBarcodeTwo(String barcodeTwo);

  Date getCreatedDate();

  void setCreatedDate(Date createdDate);

  Integer getId();

  void setId(Integer id);

  Integer getCreatedById();

  void setCreatedById(Integer createdById);

  Integer getInstrumentId();

  void setInstrumentId(Integer instrumentId);

  String getInstrumentName();

  void setInstrumentName(String instrumentName);

  String getReadLength();

  void setReadLength(String readLength);

  Date getStartDate();

  void setStartDate(Date startDate);

  Date getCompletionDate();

  void setCompletionDate(Date endDate);

  Date getModified();

  void setModified(Date modified);

  Integer getModifiedById();

  void setModifiedById(Integer modifiedById);

  String getRunDirectory();

  void setRunDirectory(String runDirectory);

  String getRunBasesMask();

  void setRunBasesMask(String runBasesMask);

  String getSequencingParameters();

  void setSequencingParameters(String sequencingParameters);

  String getChemistry();

  void setChemistry(String chemistry);

  String getWorkflowType();

  void setWorkflowType(String workflowType);

  String getSequencingKit();

  void setSequencingKit(String sequencingKit);

  Status getStatus();

  void setStatus(Status status);

  Boolean getDataReview();

  void setDataReview(Boolean dataReview);

  LocalDate getDataReviewDate();

  void setDataReviewDate(LocalDate dataReviewDate);

  Integer getDataReviewerId();

  void setDataReviewerId(Integer dataReviewerId);

  Set<RunContainer> getContainers();

  void setContainers(Set<RunContainer> containers);

}
