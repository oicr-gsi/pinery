package ca.on.oicr.pinery.api;

import java.util.Date;

public interface Instrument {

   public Integer getId();

   public void setId(Integer id);

   public String getName();

   public void setName(String name);

   public Date getCreated();

   public void setCreated(Date created);

   public Integer getModelId();

   public void setModelId(Integer modelId);

}
