package ca.on.oicr.pinery.api;

public interface Deliverable {

  String getName();

  void setName(String name);

  String getCategory();

  void setCategory(String category);

  Boolean getAnalysisReviewRequired();

  void setAnalysisReviewRequired(Boolean analysisReviewRequired);

}
