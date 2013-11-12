package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.Set;

public interface Run {

   public String getState();

   public void setState(String state);

   public String getName();

   public void setName(String name);

   public String getBarcode();

   public void setBarcode(String barcode);

   public String getInstrumentName();

   public void setInstrumentName(String instrumentName);

   public Set<RunPosition> getSamples();

   public void setSample(Set<RunPosition> samples);

   public String getCreatedByUrl();

   public void setCreatedByUrl(String createdByUrl);

   public Date getCreatedDate();

   public void setCreatedDate(Date createdDate);

   public Integer getId();

   public void setId(Integer id);

   public Integer getCreatedById();

   public void setCreatedById(Integer createdById);
}
