package com.qli.miniQuidli.accessdata.dao;

import java.util.Optional;

import com.qli.miniQuidli.accessdata.User;

public interface UserDAO
{
	public Optional<User> getById(Integer userId);
	
	void saveUser(User user);
	
}
