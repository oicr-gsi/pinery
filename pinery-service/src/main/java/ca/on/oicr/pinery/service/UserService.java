package ca.on.oicr.pinery.service;

import ca.on.oicr.pinery.api.User;
import java.util.List;

public interface UserService {

  public List<User> getUsers();

  public User getUser(Integer id);
}
