package ca.on.oicr.ws.dto;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.joda.time.DateTime;

/**
 *
 * @author mlaszloffy
 */
public class LaneProvenanceDto implements LaneProvenance {

    private String sequencerRunName;
    private Map<String, Set<String>> sequencerRunAttributes;
    private String sequencerRunPlatformModel;
    private String laneNumber;
    private Map<String, Set<String>> laneAttributes;
    private String laneProvenanceId;
    private String version;
    private DateTime lastModified;

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
    public String getLaneProvenanceId() {
        return laneProvenanceId;
    }

    public void setLaneProvenanceId(String laneProvenanceId) {
        this.laneProvenanceId = laneProvenanceId;
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
