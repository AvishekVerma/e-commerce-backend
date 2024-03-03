package com.avishek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avishek.AppConfig.JwtProvider;
import com.avishek.exception.UserException;
import com.avishek.model.User;
import com.avishek.repo.UserRepo;
import com.avishek.request.LoginRequest;
import com.avishek.response.AuthResponse;
import com.avishek.service.CustomUserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetails customUserDetails;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws UserException{
		
		
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		User isEmailExist = userRepo.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email is already used with other account");
		}
		
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser = userRepo.save(createdUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse= new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED); 
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
		
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		Authentication authentication = authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse= new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Signin Success");
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = customUserDetails.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username...");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password...");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
}
