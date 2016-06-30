package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public class SampleProvenanceDto implements SampleProvenance {

    private String studyTitle;
    private Map<String, Set<String>> studyAttributes;
    private String rootSampleName;
    private String parentSampleName;
    private String sampleName;
    private Map<String, Set<String>> sampleAttributes;
    private String sequencerRunName;
    private Map<String, Set<String>> sequencerRunAttributes;
    private String sequencerRunPlatformModel;
    private String laneNumber;
    private Map<String, Set<String>> laneAttributes;
    private String iusTag;
    private Boolean skip;
    private String sampleProvenanceId;
    private String version;

    private DateTime lastModified;

    @Override
    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    @Override
    public Map<String, Set<String>> getStudyAttributes() {
        return studyAttributes;
    }

    public void setStudyAttributes(Map<String, Set<String>> studyAttributes) {
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
    public Map<String, Set<String>> getSampleAttributes() {
        return sampleAttributes;
    }

    public void setSampleAttributes(Map<String, Set<String>> sampleAttributes) {
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
    public Map<String, Set<String>> getSequencerRunAttributes() {
        return sequencerRunAttributes;
    }

    public void setSequencerRunAttributes(Map<String, Set<String>> sequencerRunAttributes) {
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
    public Map<String, Set<String>> getLaneAttributes() {
        return laneAttributes;
    }

    public void setLaneAttributes(Map<String, Set<String>> laneAttributes) {
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

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public DateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(DateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
