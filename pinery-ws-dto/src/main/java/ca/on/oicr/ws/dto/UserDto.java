package ca.on.oicr.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@XmlRootElement(name = "user")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserDto {

   @Override
   public String toString() {
      return "UserDto [title=" + title + ", firstname=" + firstname + ", lastname=" + lastname + ", institution=" + institution
            + ", phone=" + phone + ", email=" + email + ", comment=" + comment + ", createdDate=" + createdDate + ", modifiedDate="
            + modifiedDate + ", id=" + id + "]";
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((archived == null) ? 0 : archived.hashCode());
      result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((institution == null) ? 0 : institution.hashCode());
      result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
      result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
      result = prime * result + ((phone == null) ? 0 : phone.hashCode());
      result = prime * result + ((title == null) ? 0 : title.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      UserDto other = (UserDto) obj;
      if (archived == null) {
         if (other.archived != null) return false;
      } else if (!archived.equals(other.archived)) return false;
      if (createdDate == null) {
         if (other.createdDate != null) return false;
      } else if (!createdDate.equals(other.createdDate)) return false;
      if (email == null) {
         if (other.email != null) return false;
      } else if (!email.equals(other.email)) return false;
      if (firstname == null) {
         if (other.firstname != null) return false;
      } else if (!firstname.equals(other.firstname)) return false;
      if (id == null) {
         if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      if (institution == null) {
         if (other.institution != null) return false;
      } else if (!institution.equals(other.institution)) return false;
      if (lastname == null) {
         if (other.lastname != null) return false;
      } else if (!lastname.equals(other.lastname)) return false;
      if (modifiedDate == null) {
         if (other.modifiedDate != null) return false;
      } else if (!modifiedDate.equals(other.modifiedDate)) return false;
      if (phone == null) {
         if (other.phone != null) return false;
      } else if (!phone.equals(other.phone)) return false;
      if (title == null) {
         if (other.title != null) return false;
      } else if (!title.equals(other.title)) return false;
      return true;
   }

   private String url;
   private String title;
   private String firstname;
   private String lastname;
   private String institution;
   private String phone;
   private String email;
   private String comment;
   private String createdDate;
   private String createdByUrl;
   private String modifiedDate;
   private String modifiedByUrl;
   private Integer id;
   private Boolean archived;

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   @XmlElement(name = "created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @XmlElement(name = "modified_date")
   public String getModifiedDate() {
      return modifiedDate;
   }

   public void setModifiedDate(String modifiedDate) {
      this.modifiedDate = modifiedDate;
   }

   public Boolean getArchived() {
      return archived;
   }

   public void setArchived(Boolean archived) {
      this.archived = archived;
   }

   @XmlElement(name = "created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   @XmlElement(name = "modified_by_url")
   public String getModifiedByUrl() {
      return modifiedByUrl;
   }

   public void setModifiedByUrl(String modifiedByUrl) {
      this.modifiedByUrl = modifiedByUrl;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getInstitution() {
      return institution;
   }

   public void setInstitution(String institution) {
      this.institution = institution;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

}
