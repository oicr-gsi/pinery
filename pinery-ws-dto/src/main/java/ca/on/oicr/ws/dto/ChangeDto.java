package ca.on.oicr.ws.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.joda.time.DateTime;

@XmlRootElement(name = "change")
@JsonAutoDetect
@JsonSerialize(include = Inclusion.NON_NULL)
public class ChangeDto {

   private String action;
   @JsonProperty("created_by_id")
   private Integer createdById;
   @JsonProperty("created_date")
   private String createdDate;
   private DateTime created;
   @JsonProperty("created_by_url")
   private String createdByUrl;
   private String comment;

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   @XmlElement(name = "created_date")
   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   @XmlElement(name = "created_by_url")
   public String getCreatedByUrl() {
      return createdByUrl;
   }

   public void setCreatedByUrl(String createdByUrl) {
      this.createdByUrl = createdByUrl;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   @XmlTransient
   public Integer getCreatedById() {
      return createdById;
   }

   public void setCreatedById(Integer createdById) {
      this.createdById = createdById;
   }

   @XmlTransient
   public DateTime getCreated() {
      return created;
   }

   public void setCreated(DateTime created) {
      this.created = created;
   }

}
