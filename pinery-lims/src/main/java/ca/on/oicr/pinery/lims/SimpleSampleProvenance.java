package ca.on.oicr.pinery.lims;

import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;

public class SimpleSampleProvenance implements SampleProvenance {
  
  private String sampleProvenanceId;
  private String provenanceId;
  private String sampleName;
  private String parentSampleName;
  private String rootSampleName;
  private SortedMap<String, SortedSet<String>> sampleAttributes;
  private String studyTitle;
  private SortedMap<String, SortedSet<String>> studyAttributes;
  private String iusTag;
  private String laneNumber;
  private Boolean skip;
  private SortedMap<String, SortedSet<String>> laneAttributes;
  private String sequencerRunName;
  private String sequencerRunPlatformModel;
  private String sequencerRunPlatform;
  private SortedMap<String, SortedSet<String>> sequencerRunAttributes;
  private ZonedDateTime createdDate;
  private ZonedDateTime lastModified;
  
  public static SimpleSampleProvenance from(SampleProvenance from) {
    SimpleSampleProvenance to = new SimpleSampleProvenance();
    
    to.setSampleProvenanceId(from.getSampleProvenanceId());
    to.setProvenanceId(from.getProvenanceId());
    to.setSampleName(from.getSampleName());
    to.setParentSampleName(from.getParentSampleName());
    to.setRootSampleName(from.getRootSampleName());
    to.setSampleAttributes(from.getSampleAttributes());
    to.setStudyTitle(from.getStudyTitle());
    to.setStudyAttributes(from.getStudyAttributes());
    to.setIusTag(from.getIusTag());
    to.setLaneNumber(from.getLaneNumber());
    to.setSkip(from.getSkip());
    to.setLaneAttributes(from.getLaneAttributes());
    to.setSequencerRunName(from.getSequencerRunName());
    to.setSequencerRunPlatformModel(from.getSequencerRunPlatformModel());
    to.setSequencerRunPlatform(from.getSequencerRunPlatform());
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
    // Sample attribute LimsAttribute enum key names used in hash instead of actual map keys
    SortedMap<String, SortedSet<String>> sampleAttrs = new TreeMap<>();
    getSampleAttributes().forEach((key, values) -> {
      LimsSampleAttribute limsAttr = LimsSampleAttribute.fromString(key);
      String newKey = limsAttr == null ? key : limsAttr.name();
      sampleAttrs.put(newKey, values);
    });
    
    StringBuilder sb = new StringBuilder();
    sb.append(getStudyTitle());
    sb.append(getStudyAttributes());
    sb.append(getRootSampleName());
    sb.append(getParentSampleName());
    sb.append(getSampleName());
    sb.append(sampleAttrs);
    sb.append(getSequencerRunName());
    sb.append(getSequencerRunAttributes());
    sb.append(getSequencerRunPlatformModel());
    sb.append(getLaneNumber());
    sb.append(getLaneAttributes());
    sb.append(getIusTag());
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
  public String getIusTag() {
    return iusTag;
  }
  
  public void setIusTag(String iusTag) {
    this.iusTag = iusTag;
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
  public String getParentSampleName() {
    return parentSampleName;
  }
  
  public void setParentSampleName(String parentSampleName) {
    this.parentSampleName = parentSampleName;
  }

  @Override
  public String getRootSampleName() {
    return rootSampleName;
  }
  
  public void setRootSampleName(String rootSampleName) {
    this.rootSampleName = rootSampleName;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getSampleAttributes() {
    return sampleAttributes;
  }
  
  public void setSampleAttributes(SortedMap<String, SortedSet<String>> sampleAttributes) {
    this.sampleAttributes = sampleAttributes;
  }

  @Override
  public String getSampleName() {
    return sampleName;
  }
  
  public void setSampleName(String sampleName) {
    this.sampleName = sampleName;
  }

  @Override
  public String getSampleProvenanceId() {
    return sampleProvenanceId;
  }
  
  public void setSampleProvenanceId(String sampleProvenanceId) {
    this.sampleProvenanceId = sampleProvenanceId;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getSequencerRunAttributes() {
    return sequencerRunAttributes;
  }
  
  public void setSequencerRunAttributes(SortedMap<String, SortedSet<String>> sequencerRunAttributes) {
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
  public String getSequencerRunPlatform() {
    return sequencerRunPlatform;
  }
  
  public void setSequencerRunPlatform(String sequencerRunPlatform) {
    this.sequencerRunPlatform = sequencerRunPlatform;
  }

  @Override
  public Boolean getSkip() {
    return skip;
  }
  
  public void setSkip(Boolean skip) {
    this.skip = skip;
  }

  @Override
  public SortedMap<String, SortedSet<String>> getStudyAttributes() {
    return studyAttributes;
  }
  
  public void setStudyAttributes(SortedMap<String, SortedSet<String>> studyAttributes) {
    this.studyAttributes = studyAttributes;
  }

  @Override
  public String getStudyTitle() {
    return studyTitle;
  }
  
  public void setStudyTitle(String studyTitle) {
    this.studyTitle = studyTitle;
  }

}
