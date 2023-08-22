package com.qb.api.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qb.api.dao.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
