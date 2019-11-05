package ca.on.oicr.pinery.flatfile.writer;

import ca.on.oicr.ws.dto.UserDto;
import java.util.List;
import java.util.Objects;

public class UserWriter extends Writer {

  private static final String[] headers = {
    "id",
    "title",
    "firstName",
    "lastName",
    "institution",
    "phone",
    "email",
    "comment",
    "archived",
    "createdDate",
    "createdUserId",
    "modifiedDate",
    "modifiedUserId"
  };

  private final List<UserDto> users;

  public UserWriter(List<UserDto> users) {
    this.users = users;
  }

  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return users.size();
  }

  @Override
  protected String[] getRecord(int row) {
    UserDto user = users.get(row);

    String[] data = {
      Objects.toString(user.getId(), ""),
      user.getTitle(),
      user.getFirstname(),
      user.getLastname(),
      user.getInstitution(),
      user.getPhone(),
      user.getEmail(),
      user.getComment(),
      Objects.toString(user.getArchived(), ""),
      user.getCreatedDate(),
      Objects.toString(user.getCreatedById(), ""),
      user.getModifiedDate(),
      Objects.toString(user.getModifiedById(), "")
    };

    return data;
  }
}
