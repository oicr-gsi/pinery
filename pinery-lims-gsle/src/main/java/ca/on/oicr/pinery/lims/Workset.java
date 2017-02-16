package ca.on.oicr.pinery.lims;

import java.util.HashSet;
import java.util.Set;

public class Workset {

  private String id;
  private String name;
  private String barcode;
  private String description;
  private final Set<String> sampleIds = new HashSet<>();
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = "".equals(name) ? null : name;
  }
  
  public String getBarcode() {
    return barcode;
  }
  
  public void setBarcode(String barcode) {
    this.barcode = "".equals(barcode) ? null : barcode;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = "".equals(description) ? null : description;
  }
  
  public Set<String> getSampleIds() {
    return sampleIds;
  }
  
  public void addSampleId(String sampleId) {
    this.sampleIds.add(sampleId);
  }

}
