package com.fdmgroup.bookstore.service;

import com.fdmgroup.bookstore.data.UserRepository;
import com.fdmgroup.bookstore.model.User;

public class AuthenticationService {
	
	private UserRepository<User, Integer> userRepository;

	public AuthenticationService(UserRepository<User, Integer> userrepository) {
		super();
		this.userRepository = userrepository;
	}
	
	public AuthenticationService() {
		super();
	}

	public User authenticate(String username, String password) throws UserNotFoundException {
		if (userRepository.validate(username, password)) {
			return userRepository.findByUsername(username);
		}
		throw new UserNotFoundException("User could not be validated.");
	}

	public User findById(int userId) throws UserNotFoundException {
		if (userRepository.findById(userId) != null) {
			return userRepository.findById(userId);
		}
		throw new UserNotFoundException("User could not be found."); 
	}
	
}
