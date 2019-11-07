package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Attribute;
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
import ca.on.oicr.pinery.lims.LimsSampleAttribute;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import ca.on.oicr.pinery.service.util.LimsProvenanceComparator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
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

/** @author mlaszloffy */
@Service
public class DefaultSampleProvenanceService implements SampleProvenanceService {

  Logger log = LoggerFactory.getLogger(DefaultSampleProvenanceService.class);

  private final Lims lims;

  @Autowired
  public DefaultSampleProvenanceService(Lims lims) {
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

    // See GP-960 - get sample targeted resequencing from orders
    Map<String, String> sampleTargetedRequencingTypeFromSampleIdAndBarcode = new HashMap<>();
    for (Order order : lims.getOrders()) {
      if (order.getSamples() != null) {
        for (OrderSample orderSample : order.getSamples()) {
          for (Attribute attr : orderSample.getAttributes()) {
            LimsSampleAttribute sa = LimsSampleAttribute.fromString(attr.getName());
            if (LimsSampleAttribute.TARGETED_RESEQUENCING == sa) {
              String key =
                  orderSample.getId() + orderSample.getBarcode() + orderSample.getBarcodeTwo();
              if (sampleTargetedRequencingTypeFromSampleIdAndBarcode.containsKey(key)) {
                if (sampleTargetedRequencingTypeFromSampleIdAndBarcode
                    .get(key)
                    .equals(attr.getValue())) {
                  // current and new target resequencing match
                } else {
                  // unable to determine the targeted resequencing type for the sample
                  sampleTargetedRequencingTypeFromSampleIdAndBarcode.put(
                      key, "TARGETED RESEQUENCING CONFLICT DETECTED");
                  log.warn(
                      "{} conflict detected for sample id: {}",
                      LimsSampleAttribute.TARGETED_RESEQUENCING.name(),
                      orderSample.getId());
                }
              } else {
                sampleTargetedRequencingTypeFromSampleIdAndBarcode.put(key, attr.getValue());
              }
            }
          }
        }
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

    // iterate over all sequencer runs -> lanes -> samples to build sample provenance
    for (Run sequencerRun : lims.getRuns()) {

      Instrument instrument = instrumentById.get(sequencerRun.getInstrumentId());
      InstrumentModel instrumentModel =
          instrument == null ? null : instrumentModelById.get(instrument.getModelId());

      if (sequencerRun.getSamples() != null) {
        for (RunPosition lane : sequencerRun.getSamples()) {
          if (lane.getRunSample() != null) {
            for (RunSample runSample : lane.getRunSample()) {

              Sample sample = samplesById.get(runSample.getId());
              if (sample != null) {
                DefaultSampleProvenance sp = new DefaultSampleProvenance();
                sp.setSample(sample);
                sp.setRunSample(runSample);
                sp.setLane(lane);
                sp.setSequencerRun(sequencerRun);
                sp.setInstrument(instrument);
                sp.setInstrumentModel(instrumentModel);
                sp.setSampleProject(projectByName.get(sample.getProject()));

                // special handling of target resequencing type, as this attribute is currently not
                // stored in sample or run sample attrs
                String targetedResequencing =
                    sampleTargetedRequencingTypeFromSampleIdAndBarcode.get(
                        runSample.getId() + runSample.getBarcode() + runSample.getBarcodeTwo());
                if (targetedResequencing != null && !"No Target".equals(targetedResequencing)) {
                  sp.setAdditionalSampleAttributes(
                      ImmutableMap.<LimsSampleAttribute, Set<String>>of(
                          LimsSampleAttribute.TARGETED_RESEQUENCING,
                          ImmutableSet.of(targetedResequencing)));
                }

                LinkedHashSet<Sample> parentSamples = new LinkedHashSet<>();
                for (String id : sampleHierarchy.getAncestorSampleIds(sample.getId())) {
                  parentSamples.add(samplesById.get(id));
                }
                sp.setParentSamples(parentSamples);

                sps.add(sp);
              }
            }
          }
        }
      }
    }

    Collections.sort(sps, new LimsProvenanceComparator());
    return sps;
  }

  private class SampleHierarchy {

    private final Multimap<String, String> sampleParents;

    public SampleHierarchy(List<Sample> samples) {
      sampleParents = HashMultimap.create();
      for (Sample sample : samples) {
        Set<String> parentIds = sample.getParents();
        if (parentIds == null) {
          parentIds = Collections.emptySet();
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
