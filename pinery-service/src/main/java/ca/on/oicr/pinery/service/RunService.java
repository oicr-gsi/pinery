package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Run;
import java.util.List;
import java.util.Set;

public interface RunService {

  public List<Run> getAll(Set<String> sampleIds);

  public Run getRun(Integer id);

  public Run getRun(String runName);
}
