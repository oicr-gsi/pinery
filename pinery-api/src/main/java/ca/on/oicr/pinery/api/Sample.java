package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.Set;

public interface Sample {

  public String getUrl();

  public void setUrl(String url);

  public String getName();

  public void setName(String name);

  public String getDescription();

  public void setDescription(String description);

  public String getId();

  public void setId(String id);

  public Set<String> getParents();

  public void setParents(Set<String> parents);

  public Set<String> getChildren();

  public void setChildren(Set<String> children);

  public String getSampleType();

  public void setSampleType(String sampleType);

  public String getTissueType();

  public void setTissueType(String tissueType);

  public String getProject();

  public void setProject(String project);

  public Set<Attribute> getAttributes();

  public void setAttributes(Set<Attribute> attributes);

  public Boolean getArchived();

  public void setArchived(Boolean archived);

  public Date getCreated();

  public void setCreated(Date created);

  public Date getModified();

  public void setModified(Date modified);

  public String getTubeBarcode();

  public void setTubeBarcode(String tubeBarcode);

  public Float getVolume();

  public void setVolume(Float volume);

  public Float getConcentration();

  public void setConcentration(Float concentration);

  public String getConcentrationUnits();

  public void setConcentrationUnits(String concentrationUnits);

  public String getStorageLocation();

  public void setStorageLocation(String storageLocation);

  public PreparationKit getPreparationKit();

  public void setPreparationKit(PreparationKit preparationKit);

  public Status getStatus();

  public void setStatus(Status status);

  public Integer getCreatedById();

  public void setCreatedById(Integer createdById);

  public Integer getModifiedById();

  public void setModifiedById(Integer modifiedById);

  public Long getPreMigrationId();

  public void setPreMigrationId(Long preMigrationId);
}
