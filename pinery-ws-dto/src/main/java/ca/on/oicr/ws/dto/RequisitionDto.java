package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitionDto {

  private Integer id;
  private String name;
  private Integer assayId;
  private Set<String> sampleIds;
  private Set<String> supplementalSampleIds;
  private List<SignOffDto> signOffs;
  private boolean stopped = false;
  private String stopReason;

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

  @JsonProperty("assay_id")
  public Integer getAssayId() {
    return assayId;
  }

  public void setAssayId(Integer assayId) {
    this.assayId = assayId;
  }

  @JsonProperty("sample_ids")
  public Set<String> getSampleIds() {
    return sampleIds;
  }

  public void setSampleIds(Set<String> sampleIds) {
    this.sampleIds = sampleIds;
  }

  @JsonProperty("supplemental_sample_ids")
  public Set<String> getSupplementalSampleIds() {
    return supplementalSampleIds;
  }

  public void setSupplementalSampleIds(Set<String> supplementalSampleIds) {
    this.supplementalSampleIds = supplementalSampleIds;
  }

  @JsonProperty("sign_offs")
  public List<SignOffDto> getSignOffs() {
    return signOffs;
  }

  public void setSignOffs(List<SignOffDto> signOffs) {
    this.signOffs = signOffs;
  }

  public boolean isStopped() {
    return stopped;
  }

  public void setStopped(boolean stopped) {
    this.stopped = stopped;
  }

  @JsonProperty("stop_reason")
  public String getStopReason() {
    return stopReason;
  }

  public void setStopReason(String stopReason) {
    this.stopReason = stopReason;
  }

  @Override
  public int hashCode() {
    return Objects.hash(assayId, id, name, sampleIds, signOffs, stopped, stopReason);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RequisitionDto other = (RequisitionDto) obj;
    return Objects.equals(assayId, other.assayId)
        && Objects.equals(id, other.id)
        && Objects.equals(name, other.name)
        && Objects.equals(sampleIds, other.sampleIds)
        && Objects.equals(signOffs, other.signOffs)
        && Objects.equals(stopped, other.stopped)
        && Objects.equals(stopReason, other.stopReason);
  }
}
