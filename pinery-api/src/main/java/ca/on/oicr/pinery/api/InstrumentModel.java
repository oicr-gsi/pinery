package ca.on.oicr.pinery.api;

import java.util.Date;

public interface InstrumentModel {

  public Integer getId();

  public void setId(Integer id);

  public String getName();

  public void setName(String name);

  public Date getCreated();

  public void setCreated(Date created);

  public Integer getCreatedById();

  public void setCreatedById(Integer createdById);

  public Date getModified();

  public void setModified(Date modified);

  public Integer getModifiedById();

  public void setModifiedById(Integer modifiedById);

  public void setPlatform(String platform);

  public String getPlatform();
}
