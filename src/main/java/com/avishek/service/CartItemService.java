package com.avishek.service;

import com.avishek.exception.CartItemException;
import com.avishek.exception.UserException;
import com.avishek.model.Cart;
import com.avishek.model.CartItem;
import com.avishek.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart, Product product, String size);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException; 
}
