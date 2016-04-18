package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.lims.DefaultSampleProvenance;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mlaszloffy
 */
@Service
public class DefaultSampleProvenanceService implements SampleProvenanceService {

    Logger log = LoggerFactory.getLogger(DefaultSampleProvenanceService.class);

    private Lims lims;
    
    @Autowired
    public DefaultSampleProvenanceService(Lims lims){
        this.lims = lims;
    }

    @Override
    public List<SampleProvenance> getSampleProvenance() {
        Map<String, SampleProject> projectByName = new HashMap<>();
        for (SampleProject sampleProject : lims.getSampleProjects()) {
            if (projectByName.put(sampleProject.getName(), sampleProject) != null) {
                log.warn("Duplicate SampleProject name: " + sampleProject.getName());
            }
        }

        Map<Integer, Instrument> instrumentById = new HashMap<>();
        for (Instrument instrument : lims.getInstruments()) {
            if (instrumentById.put(instrument.getId(), instrument) != null) {
                log.warn("Duplicate Instrument id: " + instrument.getId());
            }
        }

        Map<Integer, InstrumentModel> instrumentModelById = new HashMap<>();
        for (InstrumentModel instrumentModel : lims.getInstrumentModels()) {
            if (instrumentModelById.put(instrumentModel.getId(), instrumentModel) != null) {
                log.warn("Duplicate Instrument Model id: " + instrumentModel.getId());
            }
        }

        Multimap<String, OrderSample> orderSampleById = HashMultimap.create();
        for (Order order : lims.getOrders()) {
            for (OrderSample orderSample : order.getSamples()) {
                orderSampleById.put(orderSample.getId(), orderSample);
            }
        }

        List<Sample> samples = lims.getSamples(null, null, null, null, null);
        Map<String, Sample> samplesById = new HashMap<>();
        for (Sample sample : samples) {
            if (samplesById.put(sample.getId(), sample) != null) {
                log.warn("Duplicate Sample id: " + sample.getId());
            }
        }

        SampleHierarchy sampleHierarchy = new SampleHierarchy(samples);

        List<SampleProvenance> sps = new ArrayList<>();

        //iterate over all sequencer runs -> lanes -> samples to build sample provenance
        for (Run sequencerRun : lims.getRuns()) {

            Instrument instrument = instrumentById.get(sequencerRun.getInstrumentId());
            InstrumentModel instrumentModel = instrument == null ? null : instrumentModelById.get(instrument.getModelId());

            for (RunPosition lane : sequencerRun.getSamples()) {

                for (RunSample runSample : lane.getRunSample()) {

                    Sample sample = samplesById.get(runSample.getId());

                    DefaultSampleProvenance sp = new DefaultSampleProvenance();
                    sp.setSample(sample);
                    sp.setRunSample(runSample);
                    sp.setLane(lane);
                    sp.setSequencerRun(sequencerRun);
                    sp.setInstrument(instrument);
                    sp.setInstrumentModel(instrumentModel);
                    sp.setSampleProject(projectByName.get(sample.getProject()));

                    Set<Sample> parentSamples = new LinkedHashSet<>();
                    for (String id : sampleHierarchy.getAncestorSampleIds(sample.getId())) {
                        parentSamples.add(samplesById.get(id));
                    }
                    sp.setParentSamples(parentSamples);

                    sps.add(sp);
                }
            }
        }

        return sps;
    }

    private class SampleHierarchy {

        private final Multimap<String, String> sampleParents;

        public SampleHierarchy(List<Sample> samples) {
            sampleParents = HashMultimap.create();
            for (Sample sample : samples) {
                Set<String> parentIds = sample.getParents();
                if (parentIds == null) {
                    parentIds = Collections.EMPTY_SET;
                }
                sampleParents.putAll(sample.getId(), parentIds);
            }
        }

        private Set<String> getAncestorSampleIds(String id) {
            Set<String> ancestors = new LinkedHashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.addAll(sampleParents.get(id));
            while (!queue.isEmpty()) {
                String parent = queue.remove();
                ancestors.add(parent);
                queue.addAll(sampleParents.get(parent));
            }
            return ancestors;
        }
    }
}
