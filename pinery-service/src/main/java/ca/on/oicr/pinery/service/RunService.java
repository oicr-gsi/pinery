package ca.on.oicr.pinery.service;

import java.util.List;

import ca.on.oicr.pinery.api.Run;

public interface RunService {

   public List<Run> getRun();

   public Run getRun(Integer id);
   
   public Run getRun(String runName);
   
}
