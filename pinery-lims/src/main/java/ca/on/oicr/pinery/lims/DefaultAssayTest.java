package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.AssayTest;

public class DefaultAssayTest implements AssayTest {

  private String name;
  private String tissueType;
  private Boolean negateTissueType;
  private String extractionSampleType;
  private String librarySourceTemplateType;
  private String libraryQualificationMethod;
  private String libraryQualificationSourceTemplateType;
  private Boolean repeatPerTimepoint;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
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
  public String getExtractionSampleType() {
    return extractionSampleType;
  }

  @Override
  public void setExtractionSampleType(String extractionSampleType) {
    this.extractionSampleType = extractionSampleType;
  }

  @Override
  public String getLibrarySourceTemplateType() {
    return librarySourceTemplateType;
  }

  @Override
  public void setLibrarySourceTemplateType(String librarySourceTemplateType) {
    this.librarySourceTemplateType = librarySourceTemplateType;
  }

  @Override
  public String getLibraryQualificationMethod() {
    return libraryQualificationMethod;
  }

  @Override
  public void setLibraryQualificationMethod(String libraryQualificationMethod) {
    this.libraryQualificationMethod = libraryQualificationMethod;
  }

  @Override
  public String getLibraryQualificationSourceTemplateType() {
    return libraryQualificationSourceTemplateType;
  }

  @Override
  public void setLibraryQualificationSourceTemplateType(
      String libraryQualificationSourceTemplateType) {
    this.libraryQualificationSourceTemplateType = libraryQualificationSourceTemplateType;
  }

  @Override
  public Boolean getRepeatPerTimepoint() {
    return repeatPerTimepoint;
  }

  @Override
  public void setRepeatPerTimepoint(Boolean repeatPerTimepoint) {
    this.repeatPerTimepoint = repeatPerTimepoint;
  }
}
