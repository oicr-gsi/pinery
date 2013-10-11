package ca.on.oicr.pinery.api;

import java.util.Date;
import java.util.Set;

public interface Order {

   public String getStatus();

   public void setStatus(String status);

   public String getProject();

   public void setProject(String project);

   public String getPlatform();

   public void setPlatform(String platform);

   public Set<OrderSample> getSamples();

   public void setSample(Set<OrderSample> sample);

   public Date getCreatedDate();

   public void setCreatedDate(Date createdDate);

   public Date getModifiedDate();

   public void setModifiedDate(Date modifiedDate);

   public Integer getId();

   public void setId(Integer id);

   public String getCreatedById();

   public void setCreatedById(String createdById);

   public String getModifiedById();

   public void setModifiedById(String createdById);
}
