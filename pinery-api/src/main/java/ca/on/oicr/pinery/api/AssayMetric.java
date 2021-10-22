package ca.on.oicr.pinery.api;

import java.math.BigDecimal;

public interface AssayMetric {

  String getName();

  void setName(String name);

  String getCategory();

  void setCategory(String category);

  AssayMetricSubcategory getSubcategory();

  void setSubcategory(AssayMetricSubcategory subcategory);

  String getUnits();

  void setUnits(String units);

  String getThresholdType();

  void setThresholdType(String thresholdType);

  BigDecimal getMinimum();

  void setMinimum(BigDecimal minimum);

  BigDecimal getMaximum();

  void setMaximum(BigDecimal maximum);

  Integer getSortPriority();

  void setSortPriority(Integer sortPriority);

  String getNucleicAcidType();

  void setNucleicAcidType(String nucleicAcidType);

  String getTissueMaterial();

  void setTissueMaterial(String tissueMaterial);

  String getTissueType();

  void setTissueType(String tissueType);

  Boolean getNegateTissueType();

  void setNegateTissueType(Boolean negateTissueType);

  String getTissueOrigin();

  void setTissueOrigin(String tissueOrigin);

  String getContainerModel();

  void setContainerModel(String containerModel);

  Integer getReadLength();

  void setReadLength(Integer readLength);

  Integer getReadLength2();

  void setReadLength2(Integer readLength2);
}
