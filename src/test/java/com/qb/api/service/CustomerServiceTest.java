package com.qb.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.ValidationStatusEnum;
import com.qb.api.dao.repository.CustomerRepository;
import com.qb.api.dao.repository.CustomersValidateRepository;
import com.qb.api.model.ApiResponse;
import com.qb.api.service.impl.CustomerServiceImpl;

@TestPropertySource(properties = "app.scheduling.enable=false")
class CustomerServiceTest {

	ObjectMapper mapper = new ObjectMapper();

	private CustomersValidateRepository customersValidateRepository = Mockito.mock(CustomersValidateRepository.class);
	private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private MongoTemplate template = Mockito.mock(MongoTemplate.class);
	
	@InjectMocks
	private CustomerService customerService = new CustomerServiceImpl(customersValidateRepository, customerRepository, objectMapper, template);	

	@Test
	void customerCreateTest() throws Exception {
		String request = "{\n" + "  \"compnayName\": \"string\",\n" + "  \"legalName\": \"string\",\n"
				+ "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n" + "  \"website\": \"string\",\n"
				+ "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n" + "    \"line2\": \"string\",\n"
				+ "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n" + "    \"zip\": \"string\",\n"
				+ "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n" + "    \"pan\": \"string\",\n"
				+ "    \"ein\": \"string\"\n" + "  },\n" + "  \"sqlOperation\": \"SAVE\",\n"
				+ "  \"validatedProductsError\": [\n" + "    {\n" + "      \"productName\": \"string\",\n"
				+ "      \"error\": \"In_Progress\",\n" + "      \"cause\": \"string\",\n"
				+ "      \"retryValidation\": true,\n" + "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";
		
		var requestBody = mapper.readValue(request, CustomerValidate.class);
		when(customersValidateRepository.save(any())).thenReturn(requestBody);
		var actualResponse = customerService.createCustomer(requestBody);

		assertEquals(actualResponse.getMessage(), "We have accepted your request, the record is being validated");
	}

	@Test
	void customerUpdateTest() throws Exception {
		String request = "{\n" + "  \"customerId\": \"64e3bb4892a70a4570b1bad7\",\n"
				+ "  \"compnayName\": \"Intuit\",\n" + "  \"legalName\": \"string\",\n"
				+ "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n" + "  \"website\": \"string\",\n"
				+ "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n" + "    \"line2\": \"string\",\n"
				+ "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n" + "    \"zip\": \"string\",\n"
				+ "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n" + "    \"pan\": \"string\",\n"
				+ "    \"ein\": \"string\"\n" + "  },\n" + "  \"sqlOperation\": \"SAVE\",\n"
				+ "  \"validatedProductsError\": [\n" + "    {\n" + "      \"productName\": \"string\",\n"
				+ "      \"error\": \"In_Progress\",\n" + "      \"cause\": \"string\",\n"
				+ "      \"retryValidation\": true,\n" + "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";
		
		var requestBody = mapper.readValue(request, CustomerValidate.class);
		when(customersValidateRepository.save(any())).thenReturn(requestBody);
		var actualResponse = customerService.updateCustomer(requestBody);

		assertEquals(actualResponse.getMessage(), "We have accepted your update request, the record is being validated");
	}

	@Test
	void customerDeleteTest() throws Exception {
		String request = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n" + "  \"compnayName\": \"Intuit\",\n"
				+ "  \"legalName\": \"string\",\n" + "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n"
				+ "  \"website\": \"string\",\n" + "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n"
				+ "    \"line2\": \"string\",\n" + "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n"
				+ "    \"zip\": \"string\",\n" + "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n"
				+ "    \"pan\": \"string\",\n" + "    \"ein\": \"string\"\n" + "  },\n"
				+ "  \"sqlOperation\": \"SAVE\",\n" + "  \"validatedProductsError\": [\n" + "    {\n"
				+ "      \"productName\": \"string\",\n" + "      \"error\": \"In_Progress\",\n"
				+ "      \"cause\": \"string\",\n" + "      \"retryValidation\": true,\n"
				+ "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";
		ApiResponse response = ApiResponse.builder().id("test-id").message("Deleted Successfully").build();
		var requestBody = mapper.readValue(request, Customer.class);
		when(customersValidateRepository.save(any())).thenReturn(response);
		var actualResponse = customerService.deleteCustomer(List.of(requestBody));

		assertEquals(actualResponse.getMessage(), response.getMessage());
	}

