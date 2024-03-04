package com.avishek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.ProductException;
import com.avishek.model.Cart;
import com.avishek.model.CartItem;
import com.avishek.model.Product;
import com.avishek.model.User;
import com.avishek.repo.CartRepo;
import com.avishek.repo.ProductRepo;
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

		Cart cart = cartRepo.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize());
		
		if(isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createCartItem);
		}
		
		return "Item Add To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {

		Cart cart = cartRepo.findByUserId(userId);
		
		int totalPrice = 0;
		int totalDicountedPrice = 0;
		int totalItem = 0;
		
		for(CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDicountedPrice = totalDicountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDicountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice - totalDicountedPrice);
		
		return cartRepo.save(cart);
	}

}
