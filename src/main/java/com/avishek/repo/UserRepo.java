package com.avishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avishek.model.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
}
