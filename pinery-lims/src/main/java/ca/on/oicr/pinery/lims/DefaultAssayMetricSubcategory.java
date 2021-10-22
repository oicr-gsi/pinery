package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.AssayMetricSubcategory;
import java.util.Objects;

public class DefaultAssayMetricSubcategory implements AssayMetricSubcategory {

  private String name;
  private Integer sortPriority;
  private String designCode;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Integer getSortPriority() {
    return sortPriority;
  }

  @Override
  public void setSortPriority(Integer sortPriority) {
    this.sortPriority = sortPriority;
  }

  @Override
  public String getDesignCode() {
    return designCode;
  }

  @Override
  public void setDesignCode(String designCode) {
    this.designCode = designCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(designCode, name, sortPriority);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultAssayMetricSubcategory other = (DefaultAssayMetricSubcategory) obj;
    return Objects.equals(designCode, other.designCode)
        && Objects.equals(name, other.name)
        && Objects.equals(sortPriority, other.sortPriority);
  }
}
