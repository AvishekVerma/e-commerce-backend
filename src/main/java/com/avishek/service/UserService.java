package com.avishek.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.avishek.exception.UserException;
import com.avishek.model.User;

public interface UserService {
	
	public UserDetails findUserByUsername(String username) throws UserException;
	
	public User findUserById(Long userId)throws Exception;
	
	public User findUserProfileByJwt(String jwt) throws UserException; 
	
	
	
}
