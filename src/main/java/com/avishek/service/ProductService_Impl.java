package com.avishek.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator.OfDouble;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.avishek.exception.ProductException;
import com.avishek.model.Category;
import com.avishek.model.Product;
import com.avishek.repo.CategoryRepo;
import com.avishek.repo.ProductRepo;
import com.avishek.request.CreateProductRequest;

@Service
public class ProductService_Impl implements ProductService {

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLevel = categoryRepo.findByName(req.getTopLevelCategory());
		if (topLevel == null) {
			Category topCat = new Category();
			topCat.setName(req.getTopLevelCategory());
			topCat.setLevel(1);

			topLevel = categoryRepo.save(topCat);
		}
		Category secondLevel = categoryRepo.findByNameAndParant(req.getSecondLevelCategory(), topLevel.getName());
		if (secondLevel == null) {

			Category secondCat = new Category();
			secondCat.setName(req.getSecondLevelCategory());
			secondCat.setParentCategory(topLevel);
			secondCat.setLevel(2);

			secondLevel = categoryRepo.save(secondCat);
		}

		Category thirdLevel = categoryRepo.findByNameAndParant(req.getThirdLevelCategory(), secondLevel.getName());
		if (thirdLevel == null) {

			Category thirdCat = new Category();
			thirdCat.setName(req.getThirdLevelCategory());
			thirdCat.setParentCategory(secondLevel);
			thirdCat.setLevel(3);

			thirdLevel = categoryRepo.save(thirdCat);
		}

		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscount(req.getDiscount());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSizes());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = productRepo.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepo.delete(product);

		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {

		Product product = findProductById(productId);
		if (req.getQuantity() != 0) {
			product.setQuantity(req.getQuantity());
		}
		Product updatedProduct = productRepo.save(product);

		return updatedProduct;
	}

	@Override
	public Product findProductById(Long id) throws ProductException {

		Optional<Product> optional = Optional.ofNullable(productRepo.findById(id).get());

		if (optional.isPresent()) {
			return optional.get();
		}
		throw new ProductException("Product not found with id - " + id);

	}

	@Override
	public List<Product> findProductByCategory(String category) {
		List<Product> products = productRepo.findByCategory(category);

		return products;
	}

	@Override
	public List<Product> searchProduct(String query) {
		List<Product> products = productRepo.searchProduct(query);
		return products;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageble = PageRequest.of(pageNumber, pageSize);

		List<Product> products = productRepo.filterProducts(category, minPrice, maxPrice, minDiscount, stock);

		if (!colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}
		int startIndex = (int) pageble.getOffset();
		int endIndex = Math.min((startIndex + pageble.getPageSize()), products.size());

		List<Product> pageContent = products.subList(startIndex, endIndex);

		Page<Product> filteredProduct = new PageImpl<Product>(pageContent, pageble, products.size());

		return filteredProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public List<Product> recentlyAddedProduct() {

		return productRepo.findTop10ByOrderByCreatedAtDesc();
	}

}
