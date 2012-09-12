package ca.on.oicr.pinery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.service.UserService;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	private Lims lims;

	@Override
	public User getUser(Integer id) {
		return lims.getUser(id);
	}

	@Override
	public List<User> getUsers() {
		return lims.getUsers();
	}
}
