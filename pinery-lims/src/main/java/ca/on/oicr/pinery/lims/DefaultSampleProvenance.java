package ca.on.oicr.pinery.lims;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author mlaszloffy */
public class DefaultSampleProvenance implements SampleProvenance {

  private final Logger log = LoggerFactory.getLogger(DefaultSampleProvenance.class);

  private Instrument instrument;
  private InstrumentModel instrumentModel;
  private Run sequencerRun;
  private RunPosition lane;
  private RunSample runSample;
  private Sample sample;
  private SampleProject sampleProject;
  private Collection<Sample> parentSamples;
  private Map<LimsSampleAttribute, Set<String>> additionalSampleAttributes = Collections.emptyMap();

  private final boolean ALLOW_UNKNOWN_ATTRIBUTES = false;

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  public void setInstrumentModel(InstrumentModel instrumentModel) {
    this.instrumentModel = instrumentModel;
  }

  public void setParentSamples(Collection<Sample> parentSamples) {
    this.parentSamples = parentSamples;
  }

  public void setSequencerRun(Run sequencerRun) {
    this.sequencerRun = sequencerRun;
  }

  public void setRunSample(RunSample runSample) {
    this.runSample = runSample;
  }

  public void setLane(RunPosition lane) {
    this.lane = lane;
  }

  public void setSample(Sample sample) {
    this.sample = sample;
  }

  public void setSampleProject(SampleProject sampleProject) {
    this.sampleProject = sampleProject;
  }

  public void setAdditionalSampleAttributes(
      Map<LimsSampleAttribute, Set<String>> additionalSampleAttributes) {
    this.additionalSampleAttributes = additionalSampleAttributes;
  }

  @Override
  public String getStudyTitle() {
    if (sampleProject == null) {
      return null;
    } else {
      return sampleProject.getName();
    }
  }

