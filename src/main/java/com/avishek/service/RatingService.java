package com.avishek.service;

import java.util.List;

import com.avishek.exception.ProductException;
import com.avishek.model.Rating;
import com.avishek.model.User;
import com.avishek.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user)throws ProductException; 
	
	public List<Rating> getProductRating(Long productId);

}
