package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssayMetricDto {

  private String name;
  private String category;
  private AssayMetricSubcategoryDto subcategory;
  private String units;
  private String thresholdType;
  private String minimum;
  private String maximum;
  private Integer sortPriority;
  private String nucleicAcidType;
  private String tissueMaterial;
  private String tissueType;
  private Boolean negateTissueType;
  private String tissueOrigin;
  private String containerModel;
  private Integer readLength;
  private Integer readLength2;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public AssayMetricSubcategoryDto getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(AssayMetricSubcategoryDto subcategory) {
    this.subcategory = subcategory;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  @JsonProperty("threshold_type")
  public String getThresholdType() {
    return thresholdType;
  }

  public void setThresholdType(String thresholdType) {
    this.thresholdType = thresholdType;
  }

  public String getMinimum() {
    return minimum;
  }

  public void setMinimum(String minimum) {
    this.minimum = minimum;
  }

  public String getMaximum() {
    return maximum;
  }

  public void setMaximum(String maximum) {
    this.maximum = maximum;
  }

  @JsonProperty("sort_proproty")
  public Integer getSortPriority() {
    return sortPriority;
  }

  public void setSortPriority(Integer sortPriority) {
    this.sortPriority = sortPriority;
  }

  @JsonProperty("nucleic_acid_type")
  public String getNucleicAcidType() {
    return nucleicAcidType;
  }

  public void setNucleicAcidType(String nucleicAcidType) {
    this.nucleicAcidType = nucleicAcidType;
  }

  @JsonProperty("tissue_material")
  public String getTissueMaterial() {
    return tissueMaterial;
  }

  public void setTissueMaterial(String tissueMaterial) {
    this.tissueMaterial = tissueMaterial;
  }

  @JsonProperty("tissue_type")
  public String getTissueType() {
    return tissueType;
  }

  public void setTissueType(String tissueType) {
    this.tissueType = tissueType;
  }

  @JsonProperty("negate_tissue_type")
  public Boolean getNegateTissueType() {
    return negateTissueType;
  }

  public void setNegateTissueType(Boolean negateTissueType) {
    this.negateTissueType = negateTissueType;
  }

  @JsonProperty("tissue_origin")
  public String getTissueOrigin() {
    return tissueOrigin;
  }

  public void setTissueOrigin(String tissueOrigin) {
    this.tissueOrigin = tissueOrigin;
  }

  @JsonProperty("container_model")
  public String getContainerModel() {
    return containerModel;
  }

  public void setContainerModel(String containerModel) {
    this.containerModel = containerModel;
  }

  @JsonProperty("read_length")
  public Integer getReadLength() {
    return readLength;
  }

  public void setReadLength(Integer readLength) {
    this.readLength = readLength;
  }

  @JsonProperty("read_length_2")
  public Integer getReadLength2() {
    return readLength2;
  }

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
    AssayMetricDto other = (AssayMetricDto) obj;
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
