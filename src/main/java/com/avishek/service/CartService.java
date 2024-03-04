package com.avishek.service;

import com.avishek.exception.ProductException;
import com.avishek.model.Cart;
import com.avishek.model.User;
import com.avishek.request.AddItemRequest;

public interface CartService {

	public Cart createUser(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
