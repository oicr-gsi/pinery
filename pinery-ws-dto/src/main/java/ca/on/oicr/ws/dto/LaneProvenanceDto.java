package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;

/** @author mlaszloffy */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaneProvenanceDto implements LaneProvenance {

  private String sequencerRunName;
  private SortedMap<String, SortedSet<String>> sequencerRunAttributes;
  private String sequencerRunPlatformModel;
  private String laneNumber;
  private SortedMap<String, SortedSet<String>> laneAttributes;
  private Boolean skip;
  private String laneProvenanceId;
  private String version;
  private ZonedDateTime lastModified;
  private ZonedDateTime createdDate;

  public LaneProvenanceDto() {}

  public LaneProvenanceDto(LaneProvenance from) {
    sequencerRunName = from.getSequencerRunName();
    sequencerRunAttributes = from.getSequencerRunAttributes();
    sequencerRunPlatformModel = from.getSequencerRunPlatformModel();
    laneNumber = from.getLaneNumber();
    laneAttributes = from.getLaneAttributes();
    skip = from.getSkip();
    laneProvenanceId = from.getLaneProvenanceId();
    version = from.getVersion();
    lastModified = from.getLastModified();
    createdDate = from.getCreatedDate();
  }

  @Override
  public String getSequencerRunName() {
    return sequencerRunName;
  }

  public void setSequencerRunName(String sequencerRunName) {
    this.sequencerRunName = sequencerRunName;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getSequencerRunAttributes() {
    return sequencerRunAttributes;
  }

  public void setSequencerRunAttributes(
      SortedMap<String, SortedSet<String>> sequencerRunAttributes) {
    this.sequencerRunAttributes = sequencerRunAttributes;
  }

  @Override
  public String getSequencerRunPlatformModel() {
    return sequencerRunPlatformModel;
  }

  public void setSequencerRunPlatformModel(String sequencerRunPlatformModel) {
    this.sequencerRunPlatformModel = sequencerRunPlatformModel;
  }

  @Override
  public String getLaneNumber() {
    return laneNumber;
  }

  public void setLaneNumber(String laneNumber) {
    this.laneNumber = laneNumber;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getLaneAttributes() {
    return laneAttributes;
  }

  public void setLaneAttributes(SortedMap<String, SortedSet<String>> laneAttributes) {
    this.laneAttributes = laneAttributes;
  }

  @Override
  public Boolean getSkip() {
    return skip;
  }

  public void setSkip(Boolean skip) {
    this.skip = skip;
  }

  @Override
  public String getLaneProvenanceId() {
    return laneProvenanceId;
  }

  public void setLaneProvenanceId(String laneProvenanceId) {
    this.laneProvenanceId = laneProvenanceId;
  }

  @JsonIgnore
  @Override
  public String getProvenanceId() {
    return laneProvenanceId;
  }

  @Override
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public ZonedDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(ZonedDateTime lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public ZonedDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(ZonedDateTime createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return "LaneProvenanceDto{"
        + "sequencerRunName='"
        + sequencerRunName
        + '\''
        + ", sequencerRunAttributes="
        + sequencerRunAttributes
        + ", sequencerRunPlatformModel='"
        + sequencerRunPlatformModel
        + '\''
        + ", laneNumber='"
        + laneNumber
        + '\''
        + ", laneAttributes="
        + laneAttributes
        + ", skip="
        + skip
        + ", laneProvenanceId='"
        + laneProvenanceId
        + '\''
        + ", version='"
        + version
        + '\''
        + ", lastModified="
        + lastModified
        + ", createdDate="
        + createdDate
        + '}';
  }
}
