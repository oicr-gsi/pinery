package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.AssayMetric;
import ca.on.oicr.pinery.api.AssayMetricSubcategory;
import java.math.BigDecimal;
import java.util.Objects;

public class DefaultAssayMetric implements AssayMetric {

  private String name;
  private String category;
  private AssayMetricSubcategory subcategory;
  private String units;
  private String thresholdType;
  private BigDecimal minimum;
  private BigDecimal maximum;
  private Integer sortPriority;
  private String nucleicAcidType;
  private String tissueMaterial;
  private String tissueType;
  private Boolean negateTissueType;
  private String tissueOrigin;
  private String containerModel;
  private Integer readLength;
  private Integer readLength2;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getCategory() {
    return category;
  }

  @Override
  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  public AssayMetricSubcategory getSubcategory() {
    return subcategory;
  }

  @Override
  public void setSubcategory(AssayMetricSubcategory subcategory) {
    this.subcategory = subcategory;
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
  public String getThresholdType() {
    return thresholdType;
  }

  @Override
  public void setThresholdType(String thresholdType) {
    this.thresholdType = thresholdType;
  }

  @Override
  public BigDecimal getMinimum() {
    return minimum;
  }

  @Override
  public void setMinimum(BigDecimal minimum) {
    this.minimum = minimum;
  }

  @Override
  public BigDecimal getMaximum() {
    return maximum;
  }

  @Override
  public void setMaximum(BigDecimal maximum) {
    this.maximum = maximum;
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
  public String getNucleicAcidType() {
    return nucleicAcidType;
  }

  @Override
  public void setNucleicAcidType(String nucleicAcidType) {
    this.nucleicAcidType = nucleicAcidType;
  }

  @Override
  public String getTissueMaterial() {
    return tissueMaterial;
  }

  @Override
  public void setTissueMaterial(String tissueMaterial) {
    this.tissueMaterial = tissueMaterial;
  }

  @Override
  public String getTissueType() {
    return tissueType;
  }

  @Override
  public void setTissueType(String tissueType) {
    this.tissueType = tissueType;
  }

  @Override
  public Boolean getNegateTissueType() {
    return negateTissueType;
  }

  @Override
  public void setNegateTissueType(Boolean negateTissueType) {
    this.negateTissueType = negateTissueType;
  }

  @Override
  public String getTissueOrigin() {
    return tissueOrigin;
  }

  @Override
  public void setTissueOrigin(String tissueOrigin) {
    this.tissueOrigin = tissueOrigin;
  }

  @Override
  public String getContainerModel() {
    return containerModel;
  }

  @Override
  public void setContainerModel(String containerModel) {
    this.containerModel = containerModel;
  }

  @Override
  public Integer getReadLength() {
    return readLength;
  }

  @Override
  public void setReadLength(Integer readLength) {
    this.readLength = readLength;
  }

  @Override
  public Integer getReadLength2() {
    return readLength2;
  }

  @Override
  public void setReadLength2(Integer readLength2) {
    this.readLength2 = readLength2;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        category,
        containerModel,
        maximum,
        minimum,
        name,
        negateTissueType,
        nucleicAcidType,
        readLength,
        readLength2,
        sortPriority,
        subcategory,
        thresholdType,
        tissueMaterial,
        tissueOrigin,
        tissueType,
        units);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DefaultAssayMetric other = (DefaultAssayMetric) obj;
    return Objects.equals(category, other.category)
        && Objects.equals(containerModel, other.containerModel)
        && Objects.equals(maximum, other.maximum)
        && Objects.equals(minimum, other.minimum)
        && Objects.equals(name, other.name)
        && Objects.equals(negateTissueType, other.negateTissueType)
        && Objects.equals(nucleicAcidType, other.nucleicAcidType)
        && Objects.equals(readLength, other.readLength)
        && Objects.equals(readLength2, other.readLength2)
        && Objects.equals(sortPriority, other.sortPriority)
        && Objects.equals(subcategory, other.subcategory)
        && Objects.equals(thresholdType, other.thresholdType)
        && Objects.equals(tissueMaterial, other.tissueMaterial)
        && Objects.equals(tissueOrigin, other.tissueOrigin)
        && Objects.equals(tissueType, other.tissueType)
        && Objects.equals(units, other.units);
  }
}
