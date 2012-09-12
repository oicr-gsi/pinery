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
