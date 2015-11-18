package ca.on.oicr.ws.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class RunDtoSample {

   private String barcode;
   @JsonProperty("barcode_two")
   private String barcodeTwo;
   private Integer id;
   private String url;

   public Integer getId() {
     return id;
   }

   public void setId(Integer id) {
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

   public String getBarcodeTwo() {
      return barcodeTwo;
   }

   public void setBarcodeTwo(String barcodeTwo) {
      this.barcodeTwo = barcodeTwo;
   }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    result = prime * result
        + ((barcodeTwo == null) ? 0 : barcodeTwo.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RunDtoSample other = (RunDtoSample) obj;
    if (barcode == null) {
      if (other.barcode != null)
        return false;
    }
    else if (!barcode.equals(other.barcode))
      return false;
    if (barcodeTwo == null) {
      if (other.barcodeTwo != null)
        return false;
    }
    else if (!barcodeTwo.equals(other.barcodeTwo))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    }
    else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "RunDtoSample [barcode=" + barcode + ", barcodeTwo=" + barcodeTwo
        + ", id=" + id + ", url=" + url + ", hashCode()=" + hashCode() + "]";
  }

}
