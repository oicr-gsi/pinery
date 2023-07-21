package ca.on.oicr.pinery.api;

public interface AssayTest {

  String getName();

  void setName(String name);

  String getTissueType();

  void setTissueType(String tissueType);

  Boolean getNegateTissueType();

  void setNegateTissueType(Boolean negateTissueType);

  String getExtractionSampleType();

  void setExtractionSampleType(String extractionSampleType);

  String getLibrarySourceTemplateType();

  void setLibrarySourceTemplateType(String sourceTemplateType);

  String getLibraryQualificationMethod();

  void setLibraryQualificationMethod(String libraryQualificationMethod);

  String getLibraryQualificationSourceTemplateType();

  void setLibraryQualificationSourceTemplateType(String libraryQualificationSourceTemplateType);

  Boolean getRepeatPerTimepoint();

  void setRepeatPerTimepoint(Boolean repeatPerTimepoint);

  String getPermittedSamples();

  void setPermittedSamples(String permittedSamples);
}
