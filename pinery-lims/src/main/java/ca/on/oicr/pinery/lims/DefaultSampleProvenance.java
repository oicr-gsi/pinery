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
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.hash.Hashing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author mlaszloffy
 */
public class DefaultSampleProvenance implements SampleProvenance {

    private final Logger log = LogManager.getLogger(DefaultSampleProvenance.class);

    private Instrument instrument;
    private InstrumentModel instrumentModel;
    private Run sequencerRun;
    private RunPosition lane;
    private RunSample runSample;
    private Sample sample;
    private SampleProject sampleProject;
    private Collection<Sample> parentSamples;
    private Map<SampleAttribute, Set<String>> additionalSampleAttributes = Collections.EMPTY_MAP;

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

    public void setAdditionalSampleAttributes(Map<SampleAttribute, Set<String>> additionalSampleAttributes) {
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
        SortedSetMultimap attrs = TreeMultimap.create();
        //sampleProject.getAttributes();
        return (SortedMap<String, SortedSet<String>>) Multimaps.asMap(attrs);
    }

    @Override
    public String getRootSampleName() {
        if (parentSamples == null || parentSamples.isEmpty()) {
            return null;
        } else {
            return Iterables.getLast(parentSamples).getName();
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
            return Joiner.on(":").skipNulls().join(parentSampleNames);
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
        //use SampleAttribute toString as "key" value
        return getSampleAttributes(true);
    }

    private SortedMap<String, SortedSet<String>> getSampleAttributes(boolean useEnumToString) {

        //collect all parent sample attributes
        SortedSetMultimap<String, String> attrsAll = TreeMultimap.create();
        if (parentSamples != null) {

            //parentSamples is ordered - reserse the order to get ancestors
            for (Sample parentSample : Lists.reverse(Lists.newArrayList(parentSamples))) {
                for (Entry<String, Collection<String>> e : processSampleAttributes(parentSample).asMap().entrySet()) {
                    attrsAll.replaceValues(e.getKey(), e.getValue());
                }
            }
        }

        //collect all sample attributes
        for (Entry<String, Collection<String>> e : processSampleAttributes(sample).asMap().entrySet()) {
            attrsAll.replaceValues(e.getKey(), e.getValue());
        }

        //collect all run sample attributes
        for (Entry<String, Collection<String>> e : processSampleAttributes(runSample).asMap().entrySet()) {
            attrsAll.replaceValues(e.getKey(), e.getValue());
        }

        //collect additional sample fields as attributes
        if (sequencerRun.getId() != null && lane.getPosition() != null) {
            attrsAll.put("run_id_and_position", sequencerRun.getId() + "_" + lane.getPosition());
        }
        if (sample.getSampleType() != null) {
            attrsAll.put(SampleAttribute.SAMPLE_TYPE.toString(), sample.getSampleType());
        }

        //add additional sample attributes
        for (Entry<SampleAttribute, Set<String>> e : additionalSampleAttributes.entrySet()) {
            attrsAll.putAll(e.getKey().toString(), e.getValue());
        }

        //remap sample attribute key names and filter (or allow) unknown attributes
        SortedSetMultimap<String, String> attrsRemappedAndFiltered = TreeMultimap.create();
        for (String key : attrsAll.keySet()) {
            SampleAttribute sampleAttributeKey = SampleAttribute.fromString(key);
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
                log.debug("Unknown sample attribute key = [{}], sample provenance id = [{}]", key, getSampleProvenanceId());
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
        SortedSetMultimap attrs = TreeMultimap.create();
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
        return false;
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
        sb.append(getSampleAttributes(false)); //use SampleAttribute "key" Enum name value
        sb.append(getSequencerRunName());
        sb.append(getSequencerRunAttributes());
        sb.append(getSequencerRunPlatformModel());
        sb.append(getLaneNumber());
        sb.append(getLaneAttributes());
        sb.append(getIusTag());
        String s = sb.toString();
        return Hashing.sha256().hashString(s, Charsets.UTF_8).toString();
//        return Versioning.getSha256(this);
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
            //
        }
        if (runSample != null) {
            lastModified = ObjectUtils.max(lastModified,
                    getDateTimeNullSafe(runSample.getCreated()),
                    getDateTimeNullSafe(runSample.getModified()));
        }
        if (sample != null) {
            lastModified = ObjectUtils.max(lastModified,
                    getDateTimeNullSafe(sample.getCreated()),
                    getDateTimeNullSafe(sample.getModified()));
        }
        if (parentSamples != null) {
            for (Sample parentSample : parentSamples) {
                lastModified = ObjectUtils.max(lastModified,
                        getDateTimeNullSafe(parentSample.getCreated()),
                        getDateTimeNullSafe(parentSample.getModified()));
            }
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
                + "studyTitle=" + getStudyTitle() + ", "
                + "studyAttributes=" + getStudyAttributes() + ", "
                + "rootSampleName=" + getRootSampleName() + ", "
                + "parentSampleName=" + getParentSampleName() + ", "
                + "sampleName=" + getSampleName() + ", "
                + "sampleAttributes=" + getSampleAttributes() + ", "
                + "sequencerRunName=" + getSequencerRunName() + ", "
                + "sequencerRunAttributes=" + getSequencerRunAttributes() + ", "
                + "sequencerRunPlatformModel=" + getSequencerRunPlatformModel() + ", "
                + "laneNumber=" + getLaneNumber() + ", "
                + "laneAttributes=" + getLaneAttributes() + ", "
                + "iusTag=" + getIusTag() + ", "
                + "skip=" + getSkip() + ", "
                + "sampleProvenanceId=" + getSampleProvenanceId() + ", "
                + "version=" + getVersion() + ", "
                + "lastModified=" + getLastModified() + ", "
                + "createdDate=" + getCreatedDate()
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

        //Geospiza sample type is gDNA - recategorize the "Tissue Type" to the correct attribute name of "Tissue Preparation"
        if (Arrays.asList("gDNA", "gDNA_wga").contains(sample.getSampleType())
                && (attrs.containsKey(SampleAttribute.TISSUE_TYPE.toString()) || attrs.containsKey("Tissue Type"))) {
            attrs.putAll("Tissue Preparation", attrs.removeAll(SampleAttribute.TISSUE_TYPE.toString()));
            attrs.putAll("Tissue Preparation", attrs.removeAll("Tissue Type"));
        }

        return attrs;
    }

    private DateTime getDateTimeNullSafe(Date date) {
        if (date == null) {
            return null;
        } else {
            return new DateTime(date);
        }
    }

}
