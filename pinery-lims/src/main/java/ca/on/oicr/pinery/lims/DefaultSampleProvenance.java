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
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.common.hash.Hashing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author mlaszloffy
 */
public class DefaultSampleProvenance implements SampleProvenance {

    private Instrument instrument;
    private InstrumentModel instrumentModel;
    private Run sequencerRun;
    private RunPosition lane;
    private RunSample runSample;
    private Sample sample;
    private SampleProject sampleProject;
    private Set<Sample> parentSamples;

    private final boolean ALLOW_UNKNOWN_ATTRIBUTES = false;

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public void setInstrumentModel(InstrumentModel instrumentModel) {
        this.instrumentModel = instrumentModel;
    }

    public void setParentSamples(Set<Sample> parentSamples) {
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

    @Override
    public String getStudyTitle() {
        if (sampleProject == null) {
            return null;
        } else {
            return sampleProject.getName();
        }
    }

    @Override
    public Map<String, Set<String>> getStudyAttributes() {
        SetMultimap attrs = TreeMultimap.create();
        //sampleProject.getAttributes();
        return Multimaps.asMap(attrs);
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
            return Joiner.on(":").join(parentSampleNames);
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
    public Map<String, Set<String>> getSampleAttributes() {
        //use SampleAttribute toString as "key" value
        return getSampleAttributes(true);
    }

    private Map<String, Set<String>> getSampleAttributes(boolean useEnumToString) {

        //collect all parent sample attributes
        SetMultimap<String, String> attrsAll = TreeMultimap.create();
        if (parentSamples != null) {

            for (Sample parentSample : parentSamples) {
                attrsAll.putAll(processSampleAttributes(parentSample));

                if (parentSample.getPreparationKit() != null) {
                    attrsAll.put("Preparation Kit", parentSample.getPreparationKit().getName());
                }
            }
        }

        //collect all sample attributes
        attrsAll.putAll(processSampleAttributes(sample));

        //collect all run sample attributes
        attrsAll.putAll(processSampleAttributes(runSample));
        
        //collect additional sample fields as attributes
        if (sequencerRun.getId() != null && lane.getPosition() != null) {
            attrsAll.put("run_id_and_position", sequencerRun.getId() + "_" + lane.getPosition());
        }
        if (sample.getSampleType() != null) {
            attrsAll.put(SampleAttribute.SAMPLE_TYPE.toString(), sample.getSampleType());
        }

        //remap sample attribute key names and filter (or allow) unknown attributes
        SetMultimap<String, String> attrsRemappedAndFiltered = TreeMultimap.create();
        for (String key : attrsAll.keySet()) {
            SampleAttribute sampleAttributeKey = SampleAttribute.fromString(key);
            Set<String> values = attrsAll.get(key);
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
                System.out.println("Key is filtered");
            }
        }

        return Multimaps.asMap(attrsRemappedAndFiltered);
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
    public Map<String, Set<String>> getSequencerRunAttributes() {
        SetMultimap attrs = TreeMultimap.create();
        if (instrument != null) {
            attrs.put("instrument_name", instrument.getName());
        }
        return Multimaps.asMap(attrs);
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
    public Map<String, Set<String>> getLaneAttributes() {
        SetMultimap attrs = TreeMultimap.create();
        //lane.getAttributes();
        return Multimaps.asMap(attrs);
    }

    @Override
    public String getIusTag() {
        if (runSample == null) {
            return null;
        }

        if (runSample.getBarcode() == null) {
            return "NoIndex";
        }

        if (runSample.getBarcodeTwo() == null) {
            return runSample.getBarcode();
        } else {
            return runSample.getBarcode() + "-" + runSample.getBarcodeTwo();
        }
    }

    @Override
    public String getSampleProvenanceId() {
        return sequencerRun.getId() + "_" + lane.getPosition() + "_" + sample.getId();
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
                    getDateTimeNullSafe(sequencerRun.getCreatedDate()));
            //TODO: sequencerRun.getModifiedDate() does not exist
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
                + "sampleProvenanceId=" + getSampleProvenanceId() + ", "
                + "version=" + getVersion() + ", "
                + "lastModified=" + getLastModified()
                + '}';
    }

    private HashMultimap<String, String> processSampleAttributes(Sample sample) {
        HashMultimap<String, String> attrs = HashMultimap.create();

        if (sample == null || sample.getAttributes() == null) {
            return attrs;
        }
        
        for (Attribute attribute : sample.getAttributes()) {
            attrs.put(attribute.getName(), attribute.getValue());
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
