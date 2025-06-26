package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliverableDto {

  private String name;
  private String category;
  private Boolean analysisReviewRequired;

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

  @JsonProperty("analysis_review_required")
  public Boolean getAnalysisReviewRequired() {
    return analysisReviewRequired;
  }

  public void setAnalysisReviewRequired(Boolean analysisReviewRequired) {
    this.analysisReviewRequired = analysisReviewRequired;
  }

}
