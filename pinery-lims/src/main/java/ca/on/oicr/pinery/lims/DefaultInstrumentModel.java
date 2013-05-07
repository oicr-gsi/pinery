package ca.on.oicr.pinery.lims;

import java.util.Date;

import ca.on.oicr.pinery.api.InstrumentModel;

public class DefaultInstrumentModel implements InstrumentModel {

   private Integer id;
   private String name;
   private Date created;
   private Integer createdById;
   private Date modified;
   private Integer modifiedById;
   public Integer getId() {
      return id;
   }
   public void setId(Integer id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public Date getCreated() {
      return created;
   }
   public void setCreated(Date created) {
      this.created = created;
   }
   public Integer getCreatedById() {
      return createdById;
   }
   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }
   public Date getModified() {
      return modified;
   }
   public void setModified(Date modified) {
      this.modified = modified;
   }
   public Integer getModifiedById() {
      return modifiedById;
   }
   public void setModifiedById(Integer modifiedById) {
      this.modifiedById = modifiedById;
   }
   
 
}
