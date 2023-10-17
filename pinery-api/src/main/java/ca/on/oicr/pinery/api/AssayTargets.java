package ca.on.oicr.pinery.api;

public interface AssayTargets {

  Integer getCaseDays();

  void setCaseDays(Integer days);

  Integer getReceiptDays();

  void setReceiptDays(Integer days);

  Integer getExtractionDays();

  void setExtractionDays(Integer days);

  Integer getLibraryPreparationDays();

  void setLibraryPreparationDays(Integer days);

  Integer getLibraryQualificationDays();

  void setLibraryQualificationDays(Integer days);

  Integer getFullDepthSequencingDays();

  void setFullDepthSequencingDays(Integer days);

  Integer getAnalysisReviewDays();

  void setAnalysisReviewDays(Integer days);

  Integer getReleaseApprovalDays();

  void setReleaseApprovalDays(Integer days);

  Integer getReleaseDays();

  void setReleaseDays(Integer days);

}
