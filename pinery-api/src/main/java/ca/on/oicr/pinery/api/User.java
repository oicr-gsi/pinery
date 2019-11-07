package ca.on.oicr.pinery.api;

import java.util.Date;

public interface User {

  public Integer getId();

  public void setId(Integer id);

  public String getTitle();

  public void setTitle(String title);

  public String getFirstname();

  public void setFirstname(String firstname);

  public String getLastname();

  public void setLastname(String lastname);

  public String getInstitution();

  public void setInstitution(String institution);

  public String getPhone();

  public void setPhone(String phone);

  public String getEmail();

  public void setEmail(String email);

  public Boolean getArchived();

  public void setArchived(Boolean archived);

  public String getComment();

  public void setComment(String comment);

  public Date getCreated();

  public void setCreated(Date created);

  public Integer getCreatedById();

  public void setCreatedById(Integer createdById);

  public Date getModified();

  public void setModified(Date modified);

  public Integer getModifiedById();

  public void setModifiedById(Integer modifiedById);
}
