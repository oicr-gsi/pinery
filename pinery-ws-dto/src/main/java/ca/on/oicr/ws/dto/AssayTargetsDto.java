package ca.on.oicr.ws.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssayTargetsDto {

  private Integer caseDays;
  private Integer receiptDays;
  private Integer extractionDays;
  private Integer libraryPreparationDays;
  private Integer libraryQualificationDays;
  private Integer fullDepthSequencingDays;
  private Integer analysisReviewDays;
  private Integer releaseApprovalDays;
  private Integer releaseDays;

  @JsonProperty("case_days")
  public Integer getCaseDays() {
    return caseDays;
  }

  public void setCaseDays(Integer caseDays) {
    this.caseDays = caseDays;
  }

  @JsonProperty("receipt_days")
  public Integer getReceiptDays() {
    return receiptDays;
  }

  public void setReceiptDays(Integer receiptDays) {
    this.receiptDays = receiptDays;
  }

  @JsonProperty("extraction_days")
  public Integer getExtractionDays() {
    return extractionDays;
  }

  public void setExtractionDays(Integer extractionDays) {
    this.extractionDays = extractionDays;
  }

  @JsonProperty("library_preparation_days")
  public Integer getLibraryPreparationDays() {
    return libraryPreparationDays;
  }

  public void setLibraryPreparationDays(Integer libraryPreparationDays) {
    this.libraryPreparationDays = libraryPreparationDays;
  }

  @JsonProperty("library_qualification_days")
  public Integer getLibraryQualificationDays() {
    return libraryQualificationDays;
  }

  public void setLibraryQualificationDays(Integer libraryQualificationDays) {
    this.libraryQualificationDays = libraryQualificationDays;
  }

  @JsonProperty("full_depth_sequencing_days")
  public Integer getFullDepthSequencingDays() {
    return fullDepthSequencingDays;
  }

  public void setFullDepthSequencingDays(Integer fullDepthSequencingDays) {
    this.fullDepthSequencingDays = fullDepthSequencingDays;
  }

  @JsonProperty("analysis_review_days")
  public Integer getAnalysisReviewDays() {
    return analysisReviewDays;
  }

  public void setAnalysisReviewDays(Integer analysisReviewDays) {
    this.analysisReviewDays = analysisReviewDays;
  }

  @JsonProperty("release_approval_days")
  public Integer getReleaseApprovalDays() {
    return releaseApprovalDays;
  }

  public void setReleaseApprovalDays(Integer releaseApprovalDays) {
    this.releaseApprovalDays = releaseApprovalDays;
  }

  @JsonProperty("release_days")
  public Integer getReleaseDays() {
    return releaseDays;
  }

  public void setReleaseDays(Integer releaseDays) {
    this.releaseDays = releaseDays;
  }

  @Override
  public int hashCode() {
    return Objects.hash(caseDays, receiptDays, extractionDays, libraryPreparationDays, libraryQualificationDays, fullDepthSequencingDays,
        analysisReviewDays, releaseApprovalDays, releaseDays);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AssayTargetsDto other = (AssayTargetsDto) obj;
    return Objects.equals(caseDays, other.caseDays)
        && Objects.equals(receiptDays, other.receiptDays)
        && Objects.equals(extractionDays, other.extractionDays)
        && Objects.equals(libraryPreparationDays, other.libraryPreparationDays)
        && Objects.equals(libraryQualificationDays, other.libraryQualificationDays)
        && Objects.equals(fullDepthSequencingDays, other.fullDepthSequencingDays)
        && Objects.equals(analysisReviewDays, other.analysisReviewDays)
        && Objects.equals(releaseApprovalDays, other.releaseApprovalDays)
        && Objects.equals(releaseDays, other.releaseDays);
  }

}
