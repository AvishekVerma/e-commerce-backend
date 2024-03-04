package com.avishek.service;

import java.util.List;

import com.avishek.exception.OrderException;
import com.avishek.model.Address;
import com.avishek.model.OrderNew;
import com.avishek.model.User;

public interface OrderService {

	public OrderNew createOrder(User user, Address shipingAddress);
	
	public OrderNew findOrderById(Long orderId) throws OrderException;
	
	public List<OrderNew> usersOrderHistory(Long userId);
	
	public OrderNew placedOrder(Long orderId) throws OrderException;
	
	public OrderNew confirmedOrder(Long orderId)throws OrderException;
	
	public OrderNew shippedOrder(Long orderId) throws OrderException;
	
	public OrderNew deliveredOrder(Long orderId) throws OrderException;
	
	public OrderNew cancledOrder(Long orderId) throws OrderException;
	
	public List<OrderNew>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
}
