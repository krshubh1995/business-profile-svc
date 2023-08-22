package com.qb.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.ValidationStatusEnum;
import com.qb.api.model.ApiResponse;
import com.qb.api.service.CustomerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enable=false")
class CustomerControllerTest {


	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	private CustomerService customerService;
	ObjectMapper mapper = new ObjectMapper();
	private final String CUSTOMER_URL = "/v1/customers";

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
		ApiResponse response = ApiResponse.builder().id("test-id")
				.message("We have accepted your request, the record is being validated").build();
		var requestBody = mapper.readValue(request, CustomerValidate.class);
		when(customerService.createCustomer(any())).thenReturn(response);

		var testResponse = restTemplate.postForEntity(CUSTOMER_URL, requestBody, ApiResponse.class);

		assertEquals(testResponse.getBody().getMessage(), response.getMessage());
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
		ApiResponse response = ApiResponse.builder().id("test-id")
				.message("We have accepted your update request, the record is being validated").build();
		var requestBody = mapper.readValue(request, CustomerValidate.class);
		URI url = URI.create(CUSTOMER_URL);
		HttpEntity<CustomerValidate> httpRequest = new HttpEntity<CustomerValidate>(requestBody);
		when(customerService.updateCustomer(any())).thenReturn(response);
		var testResponse = restTemplate.exchange(url, HttpMethod.PUT, httpRequest, ApiResponse.class);
		 assertEquals(testResponse.getBody().getMessage(), response.getMessage());
	}

	@Test
	void customerDeleteTest() throws Exception {
		String request = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n"
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
		ApiResponse response = ApiResponse.builder().id("test-id")
				.message("Deleted Successfully").build();
		var requestBody =List.of( mapper.readValue(request, Customer.class));
		URI url = URI.create(CUSTOMER_URL);
		HttpEntity<List<Customer>> httpRequest = new HttpEntity<List<Customer>>(requestBody);
		when(customerService.deleteCustomer(any())).thenReturn(response);
		var testResponse = restTemplate.exchange(url, HttpMethod.DELETE, httpRequest, ApiResponse.class);
		 assertEquals(testResponse.getBody().getMessage(), response.getMessage());
	}
	
	
	@Test
	void customerGetTest() throws Exception {
		String response = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n"
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
		
		var responseBody =mapper.readValue(response, Customer.class);
		URI url = URI.create(CUSTOMER_URL+"/64e3bb4892a70a4570b1bad7");
		when(customerService.getCustomer(anyString())).thenReturn(responseBody);
		var testResponse = restTemplate.getForObject(url, Customer.class);
		 assertTrue(Objects.nonNull(testResponse));
	}
	
	@Test
	void customerGetStatusTest() throws Exception {
		String response = "{\n" + "  \"id\": \"64e3bb4892a70a4570b1bad7\",\n"
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
		
		var responseBody =mapper.readValue(response, Customer.class);
		URI url = URI.create(CUSTOMER_URL+"/64e3bb4892a70a4570b1bad7/status");
		when(customerService.getCustomer(anyString())).thenReturn(responseBody);
		var testResponse = restTemplate.getForObject(url, Customer.class);
		 assertTrue(Objects.nonNull(testResponse));
		 assertEquals(testResponse.getValidationStatus(), ValidationStatusEnum.In_Progress);
	}

}
