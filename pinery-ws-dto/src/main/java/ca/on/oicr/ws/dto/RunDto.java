package ca.on.oicr.ws.dto;

import java.util.Set;

public class RunDto {

   private String state;
   private String name;
   private String barcode;
   private String instrumentName;
   private Set<OrderPositionsDto> positions;
   private String createdByUrl;
   private String createdDate;
   private Integer id;
   private String url;

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBarcode() {
      return barcode;
   }

   public void SetBarcode(String barcode) {
      this.barcode = barcode;
   }

   public String getInstumentName() {
      return getInstumentName();
   }

   public void setInstrumentName(String instrumentName) {
      this.instrumentName = instrumentName;
   }

   public Set<OrderPositionsDto> getPositions() {
      return positions;
   }

   public void setPositions(Set<OrderPositionsDto> positions) {
      this.positions = positions;
   }

   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   public String getCreatedByDate() {
      return createdDate;
   }

   public void setCreatedByDate(String createdDate) {
      this.createdDate = createdDate;
   }

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

}
