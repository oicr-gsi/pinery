package ca.on.oicr.pinery.api;

import java.util.Set;

public interface Assay {

  Integer getId();

  void setId(Integer id);

  String getName();

  void setName(String name);

  String getDescription();

  void setDescription(String description);

  String getVersion();

  void setVersion(String version);

  Set<AssayTest> getTests();

  void setTests(Set<AssayTest> tests);

  void addTest(AssayTest test);

  Set<AssayMetric> getMetrics();

  void setMetrics(Set<AssayMetric> metrics);

  void addMetric(AssayMetric metric);
}
