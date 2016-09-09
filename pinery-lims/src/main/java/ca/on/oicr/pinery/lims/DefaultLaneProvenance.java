package ca.on.oicr.pinery.lims;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import com.google.common.base.Charsets;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.hash.Hashing;
import java.util.Date;
import java.util.SortedMap;
import java.util.SortedSet;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author mlaszloffy
 */
public class DefaultLaneProvenance implements LaneProvenance {

    private Instrument instrument;
    private InstrumentModel instrumentModel;
    private Run sequencerRun;
    private RunPosition lane;

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public void setInstrumentModel(InstrumentModel instrumentModel) {
        this.instrumentModel = instrumentModel;
    }

    public void setSequencerRun(Run sequencerRun) {
        this.sequencerRun = sequencerRun;
    }

    public void setLane(RunPosition lane) {
        this.lane = lane;
    }

    @Override
    public String getSequencerRunName() {
        if (sequencerRun == null) {
            return null;
        } else {
            return sequencerRun.getName();
        }
    }

    @Override
    public SortedMap<String, SortedSet<String>> getSequencerRunAttributes() {
        SortedSetMultimap<String, String> attrs = TreeMultimap.create();
        if (instrument != null) {
            attrs.put("instrument_name", instrument.getName());
        }
        return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrs);
    }

    @Override
    public String getSequencerRunPlatformModel() {
        if (instrumentModel == null) {
            return null;
        } else {
            return instrumentModel.getName();
        }
    }

    @Override
    public String getLaneNumber() {
        if (lane == null) {
            return null;
        } else {
            return lane.getPosition().toString();
        }
    }

    @Override
    public SortedMap<String, SortedSet<String>> getLaneAttributes() {
        SortedSetMultimap attrs = TreeMultimap.create();
        //lane.getAttributes();
        return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrs);
    }

    @Override
    public Boolean getSkip() {
        return false;
    }

    @Override
    public String getLaneProvenanceId() {
        return sequencerRun.getId() + "_" + lane.getPosition();
    }

    @Override
    public String getProvenanceId() {
        return getLaneProvenanceId();
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
    public DateTime getLastModified() {
        DateTime lastModified = null;

        if (sequencerRun != null) {
            lastModified = ObjectUtils.max(lastModified,
                    getDateTimeNullSafe(sequencerRun.getCreatedDate()),
                    getDateTimeNullSafe(sequencerRun.getCompletionDate()),
                    getDateTimeNullSafe(sequencerRun.getModified()));
        }
        if (lane != null) {
//            lastModified = ObjectUtils.max(lastModified,
//                    getDateTimeNullSafe(lane.getCreatedDate()),
//                    getDateTimeNullSafe(lane.getModified()));
        }

        if (lastModified == null) {
            return null;
        } else {
            return lastModified.toDateTime(DateTimeZone.UTC);
        }
    }

    @Override
    public DateTime getCreatedDate() {
        DateTime createdDate = null;

        if (sequencerRun != null) {
            createdDate = ObjectUtils.min(createdDate,
                    //completion date is used as this is the first date that this provenance object is ready for processing
                    getDateTimeNullSafe(sequencerRun.getCompletionDate()));
        }
        if (lane != null) {
//            lastModified = ObjectUtils.min(lastModified,
//                    getDateTimeNullSafe(lane.getCreatedDate()));
        }

        if (createdDate == null) {
            return null;
        } else {
            return createdDate.toDateTime(DateTimeZone.UTC);
        }
    }

    @Override
    public String toString() {
        return "SampleProvenance{"
                + "sequencerRunName=" + getSequencerRunName() + ", "
                + "sequencerRunAttributes=" + getSequencerRunAttributes() + ", "
                + "sequencerRunPlatformModel=" + getSequencerRunPlatformModel() + ", "
                + "laneNumber=" + getLaneNumber() + ", "
                + "laneAttributes=" + getLaneAttributes() + ", "
                + "skip=" + getSkip() + ", "
                + "laneProvenanceId=" + getLaneProvenanceId() + ", "
                + "version=" + getVersion() + ", "
                + "lastModified=" + getLastModified() + ", "
                + "createdDate=" + getCreatedDate()
                + '}';
    }

    private DateTime getDateTimeNullSafe(Date date) {
        if (date == null) {
            return null;
        } else {
            return new DateTime(date);
        }
    }

}
