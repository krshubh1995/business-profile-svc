package com.qb.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qb.api.dao.model.Products;
import com.qb.api.dao.repository.ProductRepository;
import com.qb.api.service.ProductsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsServiceImpl implements ProductsService {

	private final ProductRepository productRepository;

	@Override
	public List<Products> addProducts(List<Products> products) {
		log.info("Adding new product");
		return productRepository.saveAll(products);
	}

	@Override
	public void deleteProducts(List<String> id) {
		 productRepository.deleteAllById(id);
	}

}
