package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.AssayTargets;

public class DefaultAssayTargets implements AssayTargets {

  private Integer caseDays;
  private Integer receiptDays;
  private Integer extractionDays;
  private Integer libraryPreparationDays;
  private Integer libraryQualificationDays;
  private Integer fullDepthSequencingDays;
  private Integer analysisReviewDays;
  private Integer releaseApprovalDays;
  private Integer releaseDays;

  @Override
  public Integer getCaseDays() {
    return caseDays;
  }

  @Override
  public void setCaseDays(Integer caseDays) {
    this.caseDays = caseDays;
  }

  @Override
  public Integer getReceiptDays() {
    return receiptDays;
  }

  @Override
  public void setReceiptDays(Integer receiptDays) {
    this.receiptDays = receiptDays;
  }

  @Override
  public Integer getExtractionDays() {
    return extractionDays;
  }

  @Override
  public void setExtractionDays(Integer extractionDays) {
    this.extractionDays = extractionDays;
  }

  @Override
  public Integer getLibraryPreparationDays() {
    return libraryPreparationDays;
  }

  @Override
  public void setLibraryPreparationDays(Integer libraryPreparationDays) {
    this.libraryPreparationDays = libraryPreparationDays;
  }

  @Override
  public Integer getLibraryQualificationDays() {
    return libraryQualificationDays;
  }

  @Override
  public void setLibraryQualificationDays(Integer libraryQualificationDays) {
    this.libraryQualificationDays = libraryQualificationDays;
  }

  @Override
  public Integer getFullDepthSequencingDays() {
    return fullDepthSequencingDays;
  }

  @Override
  public void setFullDepthSequencingDays(Integer fullDepthSequencingDays) {
    this.fullDepthSequencingDays = fullDepthSequencingDays;
  }

  @Override
  public Integer getAnalysisReviewDays() {
    return analysisReviewDays;
  }

  @Override
  public void setAnalysisReviewDays(Integer analysisReviewDays) {
    this.analysisReviewDays = analysisReviewDays;
  }

  @Override
  public Integer getReleaseApprovalDays() {
    return releaseApprovalDays;
  }

  @Override
  public void setReleaseApprovalDays(Integer releaseApprovalDays) {
    this.releaseApprovalDays = releaseApprovalDays;
  }

  @Override
  public Integer getReleaseDays() {
    return releaseDays;
  }

  @Override
  public void setReleaseDays(Integer releaseDays) {
    this.releaseDays = releaseDays;
  }

}
