package ca.on.oicr.pinery.lims;

import java.util.Date;
import java.util.Set;

import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;

//Implementation of Order interface
public class DefaultOrder implements Order {

   private String status;
   private String project;
   private String platform;
   private Set<OrderSample> sample;
   private Date createdDate;
   private Date modifiedDate;
   private Integer id;
   private String createdById;
   private String modifiedById;

   @Override
   public String getStatus() {
      return status;
   }

   @Override
   public void setStatus(String status) {
      this.status = status;
   }

   @Override
   public String getProject() {
      return project;
   }

   @Override
   public void setProject(String project) {
      this.project = project;
   }

   @Override
   public String getPlatform() {
      return platform;
   }

   @Override
   public void setPlatform(String platform) {
      this.platform = platform;
   }

   @Override
   public Set<OrderSample> getSamples() {
      return sample;
   }

   @Override
   public void setSample(Set<OrderSample> sample) {
      this.sample = sample;
   }

   @Override
   public Date getCreatedDate() {
      return createdDate;
   }

   @Override
   public void setCreatedDate(Date createdDate) {
      this.createdDate = createdDate;
   }

   @Override
   public Date getModifiedDate() {
      return modifiedDate;
   }

   @Override
   public void setModifiedDate(Date modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   @Override
   public Integer getId() {
      return id;
   }

   @Override
   public void setId(Integer id) {
      this.id = id;
   }

   @Override
   public String getCreatedById() {
      return createdById;
   }

   @Override
   public void setCreatedById(String createdById) {
      this.createdById = createdById;
   }

   @Override
   public String getModifiedById() {
      return modifiedById;
   }

   @Override
   public void setModifiedById(String modifiedById) {
      this.modifiedById = modifiedById;
   }
}
