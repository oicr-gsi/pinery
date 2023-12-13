package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.Set;

public interface SampleProject {

  String getName();

  void setName(String name);

  Integer getCount();

  void setCount(Integer count);

  Integer getArchivedCount();

  void setArchivedCount(Integer count);

  Date getEarliest();

  void setEarliest(Date earliest);

  Date getLatest();

  void setLatest(Date latest);

  boolean isActive();

  void setActive(boolean active);

  String getPipeline();

  void setPipeline(String pipeline);

  boolean isSecondaryNamingScheme();

  void setSecondaryNamingScheme(boolean secondaryNamingScheme);

  Date getCreated();

  void setCreated(Date created);

  String getRebNumber();

  void setRebNumber(String rebNumber);

  Date getRebExpiry();

  void setRebExpiry(Date rebExpiry);

  String getDescription();

  void setDescription(String description);

  Integer getSamplesExpected();

  void setSamplesExpected(Integer samplesExpected);

  String getContactName();

  void setContactName(String contactName);

  String getContactEmail();

  void setContactEmail(String contactEmail);

  Set<String> getDeliverables();

  void setDeliverables(Set<String> deliverables);
}
