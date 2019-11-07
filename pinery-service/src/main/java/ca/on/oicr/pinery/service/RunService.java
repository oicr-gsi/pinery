package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.Run;
import java.util.List;

public interface RunService {

  public List<Run> getRun();

  public Run getRun(Integer id);

  public Run getRun(String runName);
}
