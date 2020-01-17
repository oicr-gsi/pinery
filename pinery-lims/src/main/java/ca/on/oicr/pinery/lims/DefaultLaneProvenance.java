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
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.stream.Stream;

/** @author mlaszloffy */
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
      attrs.put(LimsSequencerRunAttribute.INSTRUMENT_NAME.getKey(), instrument.getName());
    }
    if (sequencerRun != null) {
      if (sequencerRun.getRunDirectory() != null && !sequencerRun.getRunDirectory().isEmpty()) {
        attrs.put(LimsSequencerRunAttribute.RUN_DIRECTORY.getKey(), sequencerRun.getRunDirectory());
      }
      if (sequencerRun.getRunBasesMask() != null && !sequencerRun.getRunBasesMask().isEmpty()) {
        attrs.put(
            LimsSequencerRunAttribute.RUN_BASES_MASK.getKey(), sequencerRun.getRunBasesMask());
      }
      if (sequencerRun.getSequencingParameters() != null) {
        attrs.put(
            LimsSequencerRunAttribute.SEQUENCING_PARAMETERS.getKey(),
            sequencerRun.getSequencingParameters());
      }
      if (sequencerRun.getWorkflowType() != null) {
        attrs.put(LimsSequencerRunAttribute.WORKFLOW_TYPE.getKey(), sequencerRun.getWorkflowType());
      }
      if (sequencerRun.getContainerModel() != null) {
        attrs.put(
            LimsSequencerRunAttribute.CONTAINER_MODEL.getKey(), sequencerRun.getContainerModel());
      }
      if (sequencerRun.getSequencingKit() != null) {
        attrs.put(
            LimsSequencerRunAttribute.SEQUENCING_KIT.getKey(), sequencerRun.getSequencingKit());
      }
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
    SortedSetMultimap<String, String> attrs = TreeMultimap.create();

    if (lane.getPoolName() != null && !lane.getPoolName().isEmpty()) {
      attrs.put(LimsLaneAttribute.POOL_NAME.getKey(), lane.getPoolName());
    }

    if (lane.getQcStatus() != null && !lane.getQcStatus().isEmpty()) {
      attrs.put(LimsLaneAttribute.QC_STATUS.getKey(), lane.getQcStatus());
    }

    if (lane.getRunPurpose() != null && !lane.getRunPurpose().isEmpty()) {
      attrs.put(LimsLaneAttribute.RUN_PURPOSE.getKey(), lane.getRunPurpose());
    }

    return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrs);
  }

  @Override
  public Boolean getSkip() {
    return lane.isAnalysisSkipped();
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
  public ZonedDateTime getLastModified() {
    return Util.optionalDateToZDT(
        Stream.of(
                sequencerRun.getCreatedDate(),
                sequencerRun.getCompletionDate(),
                sequencerRun.getModified(),
                lane == null ? null : lane.getPoolCreated(),
                lane == null ? null : lane.getPoolModified())
            .filter(Objects::nonNull)
            .max(Date::compareTo));
  }

  @Override
  public ZonedDateTime getCreatedDate() {
    // completion date is used as this is the first date that this provenance object is ready for
    // processing
    return Util.optionalDateToZDT(
        Stream.of(sequencerRun.getCompletionDate()).filter(Objects::nonNull).min(Date::compareTo));
  }

  @Override
  public String toString() {
    return "SampleProvenance{"
        + "sequencerRunName="
        + getSequencerRunName()
        + ", "
        + "sequencerRunAttributes="
        + getSequencerRunAttributes()
        + ", "
        + "sequencerRunPlatformModel="
        + getSequencerRunPlatformModel()
        + ", "
        + "laneNumber="
        + getLaneNumber()
        + ", "
        + "laneAttributes="
        + getLaneAttributes()
        + ", "
        + "skip="
        + getSkip()
        + ", "
        + "laneProvenanceId="
        + getLaneProvenanceId()
        + ", "
        + "version="
        + getVersion()
        + ", "
        + "lastModified="
        + getLastModified()
        + ", "
        + "createdDate="
        + getCreatedDate()
        + '}';
  }
}
