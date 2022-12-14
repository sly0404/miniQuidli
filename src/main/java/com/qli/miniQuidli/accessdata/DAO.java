package com.qli.miniQuidli.accessdata;

import java.util.Optional;

public interface DAO
{
	public Optional<User> getById(Integer userId);
	
	void saveUser(User user);
	
}
