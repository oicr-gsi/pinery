package ca.on.oicr.ws.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "order")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class OrderDto {

   @Override
   public String toString() {
      return "OrderDto [status=" + status + ", project=" + project + ", platform=" + platform + ", samples=" + samples + ", createdByUrl="
            + createdByUrl + ", createdDate=" + createdDate + ", modifiedByUrl=" + modifiedByUrl + ", modifiedDate=" + modifiedDate
            + ", id=" + id + ", url=" + url + ", hashCode()=" + hashCode() + ", getStatus()=" + getStatus() + ", getProject()="
            + getProject() + ", getPlatform()=" + getPlatform() + ", getSamples()=" + getSamples() + ", getCreatedByUrl()="
            + getCreatedByUrl() + ", getCreatedDate()=" + getCreatedDate() + ", getModifiedByUrl()=" + getModifiedByUrl()
            + ", getModifiedDate()=" + getModifiedDate() + ", getId()=" + getId() + ", getUrl()=" + getUrl() + ", getClass()=" + getClass()
            + ", toString()=" + super.toString() + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((createdByUrl == null) ? 0 : createdByUrl.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((modifiedByUrl == null) ? 0 : modifiedByUrl.hashCode());
      result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
      result = prime * result + ((platform == null) ? 0 : platform.hashCode());
      result = prime * result + ((project == null) ? 0 : project.hashCode());
      result = prime * result + ((samples == null) ? 0 : samples.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result = prime * result + ((url == null) ? 0 : url.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      OrderDto other = (OrderDto) obj;
      if (createdByUrl == null) {
         if (other.createdByUrl != null) return false;
      } else if (!createdByUrl.equals(other.createdByUrl)) return false;
      if (createdDate == null) {
         if (other.createdDate != null) return false;
      } else if (!createdDate.equals(other.createdDate)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (modifiedByUrl == null) {
         if (other.modifiedByUrl != null) return false;
      } else if (!modifiedByUrl.equals(other.modifiedByUrl)) return false;
      if (modifiedDate == null) {
         if (other.modifiedDate != null) return false;
      } else if (!modifiedDate.equals(other.modifiedDate)) return false;
      if (platform == null) {
         if (other.platform != null) return false;
      } else if (!platform.equals(other.platform)) return false;
      if (project == null) {
         if (other.project != null) return false;
      } else if (!project.equals(other.project)) return false;
      if (samples == null) {
         if (other.samples != null) return false;
      } else if (!samples.equals(other.samples)) return false;
      if (status == null) {
         if (other.status != null) return false;
      } else if (!status.equals(other.status)) return false;
      if (url == null) {
         if (other.url != null) return false;
      } else if (!url.equals(other.url)) return false;
      return true;
   }

   private String status;
   private String project;
   private String platform;
   private Set<OrderDtoSample> samples;
   @JsonProperty("created_by_url")
   private String createdByUrl;
   @JsonProperty("created_date")
   private String createdDate;
   @JsonProperty("modified_by_url")
   private String modifiedByUrl;
   @JsonProperty("modified_date")
   private String modifiedDate;
   private Integer id;
   private String url;

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getProject() {
      return project;
   }

   public void setProject(String project) {
      this.project = project;
   }

   public String getPlatform() {
      return platform;
   }

   public void setPlatform(String platform) {
      this.platform = platform;
   }

   public Set<OrderDtoSample> getSamples() {
      return samples;
   }

   public void setSamples(Set<OrderDtoSample> samples) {
      this.samples = samples;
   }

   @XmlElement(name = "created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @XmlElement(name = "created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @XmlElement(name = "modified_by_url")
   public String getModifiedByUrl() {
      return modifiedByUrl;
   }

   public void setModifiedByUrl(String modifiedByUrl) {
      this.modifiedByUrl = modifiedByUrl;
   }

   @XmlElement(name = "modified_date")
   public String getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(String modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

}
