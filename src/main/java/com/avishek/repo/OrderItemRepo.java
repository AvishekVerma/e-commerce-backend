package com.avishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avishek.model.OrderI;

public interface OrderItemRepo extends JpaRepository<OrderI, Long> {

}
