package com.avishek.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.avishek.exception.UserException;

public interface UserService {
	
	public UserDetails findUserByUsername(String username) throws UserException;
	
	
	
}
