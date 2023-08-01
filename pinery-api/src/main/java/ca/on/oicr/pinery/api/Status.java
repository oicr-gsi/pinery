package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface Status {

  String getName();

  void setName(String name);

  String getState();

  void setState(String state);

  String getNote();

  void setNote(String note);

  LocalDate getDate();

  void setDate(LocalDate date);

  Integer getUserId();

  void setUserId(Integer userId);
}
