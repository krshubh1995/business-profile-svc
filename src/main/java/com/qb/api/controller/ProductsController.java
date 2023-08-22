package com.qb.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qb.api.dao.model.Products;
import com.qb.api.service.ProductsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductsController {

	private final ProductsService productsService;

	@PostMapping
	public ResponseEntity<?> addProducts(@RequestBody List<Products> products) {
		var productResponse = productsService.addProducts(products);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteProducts(@RequestBody List<String> productId) {
		productsService.deleteProducts(productId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
