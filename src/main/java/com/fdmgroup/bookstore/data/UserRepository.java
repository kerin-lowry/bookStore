package com.fdmgroup.bookstore.data;

import com.fdmgroup.bookstore.model.User;

public interface UserRepository<T, ID> extends Searchable<T, ID> {
	
	public boolean validate(String username, String password);
	
	public User findByUsername(String username);
	
}
