package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoxPositionDto {
  
  private String position;
  private String sampleId;
  
  public String getPosition() {
    return position;
  }
  
  public void setPosition(String position) {
    this.position = position;
  }
  
  @JsonProperty("sample_id")
  public String getSampleId() {
    return sampleId;
  }
  
  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }

}
