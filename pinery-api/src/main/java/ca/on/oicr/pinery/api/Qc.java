package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface Qc {

  String getName();

  void setName(String name);

  LocalDate getDate();

  void setDate(LocalDate date);

  String getResult();

  void setResult(String result);

  String getUnits();

  void setUnits(String units);
}
