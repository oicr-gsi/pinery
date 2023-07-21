package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssayTestDto {

  private String name;
  private String tissueType;
  private Boolean negateTissueType;
  private String extractionSampleType;
  private String librarySourceTemplateType;
  private String libraryQualificationMethod;
  private String libraryQualificationSourceTemplateType;
  private Boolean repeatPerTimepoint;
  private String permittedSamples;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  @JsonProperty("extraction_sample_type")
  public String getExtractionSampleType() {
    return extractionSampleType;
  }

  public void setExtractionSampleType(String extractionSampleType) {
    this.extractionSampleType = extractionSampleType;
  }

  @JsonProperty("library_source_template_type")
  public String getLibrarySourceTemplateType() {
    return librarySourceTemplateType;
  }

  public void setLibrarySourceTemplateType(String librarySourceTemplateType) {
    this.librarySourceTemplateType = librarySourceTemplateType;
  }

  @JsonProperty("library_qualification_method")
  public String getLibraryQualificationMethod() {
    return libraryQualificationMethod;
  }

  public void setLibraryQualificationMethod(String libraryQualificationMethod) {
    this.libraryQualificationMethod = libraryQualificationMethod;
  }

  @JsonProperty("library_qualification_source_template_type")
  public String getLibraryQualificationSourceTemplateType() {
    return libraryQualificationSourceTemplateType;
  }

  public void setLibraryQualificationSourceTemplateType(
      String libraryQualificationSourceTemplateType) {
    this.libraryQualificationSourceTemplateType = libraryQualificationSourceTemplateType;
  }

  @JsonProperty("repeat_per_timepoint")
  public Boolean getRepeatPerTimepoint() {
    return repeatPerTimepoint;
  }

  public void setRepeatPerTimepoint(Boolean repeatPerTimepoint) {
    this.repeatPerTimepoint = repeatPerTimepoint;
  }

  @JsonProperty("permitted_samples")
  public String getPermittedSamples() {
    return permittedSamples;
  }

  public void setPermittedSamples(String permittedSamples) {
    this.permittedSamples = permittedSamples;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AssayTestDto that = (AssayTestDto) o;
    return Objects.equals(name, that.name)
        && Objects.equals(tissueType, that.tissueType)
        && Objects.equals(negateTissueType, that.negateTissueType)
        && Objects.equals(extractionSampleType, that.extractionSampleType)
        && Objects.equals(librarySourceTemplateType, that.librarySourceTemplateType)
        && Objects.equals(libraryQualificationMethod, that.libraryQualificationMethod)
        && Objects.equals(
            libraryQualificationSourceTemplateType, that.libraryQualificationSourceTemplateType)
        && Objects.equals(repeatPerTimepoint, that.repeatPerTimepoint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        tissueType,
        negateTissueType,
        extractionSampleType,
        librarySourceTemplateType,
        libraryQualificationMethod,
        libraryQualificationSourceTemplateType,
        repeatPerTimepoint);
  }
}
