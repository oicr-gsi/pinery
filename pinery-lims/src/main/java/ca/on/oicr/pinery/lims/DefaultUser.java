package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.User;
import java.util.Date;

public class DefaultUser implements User {

  private Integer id;
  private String title;
  private String firstname;
  private String lastname;
  private String institution;
  private String phone;
  private String email;
  private Boolean archived;
  private String comment;
  private Date created;
  private Integer createdById;
  private Date modified;
  private Integer modifiedById;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getFirstname() {
    return firstname;
  }

  @Override
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @Override
  public String getLastname() {
    return lastname;
  }

  @Override
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Override
  public String getInstitution() {
    return institution;
  }

  @Override
  public void setInstitution(String institution) {
    this.institution = institution;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Boolean getArchived() {
    return archived;
  }

  @Override
  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  @Override
  public String getComment() {
    return comment;
  }

  @Override
  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public void setCreated(Date created) {
    this.created = created;
  }

  @Override
  public Integer getCreatedById() {
    return createdById;
  }

  @Override
  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  @Override
  public Date getModified() {
    return modified;
  }

  @Override
  public void setModified(Date modified) {
    this.modified = modified;
  }

  @Override
  public Integer getModifiedById() {
    return modifiedById;
  }

  @Override
  public void setModifiedById(Integer modifiedById) {
    this.modifiedById = modifiedById;
  }
}
