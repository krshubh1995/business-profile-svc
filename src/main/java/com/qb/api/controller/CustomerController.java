package com.qb.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.service.CustomerService;
import com.qb.api.validations.CreateRecord;
import com.qb.api.validations.UpdateRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody @Validated(CreateRecord.class) CustomerValidate customer) {
		
		return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomer(@PathVariable String customerId) {
		var customer = customerService.getCustomer(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping("/{customerId}/status")
	public ResponseEntity<?> getCustomerStatus(@PathVariable String customerId) {
		var customer = customerService.getCustomer(customerId);

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateCustomer(@RequestBody @Validated(UpdateRecord.class) CustomerValidate customer) {
        
		return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCustomer(@RequestBody List<Customer> customers) {
		
		return new ResponseEntity<>(customerService.deleteCustomer(customers), HttpStatus.OK);
	}
}
