package com.avishek.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avishek.AppConfig.JwtProvider;
import com.avishek.exception.UserException;
import com.avishek.repo.UserRepo;
import com.avishek.model.User;

@Service
public class UserService_Impl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public UserDetails findUserByUsername(String username) throws UserException {

		User user = userRepo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email - "+username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public User findUserById(Long userId) throws Exception {
		
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("user not found with id : "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = userRepo.findByEmail(email);
		if(user==null) {
			throw new UserException("user not found with email "+email);
		}
		return user;
	}

}
