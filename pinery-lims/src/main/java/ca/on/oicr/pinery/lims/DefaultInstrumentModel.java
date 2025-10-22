package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.InstrumentModel;
import java.util.Date;

public class DefaultInstrumentModel implements InstrumentModel {

  private Integer id;
  private String name;
  private Date created;
  private Integer createdById;
  private Date modified;
  private Integer modifiedById;
  private String platform;
  private boolean multipleContainers;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Integer getCreatedById() {
    return createdById;
  }

  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public Integer getModifiedById() {
    return modifiedById;
  }

  public void setModifiedById(Integer modifiedById) {
    this.modifiedById = modifiedById;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public boolean hasMultipleContainers() {
    return multipleContainers;
  }

  public void setMultipleContainers(boolean multipleContainers) {
    this.multipleContainers = multipleContainers;
  }
}
