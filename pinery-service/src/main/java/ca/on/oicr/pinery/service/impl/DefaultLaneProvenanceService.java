package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.lims.DefaultLaneProvenance;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import ca.on.oicr.pinery.service.util.LimsProvenanceComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class DefaultLaneProvenanceService implements LaneProvenanceService {

    Logger log = LoggerFactory.getLogger(DefaultLaneProvenanceService.class);

    private final Lims lims;

    @Autowired
    public DefaultLaneProvenanceService(Lims lims) {
        this.lims = lims;
    }

    @Override
    public List<LaneProvenance> getLaneProvenance() {
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

        //iterate over all sequencer runs -> lanes to build sample provenance
        List<LaneProvenance> lps = new ArrayList<>();
        List<Run> sequencerRuns = lims.getRuns();
        for (Run sequencerRun : sequencerRuns) {

            Instrument instrument = instrumentById.get(sequencerRun.getInstrumentId());
            InstrumentModel instrumentModel = instrument == null ? null : instrumentModelById.get(instrument.getModelId());

            Set<RunPosition> lanes = sequencerRun.getSamples();
            if (lanes == null || lanes.isEmpty()) {
                log.warn("Run [{}] does not have any lanes", sequencerRun.getName());
            } else {
                for (RunPosition lane : lanes) {
                    DefaultLaneProvenance lp = new DefaultLaneProvenance();
                    lp.setLane(lane);
                    lp.setSequencerRun(sequencerRun);
                    lp.setInstrument(instrument);
                    lp.setInstrumentModel(instrumentModel);
                    lps.add(lp);
                }
            }
        }

        Collections.sort(lps, new LimsProvenanceComparator());
        return lps;
    }
}
