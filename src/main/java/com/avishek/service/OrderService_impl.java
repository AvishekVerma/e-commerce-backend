package com.avishek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.OrderException;
import com.avishek.model.Address;
import com.avishek.model.OrderNew;
import com.avishek.model.User;
import com.avishek.repo.CartRepo;

@Service
public class OrderService_impl implements OrderService {
	
	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private CartService cartItemService;
	@Autowired
	private ProductService productService;

	@Override
	public OrderNew createOrder(User user, Address shipingAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew findOrderById(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderNew> usersOrderHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew placedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew confirmedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew shippedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew deliveredOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew cancledOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderNew> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		
	}


}
