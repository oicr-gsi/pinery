package ca.on.oicr.pinery.lims;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;

public class SimpleLaneProvenance implements LaneProvenance {

  private String laneProvenanceId;
  private String provenanceId;
  private String laneNumber;
  private Boolean skip;
  private SortedMap<String, SortedSet<String>> laneAttributes;
  private String sequencerRunName;
  private String sequencerRunPlatformModel;
  private SortedMap<String, SortedSet<String>> sequencerRunAttributes;
  private ZonedDateTime createdDate;
  private ZonedDateTime lastModified;

  public static SimpleLaneProvenance from(LaneProvenance from) {
    SimpleLaneProvenance to = new SimpleLaneProvenance();
    to.setLaneProvenanceId(from.getLaneProvenanceId());
    to.setProvenanceId(from.getProvenanceId());
    to.setLaneNumber(from.getLaneNumber());
    to.setSkip(from.getSkip());
    to.setLaneAttributes(from.getLaneAttributes());
    to.setSequencerRunName(from.getSequencerRunName());
    to.setSequencerRunPlatformModel(from.getSequencerRunPlatformModel());
    to.setSequencerRunAttributes(from.getSequencerRunAttributes());
    to.setCreatedDate(from.getCreatedDate());
    to.setLastModified(from.getLastModified());
    return to;
  }

  @Override
  public ZonedDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(ZonedDateTime lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public String getProvenanceId() {
    return provenanceId;
  }

  public void setProvenanceId(String provenanceId) {
    this.provenanceId = provenanceId;
  }

  @Override
  public String getVersion() {
    StringBuilder sb = new StringBuilder();
    sb.append(getSequencerRunName());
    sb.append(getSequencerRunAttributes());
    sb.append(getSequencerRunPlatformModel());
    sb.append(getLaneNumber());
    sb.append(getLaneAttributes());
    String s = sb.toString();
    return Hashing.sha256().hashString(s, Charsets.UTF_8).toString();
  }

  @Override
  public ZonedDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(ZonedDateTime createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getLaneAttributes() {
    return laneAttributes;
  }

  public void setLaneAttributes(SortedMap<String, SortedSet<String>> laneAttributes) {
    this.laneAttributes = laneAttributes;
  }

  @Override
  public String getLaneNumber() {
    return laneNumber;
  }

  public void setLaneNumber(String laneNumber) {
    this.laneNumber = laneNumber;
  }

  @Override
  public String getLaneProvenanceId() {
    return laneProvenanceId;
  }

  public void setLaneProvenanceId(String laneProvenanceId) {
    this.laneProvenanceId = laneProvenanceId;
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
  public String getSequencerRunName() {
    return sequencerRunName;
  }

  public void setSequencerRunName(String sequencerRunName) {
    this.sequencerRunName = sequencerRunName;
  }

  @Override
  public String getSequencerRunPlatformModel() {
    return sequencerRunPlatformModel;
  }

  public void setSequencerRunPlatformModel(String sequencerRunPlatformModel) {
    this.sequencerRunPlatformModel = sequencerRunPlatformModel;
  }

  @Override
  public Boolean getSkip() {
    return skip;
  }

  public void setSkip(Boolean skip) {
    this.skip = skip;
  }
}
