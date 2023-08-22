package com.qb.api.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.qb.api.constants.ServiceConstants;
import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.SqlOperations;
import com.qb.api.dao.model.ValidationStatusEnum;
import com.qb.api.dao.repository.CustomerRepository;
import com.qb.api.dao.repository.CustomersValidateRepository;
import com.qb.api.model.ApiResponse;
import com.qb.api.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final CustomersValidateRepository customersValidateRepository;
	private final CustomerRepository customerRepository;
	private final ObjectMapper objectMapper;
	private final MongoTemplate template;

	@Override
	public ApiResponse createCustomer(CustomerValidate customer) {
		log.info("creating customer with company: {}", customer.getCompnayName());
		customer.setSqlOperation(SqlOperations.SAVE);
		customer.setValidatedProductsError(List.of());
		customer.setValidationStatus(ValidationStatusEnum.In_Progress);
		var savedCustomer = customersValidateRepository.save(customer);
		log.info("created customer with company: {} Successfully", customer.getCompnayName());
		return ApiResponse.builder().id(savedCustomer.getId())
				.message("We have accepted your request, the record is being validated").build();

	}

	@Override
	public Customer getCustomer(String customerId) {
		return customerRepository.findById(customerId).orElseGet(() -> new Customer());
	}

	@Override
	public Customer getCustomerStatus(String customerId) {
		var customerEntity = customersValidateRepository.findById(customerId);
		var customerResponse = new Customer();

		customerEntity.ifPresentOrElse(customer -> {
			log.info("fetched data from CustomerValidation collection");
			customer.setId(customer.getId());
			customer.setValidationStatus(customer.getValidationStatus());
		}, () -> {
			log.info("fetched data from Customer collection");
			var customer = customerRepository.findById(customerId).get();
			customerResponse.setId(customer.getId());
			customerResponse.setValidationStatus(customer.getValidationStatus());
		});
		return customerResponse;

	}

	@Override
	public ApiResponse updateCustomer(CustomerValidate customer) {
		customer.setSqlOperation(SqlOperations.UPDATE);
		customer.setValidatedProductsError(List.of());
		customer.setValidationStatus(ValidationStatusEnum.In_Progress);
		var upateRequest = customersValidateRepository.save(customer);
		return ApiResponse.builder().id(upateRequest.getId())
				.message("We have accepted your update request, the record is being validated").build();

	}

	@Override
	public ApiResponse deleteCustomer(List<Customer> customers) {
		customerRepository.deleteAll(customers);
		return ApiResponse.builder().message("Deleted Successfully").build();

	}

	@SuppressWarnings("unchecked")
	@Override
	public UpdateResult update(Customer c) {
		Query query;
		final Update updateDef = new Update();
		Map<String, Object> dataToUpdate = objectMapper.convertValue(c, new TypeReference<Map<String, Object>>() {
		});
		dataToUpdate.values().removeIf(Objects::isNull);
		dataToUpdate.forEach((key, value) -> {
			if (value.getClass() == LinkedHashMap.class) {
				((LinkedHashMap<String, Object>) value).forEach((k, v) -> {
					updateDef.set(key.concat(ServiceConstants.FIELD_SEPARATOR_DOT).concat((String) k), v);
				});
			} else {
				updateDef.set(key, value);
			}

		});
		query = new Query().addCriteria(Criteria.where("id").is(c.getId()));
		return template.upsert(query, updateDef, Customer.class);
	}
}
