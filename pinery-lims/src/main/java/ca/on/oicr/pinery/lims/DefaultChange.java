package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Change;
import java.util.Date;

public class DefaultChange implements Change {

  private String action;
  private Integer createdById;
  private Date created;
  private String comment;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Integer getCreatedById() {
    return createdById;
  }

  public void setCreatedById(Integer createdById) {
    this.createdById = createdById;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
