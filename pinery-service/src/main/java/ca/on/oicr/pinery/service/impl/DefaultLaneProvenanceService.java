package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.LaneProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.service.LaneProvenanceService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author mlaszloffy */
@Service
public class DefaultLaneProvenanceService implements LaneProvenanceService {

  Logger log = LoggerFactory.getLogger(DefaultLaneProvenanceService.class);

  @Autowired private CacheOrLims lims;

  @Override
  public List<LaneProvenance> getLaneProvenance() {
    if (lims.isCacheEnabled()) {
      return lims.getCachedLaneProvenance();
    } else {
      List<Instrument> instruments = lims.getInstruments();
      List<InstrumentModel> instrumentModels = lims.getInstrumentModels();
      List<Run> runs = lims.getRuns();
      return ProvenanceUtils.buildLaneProvenance(instruments, instrumentModels, runs);
    }
  }
}
