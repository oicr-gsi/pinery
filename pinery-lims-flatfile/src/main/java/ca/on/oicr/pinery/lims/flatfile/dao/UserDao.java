package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.List;

import ca.on.oicr.pinery.api.User;

public interface UserDao {
  
  public List<User> getAllUsers();
  public User getUser(Integer id);
  
}
