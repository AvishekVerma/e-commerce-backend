package com.avishek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avishek.exception.OrderException;
import com.avishek.model.OrderNew;
import com.avishek.response.ApiResponse;
import com.avishek.service.OrderService;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<OrderNew>> getAllOrders(){
		List<OrderNew> orders=orderService.getAllOrders();
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<OrderNew> ConfirmedOrder(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderNew order=orderService.confirmedOrder(orderId);
		return new ResponseEntity<OrderNew>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<OrderNew> shippedOrder(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderNew order=orderService.shippedOrder(orderId);
		return new ResponseEntity<OrderNew>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<OrderNew> deliveredOrder(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderNew order=orderService.deliveredOrder(orderId);
		return new ResponseEntity<OrderNew>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<OrderNew> canceledOrder(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		OrderNew order=orderService.cancledOrder(orderId);
		return new ResponseEntity<OrderNew>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
}
