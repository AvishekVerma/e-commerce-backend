package com.avishek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avishek.exception.ProductException;
import com.avishek.model.Product;
import com.avishek.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/produts")
	public ResponseEntity<Page<Product>> findProductByCategory(
			@RequestParam String category, @RequestParam List<String>color, @RequestParam List<String> size, 
			@RequestParam Integer minPrice, @RequestParam Integer maxPrice,@RequestParam Integer minDiscount, 
			@RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, 
		 	@RequestParam Integer pageSize
			){
		
		Page<Product> res = productService.getAllProduct(
				category, color, size, minPrice, maxPrice, 
				minDiscount, sort, stock, pageNumber, pageSize);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException{
		
		Product product=productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam String q){
		
		List<Product> products=productService.searchProduct(q);
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
		
	}
	

}
