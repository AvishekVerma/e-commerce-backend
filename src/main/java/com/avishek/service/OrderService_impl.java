package com.avishek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.OrderException;
import com.avishek.model.Address;
import com.avishek.model.OrderNew;
import com.avishek.model.User;
import com.avishek.repo.AddressRepo;
import com.avishek.repo.CartRepo;
import com.avishek.repo.OrderItemRepo;
import com.avishek.repo.OrderRepo;
import com.avishek.repo.UserRepo;

@Service
public class OrderService_impl implements OrderService {
	
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private OrderItemRepo orderItemRepo;
	@Autowired
	private OrderItemService orderItemService;
	

	@Override
	public OrderNew createOrder(User user, Address shipingAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderNew findOrderById(Long orderId) throws OrderException {
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
