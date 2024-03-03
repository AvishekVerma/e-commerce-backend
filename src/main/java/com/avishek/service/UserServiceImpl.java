package com.avishek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avishek.exception.UserException;
import com.avishek.repo.UserRepo;
import com.avishek.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	
	private UserRepo userRepo;


	@Override
	public UserDetails findUserByUsername(String username) throws UserException {

		User user = userRepo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email - "+username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
