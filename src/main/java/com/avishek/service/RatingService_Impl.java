package com.avishek.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avishek.exception.ProductException;
import com.avishek.model.Product;
import com.avishek.model.Rating;
import com.avishek.model.User;
import com.avishek.repo.RatingRepo;
import com.avishek.request.RatingRequest;

@Service
public class RatingService_Impl implements RatingService {

	@Autowired
	private RatingRepo ratingRepo;
	@Autowired
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		return ratingRepo.getAllProductRating(productId);
	}

}
