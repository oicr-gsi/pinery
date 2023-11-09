package ca.on.oicr.pinery.api;

import java.time.LocalDate;

public interface RequisitionPause {

  LocalDate getStartDate();

  void setStartDate(LocalDate startDate);

  LocalDate getEndDate();

  void setEndDate(LocalDate endDate);

  String getReason();

  void setReason(String reason);

}
