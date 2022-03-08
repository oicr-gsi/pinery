package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Assay;
import ca.on.oicr.pinery.api.AssayMetric;
import ca.on.oicr.pinery.api.AssayTest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultAssay implements Assay {

  private Integer id;
  private String name;
  private String description;
  private String version;
  private Set<AssayTest> tests;
  private Set<AssayMetric> metrics;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
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
  public String getVersion() {
    return version;
  }

  @Override
  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public Set<AssayTest> getTests() {
    return tests;
  }

  @Override
  public void setTests(Set<AssayTest> tests) {
    this.tests = tests;
  }

  @Override
  public void addTest(AssayTest test) {
    if (tests == null) {
      tests = new HashSet<>();
    }
    tests.add(test);
  }

  @Override
  public Set<AssayMetric> getMetrics() {
    return metrics;
  }

  @Override
  public void setMetrics(Set<AssayMetric> metrics) {
    this.metrics = metrics;
  }

  @Override
  public void addMetric(AssayMetric metric) {
    if (metrics == null) {
      metrics = new HashSet<>();
    }
    metrics.add(metric);
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
    DefaultAssay other = (DefaultAssay) obj;
    return Objects.equals(description, other.description)
        && Objects.equals(id, other.id)
        && Objects.equals(metrics, other.metrics)
        && Objects.equals(name, other.name)
        && Objects.equals(version, other.version);
  }
}
