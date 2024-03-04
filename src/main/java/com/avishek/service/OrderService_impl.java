package com.avishek.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.OrderException;
import com.avishek.model.Address;
import com.avishek.model.Cart;
import com.avishek.model.CartItem;
import com.avishek.model.OrderI;
import com.avishek.model.OrderNew;
import com.avishek.model.User;
import com.avishek.repo.AddressRepo;
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
		shipingAddress.setUser(user);
		Address address = addressRepo.save(shipingAddress);
		user.getAddress().add(address);
		userRepo.save(user);

		Cart cart = cartService.findUserCart(user.getId());
		List<OrderI> orderItems = new ArrayList<>();

		for (CartItem item : cart.getCartItems()) {
			OrderI orderItem = new OrderI();

			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());

			OrderI createdOrderItem = orderItemRepo.save(orderItem);

			orderItems.add(createdOrderItem);
		}

		OrderNew createdOrder = new OrderNew();
		createdOrder.setUser(user);
		createdOrder.setOrderIs(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());

		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());

		OrderNew savedOrder = orderRepo.save(createdOrder);

		for (OrderI item : orderItems) {
			item.setOrder(savedOrder);
			orderItemRepo.save(item);
		}

		return savedOrder;
	}

	@Override
	public OrderNew findOrderById(Long orderId) throws OrderException {
		Optional<OrderNew> opt = orderRepo.findById(orderId);

		if (opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id " + orderId);
	}

	@Override
	public List<OrderNew> usersOrderHistory(Long userId) {
		List<OrderNew> orders = orderRepo.getUsersOrders(userId);
		return orders;
	}

	@Override
	public OrderNew placedOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return order;
	}

	@Override
	public OrderNew confirmedOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");

		return orderRepo.save(order);
	}

	@Override
	public OrderNew shippedOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepo.save(order);
	}

	@Override
	public OrderNew deliveredOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepo.save(order);
	}

	@Override
	public OrderNew cancledOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepo.save(order);
	}

	@Override
	public List<OrderNew> getAllOrders() {
		return orderRepo.findAllByOrderByCreatedAtDesc();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		OrderNew order = findOrderById(orderId);

		orderRepo.deleteById(orderId);

	}

}
