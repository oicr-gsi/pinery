package ca.on.oicr.ws.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "attribute")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class AttributeDto {

   @Override
   public String toString() {
      return "AttributeDto [entityUrl=" + entityUrl + ", url=" + url + ", name=" + name + ", value=" + value + ", unit=" + unit
            + ", hashCode()=" + hashCode() + ", getEntityUrl()=" + getEntityUrl() + ", getUrl()=" + getUrl() + ", getName()=" + getName()
            + ", getValue()=" + getValue() + ", getUnit()=" + getUnit() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
            + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((entityUrl == null) ? 0 : entityUrl.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((unit == null) ? 0 : unit.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      AttributeDto other = (AttributeDto) obj;
      if (entityUrl == null) {
         if (other.entityUrl != null) return false;
      } else if (!entityUrl.equals(other.entityUrl)) return false;
      if (name == null) {
         if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (unit == null) {
         if (other.unit != null) return false;
      } else if (!unit.equals(other.unit)) return false;
      if (url == null) {
         if (other.url != null) return false;
      } else if (!url.equals(other.url)) return false;
      if (value == null) {
         if (other.value != null) return false;
      } else if (!value.equals(other.value)) return false;
      return true;
   }

   @JsonProperty("entity_url")
   private String entityUrl;
   private String url;
   private String name;
   private String value;
   private String unit;

   public String getEntityUrl() {
      return entityUrl;
   }

   public void setEntityUrl(String entityUrl) {
      this.entityUrl = entityUrl;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getUnit() {
      return unit;
   }

   public void setUnit(String unit) {
      this.unit = unit;
   }
}
