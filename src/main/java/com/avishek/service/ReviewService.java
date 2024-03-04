package com.avishek.service;

import java.util.List;

import com.avishek.exception.ProductException;
import com.avishek.model.Review;
import com.avishek.model.User;
import com.avishek.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest req, User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId); 

}
