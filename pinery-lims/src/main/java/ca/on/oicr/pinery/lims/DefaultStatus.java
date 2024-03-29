package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Status;
import java.time.LocalDate;

public class DefaultStatus implements Status {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((note == null) ? 0 : note.hashCode());
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultStatus other = (DefaultStatus) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (state == null) {
      if (other.state != null) return false;
    } else if (!state.equals(other.state)) return false;
    if (note == null) {
      if (other.note != null) return false;
    } else if (!note.equals(other.note)) return false;
    if (date == null) {
      if (other.date != null) return false;
    } else if (!date.equals(other.date)) return false;
    if (userId == null) {
      if (other.userId != null) return false;
    } else if (!userId.equals(other.userId)) return false;
    return true;
  }

  private String name;
  private String state;
  private String note;
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
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getNote() {
    return note;
  }

  @Override
  public void setNote(String note) {
    this.note = note;
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
}
