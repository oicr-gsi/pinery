package ca.on.oicr.ws.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequisitionPauseDto {

  private String startDate;
  private String endDate;
  private String reason;

  @JsonProperty("start_date")
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @JsonProperty("end_date")
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate, reason);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RequisitionPauseDto other = (RequisitionPauseDto) obj;
    return Objects.equals(startDate, other.startDate)
        && Objects.equals(endDate, other.endDate)
        && Objects.equals(reason, other.reason);
  }

}
