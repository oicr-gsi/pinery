package ca.on.oicr.pinery.api;

import java.util.Date;

public interface Change {

  public String getAction();

  public void setAction(String action);

  public Integer getCreatedById();

  public void setCreatedById(Integer createdById);

  public Date getCreated();

  public void setCreated(Date created);

  public String getComment();

  public void setComment(String comment);
}
