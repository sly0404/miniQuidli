package com.qli.miniQuidli.accessdata;

import java.util.Optional;

public interface UserDAO
{
	public Optional<User> getById(Integer userId);
	
	void saveUser(User user);
	
}
