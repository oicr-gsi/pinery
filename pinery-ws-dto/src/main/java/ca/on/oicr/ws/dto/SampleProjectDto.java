package ca.on.oicr.ws.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleProjectDto {

  private String name;
  private Integer count;
  private Integer archivedCount;
  private String earliest;
  private String latest;
  private boolean active;
  private String pipeline;
  private boolean secondaryNamingScheme;
  private String createdDate;
  private String rebNumber;
  private String rebExpiry;
  private String description;
  private Integer samplesExpected;
  private String contactName;
  private String contactEmail;
  private Set<String> deliverables;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getEarliest() {
    return earliest;
  }

  public void setEarliest(String earliest) {
    this.earliest = earliest;
  }

  public String getLatest() {
    return latest;
  }

  public void setLatest(String latest) {
    this.latest = latest;
  }

  @JsonProperty("archived_count")
  public Integer getArchivedCount() {
    return archivedCount;
  }

  public void setArchivedCount(Integer archivedCount) {
    this.archivedCount = archivedCount;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getPipeline() {
    return pipeline;
  }

  public void setPipeline(String pipeline) {
    this.pipeline = pipeline;
  }

  @JsonProperty("secondary_naming_scheme")
  public boolean isSecondaryNamingSCheme() {
    return secondaryNamingScheme;
  }

  public void setSecondaryNamingSCheme(boolean secondaryNamingSCheme) {
    this.secondaryNamingScheme = secondaryNamingSCheme;
  }

  @JsonProperty("created_date")
  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  @JsonProperty("reb_number")
  public String getRebNumber() {
    return rebNumber;
  }

  public void setRebNumber(String rebNumber) {
    this.rebNumber = rebNumber;
  }

  @JsonProperty("reb_expiry")
  public String getRebExpiry() {
    return rebExpiry;
  }

  public void setRebExpiry(String rebExpiry) {
    this.rebExpiry = rebExpiry;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonProperty("samples_expected")
  public Integer getSamplesExpected() {
    return samplesExpected;
  }

  public void setSamplesExpected(Integer samplesExpected) {
    this.samplesExpected = samplesExpected;
  }

  @JsonProperty("contact_name")
  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  @JsonProperty("contact_email")
  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public Set<String> getDeliverables() {
    return deliverables;
  }

  public void setDeliverables(Set<String> deliverables) {
    this.deliverables = deliverables;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((archivedCount == null) ? 0 : archivedCount.hashCode());
    result = prime * result + ((count == null) ? 0 : count.hashCode());
    result = prime * result + ((earliest == null) ? 0 : earliest.hashCode());
    result = prime * result + ((latest == null) ? 0 : latest.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + Boolean.hashCode(active);
    result = prime * result + Boolean.hashCode(secondaryNamingScheme);
    result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
    result = prime * result + ((rebNumber == null) ? 0 : rebNumber.hashCode());
    result = prime * result + ((rebExpiry == null) ? 0 : rebExpiry.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((samplesExpected == null) ? 0 : samplesExpected.hashCode());
    result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
    result = prime * result + ((contactEmail == null) ? 0 : contactEmail.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SampleProjectDto other = (SampleProjectDto) obj;
    if (archivedCount == null) {
      if (other.archivedCount != null)
        return false;
    } else if (!archivedCount.equals(other.archivedCount))
      return false;
    if (count == null) {
      if (other.count != null)
        return false;
    } else if (!count.equals(other.count))
      return false;
    if (earliest == null) {
      if (other.earliest != null)
        return false;
    } else if (!earliest.equals(other.earliest))
      return false;
    if (latest == null) {
      if (other.latest != null)
        return false;
    } else if (!latest.equals(other.latest))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (active != other.active)
      return false;
    if (secondaryNamingScheme != other.secondaryNamingScheme)
      return false;
    if (createdDate == null) {
      if (other.createdDate != null)
        return false;
    } else if (!createdDate.equals(other.createdDate))
      return false;
    if (rebNumber == null) {
      if (other.rebNumber != null)
        return false;
    } else if (!rebNumber.equals(other.rebNumber))
      return false;
    if (rebExpiry == null) {
      if (other.rebExpiry != null)
        return false;
    } else if (!rebExpiry.equals(other.rebExpiry))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (samplesExpected == null) {
      if (other.samplesExpected != null)
        return false;
    } else if (!samplesExpected.equals(other.samplesExpected))
      return false;
    if (contactName == null) {
      if (other.contactName != null)
        return false;
    } else if (!contactName.equals(other.contactName))
      return false;
    if (contactEmail == null) {
      if (other.contactEmail != null)
        return false;
    } else if (!contactEmail.equals(other.contactEmail))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SampleProjectDto [name="
        + name
        + ", count="
        + count
        + ", archivedCount="
        + archivedCount
        + ", earliest="
        + earliest
        + ", latest="
        + latest
        + ", active="
        + active
        + ", secondaryNamingScheme="
        + secondaryNamingScheme
        + "]";
  }
}
