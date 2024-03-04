package com.avishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avishek.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {

//	@Query("select c from cart c where c.user_id=:userId")
	public Cart findByUserId(@Param("userId") Long userId);
}