	@Test
	void customerGetTest() throws Exception {
		String response = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n" + "  \"compnayName\": \"Intuit\",\n"
				+ "  \"legalName\": \"string\",\n" + "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n"
				+ "  \"website\": \"string\",\n" + "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n"
				+ "    \"line2\": \"string\",\n" + "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n"
				+ "    \"zip\": \"string\",\n" + "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n"
				+ "    \"pan\": \"string\",\n" + "    \"ein\": \"string\"\n" + "  },\n"
				+ "  \"sqlOperation\": \"SAVE\",\n" + "  \"validatedProductsError\": [\n" + "    {\n"
				+ "      \"productName\": \"string\",\n" + "      \"error\": \"In_Progress\",\n"
				+ "      \"cause\": \"string\",\n" + "      \"retryValidation\": true,\n"
				+ "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";

		var responseBody = mapper.readValue(response, Customer.class);
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(responseBody));
		var actualResponse = customerService.getCustomer("cust-id");

		assertTrue(Objects.nonNull(actualResponse));
	}

	@Test
	void customerGetTest_2() throws Exception {
		String response = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n" + "  \"compnayName\": \"Intuit\",\n"
				+ "  \"legalName\": \"string\",\n" + "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n"
				+ "  \"website\": \"string\",\n" + "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n"
				+ "    \"line2\": \"string\",\n" + "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n"
				+ "    \"zip\": \"string\",\n" + "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n"
				+ "    \"pan\": \"string\",\n" + "    \"ein\": \"string\"\n" + "  },\n"
				+ "  \"sqlOperation\": \"SAVE\",\n" + "  \"validatedProductsError\": [\n" + "    {\n"
				+ "      \"productName\": \"string\",\n" + "      \"error\": \"In_Progress\",\n"
				+ "      \"cause\": \"string\",\n" + "      \"retryValidation\": true,\n"
				+ "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";

		var responseBody = mapper.readValue(response, Customer.class);
		when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
		var actualResponse = customerService.getCustomer("cust-id");

		assertTrue(Objects.nonNull(actualResponse));
	}

	@Test
	void customerGetStatusTest() throws Exception {
		String response = "{\n" + "  \"tempId\": \"64e3bb4892a70a4570b1bad7\",\n" + "  \"compnayName\": \"Intuit\",\n"
				+ "  \"legalName\": \"string\",\n" + "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n"
				+ "  \"website\": \"string\",\n" + "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n"
				+ "    \"line2\": \"string\",\n" + "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n"
				+ "    \"zip\": \"string\",\n" + "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n"
				+ "    \"pan\": \"string\",\n" + "    \"ein\": \"string\"\n" + "  },\n"
				+ "  \"sqlOperation\": \"SAVE\",\n" + "  \"validatedProductsError\": [\n" + "    {\n"
				+ "      \"productName\": \"string\",\n" + "      \"error\": \"In_Progress\",\n"
				+ "      \"cause\": \"string\",\n" + "      \"retryValidation\": true,\n"
				+ "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";

		var responseBody = mapper.readValue(response, CustomerValidate.class);
		when(customersValidateRepository.findById(anyString())).thenReturn(Optional.of(responseBody));
		var actualResponse = customerService.getCustomerStatus("cust-id");

		assertTrue(Objects.nonNull(actualResponse));
	}

	@Test
	void customerGetStatusTest2() throws Exception {
		String response = "{\n" + "  \"tempId\": \"64e3bb4892a70a4570b1bad7\",\n" + "  \"compnayName\": \"Intuit\",\n"
				+ "  \"legalName\": \"string\",\n" + "  \"legalAddress\": \"string\",\n" + "  \"email\": \"string\",\n"
				+ "  \"website\": \"string\",\n" + "  \"businessAddress\": {\n" + "    \"line1\": \"string\",\n"
				+ "    \"line2\": \"string\",\n" + "    \"city\": \"string\",\n" + "    \"state\": \"string\",\n"
				+ "    \"zip\": \"string\",\n" + "    \"country\": \"string\"\n" + "  },\n" + "  \"taxIdentifier\": {\n"
				+ "    \"pan\": \"string\",\n" + "    \"ein\": \"string\"\n" + "  },\n"
				+ "  \"sqlOperation\": \"SAVE\",\n" + "  \"validatedProductsError\": [\n" + "    {\n"
				+ "      \"productName\": \"string\",\n" + "      \"error\": \"In_Progress\",\n"
				+ "      \"cause\": \"string\",\n" + "      \"retryValidation\": true,\n"
				+ "      \"retryValidationCount\": 0\n" + "    }\n" + "  ],\n"
				+ "  \"validationStatus\": \"In_Progress\"\n" + "}";

		var responseBody = mapper.readValue(response, Customer.class);
		when(customersValidateRepository.findById(anyString())).thenReturn(Optional.empty());
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(responseBody));

		var actualResponse = customerService.getCustomerStatus("cust-id");

		assertTrue(Objects.nonNull(actualResponse));
	}
}
