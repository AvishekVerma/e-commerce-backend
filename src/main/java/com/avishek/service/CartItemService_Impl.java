package com.avishek.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.CartItemException;
import com.avishek.exception.UserException;
import com.avishek.model.Cart;
import com.avishek.model.CartItem;
import com.avishek.model.Product;
import com.avishek.model.User;
import com.avishek.repo.CartItemRepo;
import com.avishek.repo.CartRepo;

@Service
public class CartItemService_Impl implements CartItemService {
	
	@Autowired
	private CartItemRepo cartItemRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private CartRepo cartRepo;

	@Override
	public CartItem createCartItem(CartItem cartItem) {

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepo.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException,UserException {

		CartItem item = findCartItemById(id);
		User user;
		try {
			user = userService.findUserById(userId);
			if(user.getId().equals(userId)) {
				item.setQuantity(cartItem.getQuantity());
				item.setPrice(item.getQuantity() * item.getProduct().getPrice());
				item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size) {

		CartItem cartItem = cartItemRepo.isCartItemExist(cart, product, size);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

		CartItem cartItem = findCartItemById(cartItemId);
		
		try {
			User user = userService.findUserById(cartItem.getUserId());
			User reqUser = userService.findUserById(userId);
			if(user.getId().equals(reqUser.getId())) {
				cartItemRepo.deleteById(cartItemId);
			}else {
				throw new UserException("You can't remove other users item");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {

		Optional<CartItem> opt = cartItemRepo.findById(cartItemId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cart item nowt found with id : "+cartItemId);
	}

}
