package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssayMetricSubcategoryDto {

  private String name;
  private Integer sortPriority;
  private String designCode;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("sort_priority")
  public Integer getSortPriority() {
    return sortPriority;
  }

  public void setSortPriority(Integer sortPriority) {
    this.sortPriority = sortPriority;
  }

  @JsonProperty("design_code")
  public String getDesignCode() {
    return designCode;
  }

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
    AssayMetricSubcategoryDto other = (AssayMetricSubcategoryDto) obj;
    return Objects.equals(designCode, other.designCode)
        && Objects.equals(name, other.name)
        && Objects.equals(sortPriority, other.sortPriority);
  }
}
