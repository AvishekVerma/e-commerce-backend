package com.avishek.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avishek.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
