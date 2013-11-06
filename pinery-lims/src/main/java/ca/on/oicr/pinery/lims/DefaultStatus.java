package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Status;

public class DefaultStatus implements Status {

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((state == null) ? 0 : state.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultStatus other = (DefaultStatus) obj;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (state == null) {
         if (other.state != null) return false;
      } else if (!state.equals(other.state)) return false;
      return true;
   }

   private String name;
   private String state;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

}
