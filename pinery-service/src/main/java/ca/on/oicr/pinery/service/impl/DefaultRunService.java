package ca.on.oicr.pinery.service.impl;

import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.service.RunService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRunService implements RunService {

  @Autowired private CacheOrLims lims;

  @Override
  public List<Run> getAll(Set<String> sampleIds) {
    return lims.getRuns(sampleIds);
  }

  @Override
  public Run getRun(Integer id) {
    return lims.getRun(id);
  }

  @Override
  public Run getRun(String runName) {
    return lims.getRun(runName);
  }
}
