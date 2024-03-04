package com.avishek.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avishek.model.OrderNew;

public interface OrderRepo extends JpaRepository<OrderNew, Long> {
	
	@Query("SELECT o FROM OrderNew o "
			+ "WHERE o.user.id = :userId "
			+ "AND (o.orderStatus = 'PLACED' "
			+ "OR o.orderStatus = 'CONFIRMED' "
			+ "OR o.orderStatus = 'SHIPPED' "
			+ "OR o.orderStatus = 'DELIVERED')")
	public List<OrderNew> getUsersOrders(@Param("userId") Long userId);
	
	List<OrderNew> findAllByOrderByCreatedAtDesc();

}
