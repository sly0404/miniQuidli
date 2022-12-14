package com.qli.miniQuidli.accessdata.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.qli.miniQuidli.accessdata.User;
import com.qli.miniQuidli.accessdata.UserRepository;

public class UserDAOImplementation implements UserDAO
{
	
	private UserRepository userRepository;
	
	
	public UserDAOImplementation(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@Override
	public Optional<User> getById(Integer userId)
	{
		return userRepository.findById(userId);
	}

	@Override
	public void saveUser(User user)
	{
		userRepository.save(user);
	}
}
