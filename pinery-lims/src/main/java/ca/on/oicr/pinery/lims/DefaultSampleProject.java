package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.SampleProject;
import java.util.Date;

public class DefaultSampleProject implements SampleProject {

  private String name;
  private Integer count = 0;
  private Integer archivedCount = 0;
  private Date earliest;
  private Date latest;
  private boolean active = true;
  private boolean clinical = false;
  private String pipeline;
  private boolean secondaryNamingScheme;
  private Date created;
  private String rebNumber;
  private Date rebExpiry;
  private String description;
  private Integer samplesExpected;
  private String contactName;
  private String contactEmail;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Integer getCount() {
    return count;
  }

  @Override
  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public Date getEarliest() {
    return earliest != null ? new Date(earliest.getTime()) : null;
  }

  @Override
  public void setEarliest(Date earliest) {
    this.earliest = earliest != null ? new Date(earliest.getTime()) : null;
  }

  @Override
  public Date getLatest() {
    return latest != null ? new Date(latest.getTime()) : null;
  }

  @Override
  public void setLatest(Date latest) {
    this.latest = latest != null ? new Date(latest.getTime()) : null;
  }

  @Override
  public Integer getArchivedCount() {
    if (archivedCount == null) {
      archivedCount = 0;
    }
    return archivedCount;
  }

  @Override
  public void setArchivedCount(Integer archivedCount) {
    this.archivedCount = archivedCount;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public boolean isClinical() {
    return clinical;
  }

  @Override
  public void setClinical(boolean clinical) {
    this.clinical = clinical;
  }

  @Override
  public String getPipeline() {
    return pipeline;
  }

  @Override
  public void setPipeline(String pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public boolean isSecondaryNamingScheme() {
    return secondaryNamingScheme;
  }

  @Override
  public void setSecondaryNamingScheme(boolean secondaryNamingScheme) {
    this.secondaryNamingScheme = secondaryNamingScheme;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public String getRebNumber() {
    return rebNumber;
  }

  @Override
  public void setRebNumber(String rebNumber) {
    this.rebNumber = rebNumber;
  }

  @Override
  public Date getRebExpiry() {
    return rebExpiry;
  }

  @Override
  public void setRebExpiry(Date rebExpiry) {
    this.rebExpiry = rebExpiry;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public Integer getSamplesExpected() {
    return samplesExpected;
  }

  @Override
  public void setSamplesExpected(Integer samplesExpected) {
    this.samplesExpected = samplesExpected;
  }

  @Override
  public String getContactName() {
    return contactName;
  }

  @Override
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  @Override
  public String getContactEmail() {
    return contactEmail;
  }

  @Override
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }
}
