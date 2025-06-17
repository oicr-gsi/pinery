package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Deliverable;

public class DefaultDeliverable implements Deliverable {

  private String name;
  private String category;
  private Boolean analysisReviewRequired;

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
  public Boolean getAnalysisReviewRequired() {
    return analysisReviewRequired;
  }

  @Override
  public void setAnalysisReviewRequired(Boolean analysisReviewRequired) {
    this.analysisReviewRequired = analysisReviewRequired;
  }

}
