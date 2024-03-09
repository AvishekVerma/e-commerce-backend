package com.avishek.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.ProductException;
import com.avishek.model.Product;
import com.avishek.model.Review;
import com.avishek.model.User;
import com.avishek.repo.ProductRepo;
import com.avishek.repo.ReviewRepo;
import com.avishek.request.ReviewRequest;

@Service
public class ReviewService_Impl implements ReviewService {
	
	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepo productRepo;

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {

		return reviewRepo.getAllProductReview(productId);
	}

}
