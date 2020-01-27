package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.service.SampleProvenanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @author mlaszloffy */
@Service
public class DefaultSampleProvenanceService implements SampleProvenanceService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<SampleProvenance> getSampleProvenance() {
    if (lims.isCacheEnabled()) {
      return lims.getCachedSampleProvenance();
    } else {
      List<SampleProject> projects = lims.getSampleProjects();
      List<Instrument> instruments = lims.getInstruments();
      List<InstrumentModel> instrumentModels = lims.getInstrumentModels();
      List<Order> orders = lims.getOrders();
      List<Sample> samples = lims.getSamples(null, null, null, null, null);
      List<Run> runs = lims.getRuns();
      return ProvenanceUtils.buildSampleProvenance(
          projects, instruments, instrumentModels, orders, samples, runs);
    }
  }
}
