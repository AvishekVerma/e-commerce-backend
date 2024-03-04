package com.avishek.service;

import org.springframework.stereotype.Service;

import com.avishek.model.OrderI;
import com.avishek.repo.OrderItemRepo;

@Service
public class OrderItemService_Impl implements OrderItemService {

	private OrderItemRepo orderItemRepo;
	@Override
	public OrderI createOrderItem(OrderI orderItem) {
		return orderItemRepo.save(orderItem);
	}

}
