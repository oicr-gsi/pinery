package ca.on.oicr.pinery.lims;

import java.time.LocalDate;

import ca.on.oicr.pinery.api.RequisitionPause;

public class DefaultRequisitionPause implements RequisitionPause {

  private LocalDate startDate;
  private LocalDate endDate;
  private String reason;

  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Override
  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Override
  public String getReason() {
    return reason;
  }

  @Override
  public void setReason(String reason) {
    this.reason = reason;
  }

}
