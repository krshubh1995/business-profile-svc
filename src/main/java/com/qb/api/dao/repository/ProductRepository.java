package com.qb.api.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qb.api.dao.model.Products;

public interface ProductRepository extends MongoRepository<Products, String> {

	 List<Products> findAllByNameIn(List<String> name);
}
