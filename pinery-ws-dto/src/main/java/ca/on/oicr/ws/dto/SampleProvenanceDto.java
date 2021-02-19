package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;

/** @author mlaszloffy */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleProvenanceDto implements SampleProvenance {

  private String studyTitle;
  private SortedMap<String, SortedSet<String>> studyAttributes;
  private String rootSampleName;
  private String parentSampleName;
  private String sampleName;
  private SortedMap<String, SortedSet<String>> sampleAttributes;
  private String sequencerRunName;
  private SortedMap<String, SortedSet<String>> sequencerRunAttributes;
  private String sequencerRunPlatformModel;
  private String laneNumber;
  private SortedMap<String, SortedSet<String>> laneAttributes;
  private String iusTag;
  private Boolean skip;
  private String sampleProvenanceId;
  private String version;
  private ZonedDateTime lastModified;
  private ZonedDateTime createdDate;

  public SampleProvenanceDto() {}

  public SampleProvenanceDto(SampleProvenance from) {
    studyTitle = from.getStudyTitle();
    studyAttributes = from.getStudyAttributes();
    rootSampleName = from.getRootSampleName();
    parentSampleName = from.getParentSampleName();
    sampleName = from.getSampleName();
    sampleAttributes = from.getSampleAttributes();
    sequencerRunName = from.getSequencerRunName();
    sequencerRunAttributes = from.getSequencerRunAttributes();
    sequencerRunPlatformModel = from.getSequencerRunPlatformModel();
    laneNumber = from.getLaneNumber();
    laneAttributes = from.getLaneAttributes();
    iusTag = from.getIusTag();
    skip = from.getSkip();
    sampleProvenanceId = from.getSampleProvenanceId();
    version = from.getVersion();
    lastModified = from.getLastModified();
    createdDate = from.getCreatedDate();
  }

  @Override
  public String getStudyTitle() {
    return studyTitle;
  }

  public void setStudyTitle(String studyTitle) {
    this.studyTitle = studyTitle;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getStudyAttributes() {
    return studyAttributes;
  }

  public void setStudyAttributes(SortedMap<String, SortedSet<String>> studyAttributes) {
    this.studyAttributes = studyAttributes;
  }

  @Override
  public String getRootSampleName() {
    return rootSampleName;
  }

  public void setRootSampleName(String rootSampleName) {
    this.rootSampleName = rootSampleName;
  }

  @Override
  public String getParentSampleName() {
    return parentSampleName;
  }

  public void setParentSampleName(String parentSampleName) {
    this.parentSampleName = parentSampleName;
  }

  @Override
  public String getSampleName() {
    return sampleName;
  }

  public void setSampleName(String sampleName) {
    this.sampleName = sampleName;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getSampleAttributes() {
    return sampleAttributes;
  }

  public void setSampleAttributes(SortedMap<String, SortedSet<String>> sampleAttributes) {
    this.sampleAttributes = sampleAttributes;
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
  public String getIusTag() {
    return iusTag;
  }

  public void setIusTag(String iusTag) {
    this.iusTag = iusTag;
  }

  @Override
  public Boolean getSkip() {
    return skip;
  }

  public void setSkip(Boolean skip) {
    this.skip = skip;
  }

  @Override
  public String getSampleProvenanceId() {
    return sampleProvenanceId;
  }

  public void setSampleProvenanceId(String sampleProvenanceId) {
    this.sampleProvenanceId = sampleProvenanceId;
  }

  @JsonIgnore
  @Override
  public String getProvenanceId() {
    return sampleProvenanceId;
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
    return "SampleProvenanceDto{"
        + "studyTitle='"
        + studyTitle
        + '\''
        + ", studyAttributes="
        + studyAttributes
        + ", rootSampleName='"
        + rootSampleName
        + '\''
        + ", parentSampleName='"
        + parentSampleName
        + '\''
        + ", sampleName='"
        + sampleName
        + '\''
        + ", sampleAttributes="
        + sampleAttributes
        + ", sequencerRunName='"
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
        + ", iusTag='"
        + iusTag
        + '\''
        + ", skip="
        + skip
        + ", sampleProvenanceId='"
        + sampleProvenanceId
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
