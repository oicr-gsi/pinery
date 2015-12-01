package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultUser;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

public class UserFileDao implements UserDao {
  
  private static final String queryAllUsers = "SELECT * FROM users";
  
  private static final String queryUserById = queryAllUsers
      + " WHERE id LIKE ?";
  
  private static final RowMapper<User> userMapper = new RowMapper<User>() {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User u = new DefaultUser();
      
      u.setId(rs.getInt("id"));
      u.setTitle(rs.getString("title"));
      u.setFirstname(rs.getString("firstName"));
      u.setLastname(rs.getString("lastName"));
      u.setInstitution(rs.getString("institution"));
      u.setPhone(rs.getString("phone"));
      u.setEmail(rs.getString("email"));
      u.setArchived(rs.getBoolean("archived"));
      
      u.setCreated(ModelUtils.convertToDate(rs.getString("createdDate")));
      int creator = rs.getInt("createdUserId");
      if (creator != 0) u.setCreatedById(creator);
      
      u.setModified(ModelUtils.convertToDate(rs.getString("modifiedDate")));
      int modifier = rs.getInt("modifiedUserId");
      if (modifier != 0) u.setModifiedById(modifier);
      
      return u;
    }
    
  };
  
  @Autowired
  private JdbcTemplate template;
  
  @Override
  public List<User> getAllUsers() {
    return template.query(queryAllUsers, userMapper);
  }

  @Override
  public User getUser(Integer id) {
    List<User> users = template.query(queryUserById, new Object[]{id}, userMapper);
    return DaoUtils.getExpectedSingleResult(users);
  }

}
