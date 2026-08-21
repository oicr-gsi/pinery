package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Qc;
import java.time.LocalDate;
import java.util.Objects;

public class DefaultQc implements Qc {

  private String name;
  private LocalDate date;
  private String result;
  private String units;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
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
  public String getResult() {
    return result;
  }

  @Override
  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public String getUnits() {
    return units;
  }

  @Override
  public void setUnits(String units) {
    this.units = units;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, name, result, units);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultQc other = (DefaultQc) obj;
    return Objects.equals(date, other.date)
        && Objects.equals(name, other.name)
        && Objects.equals(result, other.result)
        && Objects.equals(units, other.units);
  }
}
