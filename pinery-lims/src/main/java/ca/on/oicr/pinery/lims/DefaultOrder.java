package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import java.util.Date;
import java.util.Set;

public class DefaultOrder implements Order {

  @Override
  public String toString() {
    return "DefaultOrder [status="
        + status
        + ", project="
        + project
        + ", platform="
        + platform
        + ", sample="
        + sample
        + ", createdDate="
        + createdDate
        + ", modifiedDate="
        + modifiedDate
        + ", id="
        + id
        + ", createdById="
        + createdById
        + ", modifiedById="
        + modifiedById
        + ", getStatus()="
        + getStatus()
        + ", getProject()="
        + getProject()
        + ", getPlatform()="
        + getPlatform()
        + ", getSamples()="
        + getSamples()
        + ", getCreatedDate()="
        + getCreatedDate()
        + ", getModifiedDate()="
        + getModifiedDate()
        + ", getId()="
        + getId()
        + ", getCreatedById()="
        + getCreatedById()
        + ", getModifiedById()="
        + getModifiedById()
        + ", getClass()="
        + getClass()
        + ", hashCode()="
        + hashCode()
        + ", toString()="
        + super.toString()
        + "]";
  }

  private String status;
  private String project;
  private String platform;
  private Set<OrderSample> sample;
  private Date createdDate;
  private Date modifiedDate;
  private Integer id;
  private Integer createdById;
  private Integer modifiedById;

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String getProject() {
    return project;
  }

  @Override
  public void setProject(String project) {
    this.project = project;
  }

  @Override
  public String getPlatform() {
    return platform;
  }

  @Override
  public void setPlatform(String platform) {
    this.platform = platform;
  }

  @Override
  public Set<OrderSample> getSamples() {
    return sample;
  }

  @Override
  public void setSample(Set<OrderSample> sample) {
    this.sample = sample;
  }

  @Override
  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public Date getModifiedDate() {
    return modifiedDate;
  }

  @Override
  public void setModifiedDate(Date modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public Integer getCreatedById() {
    return createdById;
  }

  @Override
  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  @Override
  public Integer getModifiedById() {
    return modifiedById;
  }

  @Override
  public void setModifiedById(Integer modifiedById) {
    this.modifiedById = modifiedById;
  }
}
