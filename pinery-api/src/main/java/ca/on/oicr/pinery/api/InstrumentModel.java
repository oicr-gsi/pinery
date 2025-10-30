package ca.on.oicr.pinery.api;

import java.util.Date;

public interface InstrumentModel {

  Integer getId();

  void setId(Integer id);

  String getName();

  void setName(String name);

  Date getCreated();

  void setCreated(Date created);

  Integer getCreatedById();

  void setCreatedById(Integer createdById);

  Date getModified();

  void setModified(Date modified);

  Integer getModifiedById();

  void setModifiedById(Integer modifiedById);

  void setPlatform(String platform);

  String getPlatform();

  boolean hasMultipleContainers();

  void setMultipleContainers(boolean multipleContainers);
  
}
