package ca.on.oicr.pinery.lims.flatfile.dao;

import ca.on.oicr.pinery.api.User;
import java.util.List;

public interface UserDao {

  /** @return a List of all Users */
  public List<User> getAllUsers();

  /**
   * Retrieves a single User by User ID
   *
   * @param id ID of the User to retrieve
   * @return the User if one is found with the provided User ID; otherwise null
   */
  public User getUser(Integer id);
}