  @Override
  public SortedMap<String, SortedSet<String>> getStudyAttributes() {
    SortedSetMultimap<String, String> attrs = TreeMultimap.create();
    return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrs);
  }

  @Override
  public String getRootSampleName() {
    if (parentSamples == null || parentSamples.isEmpty()) {
      return null;
    } else {
      return parentSamples.stream().reduce((first, second) -> second).get().getName();
    }
  }

  @Override
  public String getParentSampleName() {
    if (parentSamples == null || parentSamples.isEmpty()) {
      return null;
    } else {
      List<String> parentSampleNames = new ArrayList<>();
      for (Sample s : parentSamples) {
        parentSampleNames.add(s.getName());
      }
      return parentSampleNames.stream().filter(Objects::nonNull).collect(Collectors.joining(":"));
    }
  }

  @Override
  public String getSampleName() {
    if (sample == null) {
      return null;
    } else {
      return sample.getName();
    }
  }

  @Override
  public SortedMap<String, SortedSet<String>> getSampleAttributes() {
    // use LimsAttribute toString as "key" value
    return getSampleAttributes(true);
  }

  private SortedMap<String, SortedSet<String>> getSampleAttributes(boolean useEnumToString) {

    // collect all parent sample attributes
    SortedSetMultimap<String, String> attrsAll = TreeMultimap.create();
    if (parentSamples != null) {

      // parentSamples is ordered - reverse the order to get ancestors
      final List<Sample> reversedParents = new ArrayList<>(parentSamples);
      Collections.reverse(reversedParents);
      for (Sample parentSample : reversedParents) {
        for (Entry<String, Collection<String>> e : processSampleAttributes(parentSample).asMap().entrySet()) {
          attrsAll.replaceValues(e.getKey(), e.getValue());
        }
      }
    }

    // collect all sample attributes
    for (Entry<String, Collection<String>> e : processSampleAttributes(sample).asMap().entrySet()) {
      attrsAll.replaceValues(e.getKey(), e.getValue());
    }

    // collect all run sample attributes
    for (Entry<String, Collection<String>> e : processSampleAttributes(runSample).asMap().entrySet()) {
      attrsAll.replaceValues(e.getKey(), e.getValue());
    }

    // collect additional sample fields as attributes
    if (sequencerRun.getId() != null && lane.getPosition() != null) {
      attrsAll.put(
          LimsSampleAttribute.RUN_ID_AND_POSITION.toString(),
          sequencerRun.getId() + "_" + lane.getPosition());
    }
    if (runSample.getRunPurpose() != null && !runSample.getRunPurpose().isEmpty()) {
      attrsAll.put(LimsSampleAttribute.RUN_PURPOSE.toString(), runSample.getRunPurpose());
    } else if (lane.getRunPurpose() != null && !lane.getRunPurpose().isEmpty()) {
      attrsAll.put(LimsSampleAttribute.RUN_PURPOSE.toString(), lane.getRunPurpose());
    }

    if (sample.getSampleType() != null) {
      attrsAll.put(LimsSampleAttribute.SAMPLE_TYPE.toString(), sample.getSampleType());
    }

    // add additional sample attributes
    for (Entry<LimsSampleAttribute, Set<String>> e : additionalSampleAttributes.entrySet()) {
      attrsAll.putAll(e.getKey().toString(), e.getValue());
    }

    // remap sample attribute key names and filter (or allow) unknown attributes
    SortedSetMultimap<String, String> attrsRemappedAndFiltered = TreeMultimap.create();
    for (String key : attrsAll.keySet()) {
      LimsSampleAttribute sampleAttributeKey = LimsSampleAttribute.fromString(key);
      SortedSet<String> values = attrsAll.get(key);
      if (sampleAttributeKey != null) {
        // sample attribute key is known
        if (useEnumToString) {
          attrsRemappedAndFiltered.putAll(sampleAttributeKey.toString(), values);
        } else {
          attrsRemappedAndFiltered.putAll(sampleAttributeKey.name(), values);
        }
      } else if (ALLOW_UNKNOWN_ATTRIBUTES) {
        // sample attribute key is unknown and allowed
        attrsRemappedAndFiltered.putAll(key, values);
      } else {
        // sample attribute key is unknown and not allowed
        log.debug(
            "Unknown sample attribute key = [{}], sample provenance id = [{}]",
            key,
            getSampleProvenanceId());
      }
    }

    return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrsRemappedAndFiltered);
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
  public String getIusTag() {
    if (runSample == null) {
      return null;
    }

    if (runSample.getBarcode() == null || runSample.getBarcode().isEmpty()) {
      return "NoIndex";
    }

    if (runSample.getBarcodeTwo() == null || runSample.getBarcodeTwo().isEmpty()) {
      return runSample.getBarcode();
    } else {
      return runSample.getBarcode() + "-" + runSample.getBarcodeTwo();
    }
  }

  @Override
  public Boolean getSkip() {
    if ((sequencerRun.getStatus() == null
        || "Not Ready".equals(sequencerRun.getStatus().getState()))
        && lane.isAnalysisSkipped() == null
        && (runSample.getStatus() == null
            || "Not Ready".equals(runSample.getStatus().getState()))) {
      return null;
    } else {
      return (sequencerRun.getStatus() != null
          && "Failed".equals(sequencerRun.getStatus().getState()))
          || Boolean.TRUE.equals(lane.isAnalysisSkipped())
          || (runSample.getStatus() != null && "Failed".equals(runSample.getStatus().getState()))
          || isConsentRevoked();
    }
  }

  private boolean isConsentRevoked() {
    if (isConsentRevoked(sample)) {
      return true;
    }
    return parentSamples.stream().anyMatch(parent -> isConsentRevoked(parent));
  }

  private static boolean isConsentRevoked(Sample sample) {
    if (sample.getAttributes() == null) {
      return false;
    }
    return sample.getAttributes().stream()
        .filter(attr -> "Consent".equals(attr.getName()))
        .anyMatch(attr -> "REVOKED".equals(attr.getValue()));
  }

  @Override
  public String getSampleProvenanceId() {
    return sequencerRun.getId() + "_" + lane.getPosition() + "_" + sample.getId();
  }

  @Override
  public String getProvenanceId() {
    return getSampleProvenanceId();
  }

  @Override
  public String getVersion() {
    StringBuilder sb = new StringBuilder();
    sb.append(getStudyTitle());
    sb.append(getStudyAttributes());
    sb.append(getRootSampleName());
    sb.append(getParentSampleName());
    sb.append(getSampleName());
    sb.append(getSampleAttributes(false)); // use LimsAttribute "key" Enum name value
    sb.append(getSequencerRunName());
    sb.append(getSequencerRunAttributes());
    sb.append(getSequencerRunPlatformModel());
    sb.append(getLaneNumber());
    sb.append(getLaneAttributes());
    sb.append(getIusTag());
    sb.append(getBatchIds());
    String s = sb.toString();
    return Hashing.sha256().hashString(s, StandardCharsets.UTF_8).toString();
  }

  @Override
  public ZonedDateTime getLastModified() {
    return Util.optionalDateToZDT(
        Stream.concat(
            Stream.of(
                sequencerRun == null ? null : sequencerRun.getCreatedDate(),
                sequencerRun == null ? null : sequencerRun.getCompletionDate(),
                sequencerRun == null ? null : sequencerRun.getModified(),
                runSample == null ? null : runSample.getCreated(),
                runSample == null ? null : runSample.getModified(),
                sample == null ? null : sample.getCreated(),
                sample == null ? null : sample.getModified(),
                lane == null ? null : lane.getPoolCreated(),
                lane == null ? null : lane.getPoolModified()),
            parentSamples == null
                ? Stream.empty()
                : parentSamples.stream()
                    .flatMap(p -> Stream.of(p.getCreated(), p.getModified())))
            .filter(Objects::nonNull)
            .max(Date::compareTo));
  }

  @Override
  public ZonedDateTime getCreatedDate() {
    return Util.optionalDateToZDT(
        Stream.of(sequencerRun.getCompletionDate()).filter(Objects::nonNull).min(Date::compareTo));
  }

  @Override
  public String toString() {
    return "SampleProvenance{"
        + "studyTitle="
        + getStudyTitle()
        + ", "
        + "studyAttributes="
        + getStudyAttributes()
        + ", "
        + "rootSampleName="
        + getRootSampleName()
        + ", "
        + "parentSampleName="
        + getParentSampleName()
        + ", "
        + "sampleName="
        + getSampleName()
        + ", "
        + "sampleAttributes="
        + getSampleAttributes()
        + ", "
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
        + "iusTag="
        + getIusTag()
        + ", "
        + "skip="
        + getSkip()
        + ", "
        + "sampleProvenanceId="
        + getSampleProvenanceId()
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

  private HashMultimap<String, String> processSampleAttributes(Sample sample) {
    HashMultimap<String, String> attrs = HashMultimap.create();

    if (sample.getAttributes() != null) {
      for (Attribute attribute : sample.getAttributes()) {
        attrs.put(attribute.getName(), attribute.getValue());
      }
    }

    if (sample.getPreparationKit() != null) {
      attrs.put("Preparation Kit", sample.getPreparationKit().getName());
    }

    // Geospiza sample type is gDNA - recategorize the "Tissue Type" to the correct
    // attribute name
    // of "Tissue Preparation"
    if (Arrays.asList("gDNA", "gDNA_wga").contains(sample.getSampleType())
        && (attrs.containsKey(LimsSampleAttribute.TISSUE_TYPE.toString())
            || attrs.containsKey("Tissue Type"))) {
      attrs.putAll(
          "Tissue Preparation", attrs.removeAll(LimsSampleAttribute.TISSUE_TYPE.toString()));
      attrs.putAll("Tissue Preparation", attrs.removeAll("Tissue Type"));
    }

    return attrs;
  }

  @Override
  public List<String> getBatchIds() {
    // Should return an empty list if there are none. Null is used to omit the
    // attribue from old
    // provenance versons (see SampleProvenanceDto and SimpleSampleProvenance)
    List<String> batchIds = new ArrayList<>();
    addBatchId(sample, batchIds);
    if (parentSamples != null) {
      for (Sample parent : parentSamples) {
        addBatchId(parent, batchIds);
      }
    }
    return batchIds;
  }

  private static void addBatchId(Sample sample, List<String> list) {
    if (sample.getAttributes() == null) {
      return;
    }
    sample.getAttributes().stream()
        .filter(x -> "Batch ID".equals(x.getName()))
        .sorted(Comparator.comparing(Attribute::getValue))
        .forEach(x -> list.add(x.getValue()));
  }
}
