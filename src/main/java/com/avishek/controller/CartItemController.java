package com.avishek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avishek.exception.CartItemException;
import com.avishek.exception.UserException;
import com.avishek.model.CartItem;
import com.avishek.model.User;
import com.avishek.response.ApiResponse;
import com.avishek.service.CartItemService;
import com.avishek.service.UserService;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private UserService userService;
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse>deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res=new ApiResponse("Item Remove From Cart",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
				
		return new ResponseEntity<>(updatedCartItem,HttpStatus.CREATED);
	}
}

