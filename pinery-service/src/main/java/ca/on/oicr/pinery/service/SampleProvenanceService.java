package ca.on.oicr.pinery.service;

import ca.on.oicr.gsi.provenance.model.SampleProvenance;
import java.util.List;

/**
 *
 * @author mlaszloffy
 */
public interface SampleProvenanceService {
    
    public List<SampleProvenance> getSampleProvenance();
    
}
