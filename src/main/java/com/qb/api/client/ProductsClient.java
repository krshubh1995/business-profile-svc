package com.qb.api.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.Products;
import com.qb.api.dao.repository.ProductRepository;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductsClient {

	@Value("${product.config.retryProductValidationCount}")
	private int retryProductValidationCount;
	private final ProductRepository productsRepository;
	private final RestTemplate template;

	public Map<String, ValidatedResponse> validateCustomer(CustomerValidate payload) {
		List<Products> products = null;
		if (!CollectionUtils.isEmpty(payload.getValidatedProductsError())) {
			List<String> productName = payload.getValidatedProductsError().stream().filter(
					(val) -> val.isRetryValidation() && val.getRetryValidationCount() < retryProductValidationCount)
					.map(val -> val.getProductName()).collect(Collectors.toList());
			products = productsRepository.findAllByNameIn(productName);
		} else {
			products = productsRepository.findAll();
		}
		Map<String, CompletableFuture<ValidatedResponse>> validatedAsyncResponse = products.stream().collect(Collectors
				.toMap(product -> product.getName(), product -> validate(product.getValidationUrl(), payload)));
		CompletableFuture.allOf(validatedAsyncResponse.values().toArray(CompletableFuture[]::new)).join();

		Map<String, ValidatedResponse> response = validatedAsyncResponse.entrySet().stream()
				.collect(Collectors.toMap(validatedResponse -> validatedResponse.getKey(), validatedResponse -> {
					try {
						return validatedResponse.getValue().get();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						return null;
					}
				}));

		return response;
	}

//	@CircuitBreaker(fallbackMethod = "fallbackValidate", name = "productValidate")
	@Retry(name = "qbAccountsClientRetry", fallbackMethod = "fallbackValidate")
	@Async
	public CompletableFuture<ValidatedResponse> validate(String url, CustomerValidate payload) {
		log.info("validation trigereed with url {} for customer:{}", url, payload.getCompnayName());
		try {
			var valid = new ValidatedResponse();
			valid.setCustomerId(payload.getId());
			valid.setValid(true);
			ResponseEntity<ValidatedResponse> responseMocked = new ResponseEntity<ValidatedResponse>(valid,
					HttpStatus.OK);

			when(template.postForEntity(anyString(), any(), eq(ValidatedResponse.class))).thenReturn(responseMocked);

			var response = template.postForEntity(url, payload, ValidatedResponse.class);

			return CompletableFuture.completedFuture(response.getBody());
		} catch (HttpClientErrorException ex) {
			var valid = new ValidatedResponse();
			valid.setCustomerId(payload.getId());
			valid.setValid(false);
			//return CompletableFuture.completedFuture(ex.getResponseBodyAs(ValidatedResponse.class));
			return CompletableFuture.completedFuture(valid);

		}

	}

	public CompletableFuture<ValidatedResponse> fallbackValidate(String url, CustomerValidate payload, Exception ex) {
		log.error("fallback trigerred, failed to process: {}", ex.getMessage());
		var valid = new ValidatedResponse();
		valid.setCustomerId(payload.getId());
		valid.setError(true);
		return CompletableFuture.completedFuture(valid);

	}
}
