package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface SignOff {

  String getName();

  void setName(String name);

  Boolean getPassed();

  void setPassed(Boolean passed);

  LocalDate getDate();

  void setDate(LocalDate date);

  Integer getUserId();

  void setUserId(Integer userId);
}
