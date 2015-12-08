package ca.on.oicr.pinery.flatfile.writer;

import java.util.List;

import ca.on.oicr.ws.dto.UserDto;

public class UserWriter extends Writer {
  
  private static final String[] headers = {
    "id",
    "title",
    "firstName",
    "lastName",
    "institution",
    "phone",
    "email",
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
        user.getId().toString(),
        user.getTitle(),
        user.getFirstname(),
        user.getLastname(),
        user.getInstitution(),
        user.getPhone(),
        user.getEmail(),
        user.getArchived().toString(),
        user.getCreatedDate(),
        user.getCreatedById().toString(),
        user.getModifiedDate(),
        user.getModifiedById().toString()
    };
    
    return data;
  }
  
}
