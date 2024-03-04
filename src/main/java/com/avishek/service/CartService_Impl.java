package com.avishek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.ProductException;
import com.avishek.model.Cart;
import com.avishek.model.User;
import com.avishek.repo.CartRepo;
import com.avishek.request.AddItemRequest;

@Service
public class CartService_Impl implements CartService {
	
	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;

	@Override
	public Cart createUser(User user) {

		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepo.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

//		Cart cart = cartRepo.findby
		
		return null;
	}

	@Override
	public Cart findUserCart(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
