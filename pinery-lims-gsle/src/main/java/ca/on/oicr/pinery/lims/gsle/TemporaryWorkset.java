package ca.on.oicr.pinery.lims.gsle;

import ca.on.oicr.pinery.lims.Workset;

public class TemporaryWorkset extends Workset {

  private String sampleId;

  public String getSampleId() {
    return sampleId;
  }

  public void setSampleId(String sampleId) {
    this.sampleId = sampleId;
  }
  
  public Workset makeWorkset() {
    Workset ws = new Workset();
    ws.setId(this.getId());
    ws.setName(this.getName());
    ws.setDescription(this.getDescription());
    ws.setBarcode(this.getBarcode());
    ws.addSampleId(this.getSampleId());
    return ws;
  }
  
}
