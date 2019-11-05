package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Box;
import ca.on.oicr.pinery.api.BoxPosition;
import java.util.Set;

public class DefaultBox implements Box {

  private Long id;
  private String name;
  private String description;
  private String location;
  private Integer rows;
  private Integer columns;
  private Set<BoxPosition> positions;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String getLocation() {
    return location;
  }

  @Override
  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public Integer getRows() {
    return rows;
  }

  @Override
  public void setRows(Integer rows) {
    this.rows = rows;
  }

  @Override
  public Integer getColumns() {
    return columns;
  }

  @Override
  public void setColumns(Integer columns) {
    this.columns = columns;
  }

  @Override
  public Set<BoxPosition> getPositions() {
    return positions;
  }

  @Override
  public void setPositions(Set<BoxPosition> positions) {
    this.positions = positions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((columns == null) ? 0 : columns.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((rows == null) ? 0 : rows.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultBox other = (DefaultBox) obj;
    if (columns == null) {
      if (other.columns != null) return false;
    } else if (!columns.equals(other.columns)) return false;
    if (description == null) {
      if (other.description != null) return false;
    } else if (!description.equals(other.description)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (location == null) {
      if (other.location != null) return false;
    } else if (!location.equals(other.location)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (rows == null) {
      if (other.rows != null) return false;
    } else if (!rows.equals(other.rows)) return false;
    return true;
  }
}
