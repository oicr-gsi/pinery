package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
