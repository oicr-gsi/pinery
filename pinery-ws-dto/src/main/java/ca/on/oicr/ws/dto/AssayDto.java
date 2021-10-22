package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssayDto {

  private Integer id;
  private String name;
  private String description;
  private String version;
  private Set<AssayMetricDto> metrics;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Set<AssayMetricDto> getMetrics() {
    return metrics;
  }

  public void setMetrics(Set<AssayMetricDto> metrics) {
    this.metrics = metrics;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, id, metrics, name, version);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AssayDto other = (AssayDto) obj;
    return Objects.equals(description, other.description)
        && Objects.equals(id, other.id)
        && Objects.equals(metrics, other.metrics)
        && Objects.equals(name, other.name)
        && Objects.equals(version, other.version);
  }
}
