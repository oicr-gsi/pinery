package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface Status {

  public String getName();

  public void setName(String name);

  public String getState();

  public void setState(String state);

  public LocalDate getDate();

  public void setDate(LocalDate date);

  public Integer getUserId();

  public void setUserId(Integer userId);
}
