package com.qb.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.api.dao.model.Products;
import com.qb.api.service.ProductsService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class ProductServiceTest {

	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	private ProductsService productsService;
	ObjectMapper mapper = new ObjectMapper();
	private final String PRODUCT_URL = "/v1/products";

	@Test
	public void addProducts() throws JsonMappingException, JsonProcessingException {
		String requestJson = "[\r\n" + "  {\r\n" + "   \r\n" + "    \"name\": \"string\",\r\n"
				+ "    \"validationUrl\": \"string\"\r\n" + "  }\r\n" + "]";
		var productRequest = mapper.readValue(requestJson, new TypeReference<List<Products>>() {
		});
		when(productsService.addProducts(any())).thenReturn(productRequest);
		var reponse = restTemplate.postForEntity(PRODUCT_URL, productRequest, List.class);
		assertTrue(!CollectionUtils.isEmpty(reponse.getBody()));

	}

	public void deleteProducts() throws JsonMappingException, JsonProcessingException {
		String requestJson = "[\r\n" + "  \"string\"\r\n" + "]";
		var productRequest = mapper.readValue(requestJson, new TypeReference<List<String>>() {
		});
		doNothing().when(productsService).deleteProducts(any());
		var reponse = restTemplate.postForEntity(PRODUCT_URL, productRequest, List.class);
		assertEquals(reponse.getStatusCode(), HttpStatus.OK);
	}
}
