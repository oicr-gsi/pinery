package ca.on.oicr.pinery.lims;

import java.util.Date;

import ca.on.oicr.pinery.api.Instrument;

public class DefaultInstrument implements Instrument {

   private Integer id;
   private String name;
   private Date created;

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
}
