package com.qb.api.service;

import java.util.List;

import com.mongodb.client.result.UpdateResult;
import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.model.ApiResponse;

public interface CustomerService {

	public ApiResponse createCustomer(CustomerValidate customer);

	public Customer getCustomer(String customerId);

	public Customer getCustomerStatus(String customerId);

	public ApiResponse updateCustomer(CustomerValidate customer);

	public ApiResponse deleteCustomer(List<Customer> customer);

	public UpdateResult update(Customer c);

}
