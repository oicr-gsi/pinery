package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.SignOff;
import java.time.LocalDate;
import java.util.Objects;

public class DefaultSignOff implements SignOff {

  private String name;
  private Boolean passed;
  private LocalDate date;
  private Integer userId;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Boolean getPassed() {
    return passed;
  }

  @Override
  public void setPassed(Boolean passed) {
    this.passed = passed;
  }

  @Override
  public LocalDate getDate() {
    return date;
  }

  @Override
  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public Integer getUserId() {
    return userId;
  }

  @Override
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, name, passed, userId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultSignOff other = (DefaultSignOff) obj;
    return Objects.equals(date, other.date)
        && Objects.equals(name, other.name)
        && Objects.equals(passed, other.passed)
        && Objects.equals(userId, other.userId);
  }
}
