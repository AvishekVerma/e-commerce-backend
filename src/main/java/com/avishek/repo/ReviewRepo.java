package com.avishek.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avishek.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {
	
	@Query("Select r from Review r where r.product.id=:productId")
	public List<Review>getAllProductReview(@Param("productId")Long productId); 

}
