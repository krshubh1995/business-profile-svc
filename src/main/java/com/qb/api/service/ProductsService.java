package com.qb.api.service;

import java.util.List;

import com.qb.api.dao.model.Products;

public interface ProductsService {

	public List<Products> addProducts(List<Products> products);

	public void deleteProducts(List<String> id);

}
