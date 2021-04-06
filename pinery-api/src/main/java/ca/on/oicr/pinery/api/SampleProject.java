package ca.on.oicr.pinery.api;

import java.util.Date;

public interface SampleProject {

  public String getName();

  public void setName(String name);

  public Integer getCount();

  public void setCount(Integer count);

  public Integer getArchivedCount();

  public void setArchivedCount(Integer count);

  public Date getEarliest();

  public void setEarliest(Date earliest);

  public Date getLatest();

  public void setLatest(Date latest);

  public boolean isActive();

  public void setActive(boolean active);

  public String getPipeline();

  public void setPipeline(String pipeline);

  public boolean isSecondaryNamingScheme();

  public void setSecondaryNamingScheme(boolean secondaryNamingScheme);

  public Date getCreated();

  public void setCreated(Date created);

  public String getRebNumber();

  public void setRebNumber(String rebNumber);

  public Date getRebExpiry();

  public void setRebExpiry(Date rebExpiry);

  public String getDescription();

  public void setDescription(String description);

  public Integer getSamplesExpected();

  public void setSamplesExpected(Integer samplesExpected);

  public String getContactName();

  public void setContactName(String contactName);

  public String getContactEmail();

  public void setContactEmail(String contactEmail);
}
