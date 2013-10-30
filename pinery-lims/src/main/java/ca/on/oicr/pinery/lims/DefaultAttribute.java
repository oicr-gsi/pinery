package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Attribute;

public class DefaultAttribute implements Attribute {

   @Override
   public String toString() {
      return "DefaultAttribute [id=" + id + ", name=" + name + ", value=" + value + ", hashCode()=" + hashCode() + ", getId()=" + getId()
            + ", getName()=" + getName() + ", getValue()=" + getValue() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
            + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      DefaultAttribute other = (DefaultAttribute) obj;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (value == null) {
         if (other.value != null) return false;
      } else if (!value.equals(other.value)) return false;
      return true;
   }

   private Integer id;
   private String name;
   private String value;

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
   public String getValue() {
      return value;
   }

   @Override
   public void setValue(String value) {
      this.value = value;
   }

}
