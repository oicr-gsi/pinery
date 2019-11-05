package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Instrument;
import java.util.Date;

public class DefaultInstrument implements Instrument {

  private Integer id;
  private String name;
  private Date created;
  private Integer modelId;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public Integer getModelId() {
    return modelId;
  }

  @Override
  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }
}
