package com.avishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avishek.model.Cart;
import com.avishek.model.CartItem;
import com.avishek.model.Product;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{

	@Query("select ci From CartItem ci "
			+ "where ci.cart=:cart "
			+ "and ci.product=:product "
			+ "and ci.size=:size ")
	public CartItem isCartItemExist(
			@Param("cart")Cart cart, 
			@Param("product") Product product, 
			@Param("size") String size
			);
}
