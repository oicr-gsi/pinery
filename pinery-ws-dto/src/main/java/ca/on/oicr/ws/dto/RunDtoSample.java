package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunDtoSample {

  private String barcode;
  private String barcodeTwo;
  private String runPurpose;
  private String id;
  private String url;
  private Set<AttributeDto> attributes;
  private StatusDto status;
  private String dataReview;
  private String dataReviewDate;
  private Integer dataReviewerId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  @JsonProperty("barcode_two")
  public String getBarcodeTwo() {
    return barcodeTwo;
  }

  public void setBarcodeTwo(String barcodeTwo) {
    this.barcodeTwo = barcodeTwo;
  }

  public String getRunPurpose() {
    return runPurpose;
  }

  public void setRunPurpose(String runPurpose) {
    this.runPurpose = runPurpose;
  }

  public Set<AttributeDto> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<AttributeDto> attributes) {
    this.attributes = attributes;
  }

  public StatusDto getStatus() {
    return status;
  }

  public void setStatus(StatusDto status) {
    this.status = status;
  }

  @JsonProperty("data_review")
  public String getDataReview() {
    return dataReview;
  }

  public void setDataReview(String dataReview) {
    this.dataReview = dataReview;
  }

  @JsonProperty("data_review_date")
  public String getDataReviewDate() {
    return dataReviewDate;
  }

  public void setDataReviewDate(String dataReviewDate) {
    this.dataReviewDate = dataReviewDate;
  }

  @JsonProperty("data_reviewer_id")
  public Integer getDataReviewerId() {
    return dataReviewerId;
  }

  public void setDataReviewerId(Integer dataReviewerId) {
    this.dataReviewerId = dataReviewerId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((runPurpose == null) ? 0 : runPurpose.hashCode());
    result = prime * result + ((dataReview == null) ? 0 : dataReview.hashCode());
    result = prime * result + ((dataReviewDate == null) ? 0 : dataReviewDate.hashCode());
    result = prime * result + ((dataReviewerId == null) ? 0 : dataReviewerId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RunDtoSample other = (RunDtoSample) obj;
    if (attributes == null) {
      if (other.attributes != null) return false;
    } else if (!attributes.equals(other.attributes)) return false;
    if (barcode == null) {
      if (other.barcode != null) return false;
    } else if (!barcode.equals(other.barcode)) return false;
    if (barcodeTwo == null) {
      if (other.barcodeTwo != null) return false;
    } else if (!barcodeTwo.equals(other.barcodeTwo)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (status == null) {
      if (other.status != null) return false;
    } else if (!status.equals(other.status)) return false;
    if (runPurpose == null) {
      if (other.runPurpose != null) return false;
    } else if (!runPurpose.equals(other.runPurpose)) return false;
    if (dataReview == null) {
      if (other.dataReview != null) return false;
    } else if (!dataReview.equals(other.dataReview)) return false;
    if (dataReviewDate == null) {
      if (other.dataReviewDate != null) return false;
    } else if (!dataReviewDate.equals(other.dataReviewDate)) return false;
    if (dataReviewerId == null) {
      if (other.dataReviewerId != null) return false;
    } else if (!dataReviewerId.equals(other.dataReviewerId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RunDtoSample [barcode="
        + barcode
        + ", barcodeTwo="
        + barcodeTwo
        + ", runPurpose="
        + runPurpose
        + ", id="
        + id
        + ", status="
        + status
        + ", url="
        + url
        + ", attributes="
        + attributes
        + "]";
  }
}
