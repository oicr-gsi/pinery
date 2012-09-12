package ca.on.oicr.pinery.service;

import java.util.List;

import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.User;

public interface UserService {

	public List<User> getUsers();

	public User getUser(Integer id);
}
