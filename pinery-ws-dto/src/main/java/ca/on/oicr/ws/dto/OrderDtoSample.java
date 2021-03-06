package ca.on.oicr.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDtoSample {

  private String barcode;
  private String barcodeTwo;
  private String url;
  private String id;
  private Set<AttributeDto> attributes;

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

  public Set<AttributeDto> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<AttributeDto> attributes) {
    this.attributes = attributes;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    OrderDtoSample other = (OrderDtoSample) obj;
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
    if (url == null) {
      if (other.url != null) return false;
    } else if (!url.equals(other.url)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "OrderDtoSample [barcode="
        + barcode
        + ", barcodeTwo="
        + barcodeTwo
        + ", url="
        + url
        + ", id="
        + id
        + ", attributes="
        + attributes
        + ", hashCode()="
        + hashCode()
        + "]";
  }
}
