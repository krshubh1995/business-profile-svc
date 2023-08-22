package com.qb.api.scheduler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.api.client.ProductsClient;
import com.qb.api.client.ValidatedResponse;
import com.qb.api.dao.model.Customer;
import com.qb.api.dao.model.CustomerValidate;
import com.qb.api.dao.model.ProductsValidationAudit;
import com.qb.api.dao.model.SqlOperations;
import com.qb.api.dao.model.ValidationStatusEnum;
import com.qb.api.dao.repository.CustomerRepository;
import com.qb.api.dao.repository.CustomersValidateRepository;
import com.qb.api.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductValidationScheduler {

	private final ProductsClient productsClient;
	private final CustomerRepository customerRepository;
	private final CustomersValidateRepository customersValidateRepository;
	private final CustomerService customerService;
	private final ObjectMapper mapper;

	@Scheduled(fixedRate = 30000, initialDelay = 3000)
	public void execute() {
		var customers = customersValidateRepository
				.findAllByValidationStatusIn(List.of(ValidationStatusEnum.In_Progress, ValidationStatusEnum.Error));
		log.info("validating for {} customers", customers.size());
		runValidation(customers);
	}

	@Async
	private void runValidation(List<CustomerValidate> customers) {

		// customers TO be approve
		customers.stream().forEach(customer -> {
			Map<String, ValidatedResponse> productValidationMap = productsClient.validateCustomer(customer);
			// Products call to get approval
			List<Boolean> validatedResponseList = productValidationMap.entrySet().stream().map(product -> {
				// validate by calling product APIs
				ValidatedResponse validatedResponse = product.getValue();
				// if not valid store in products Error collection
				if (!validatedResponse.isValid()) {
					ProductsValidationAudit audit = new ProductsValidationAudit();
					audit.setProductName(product.getKey());
					audit.setCause(validatedResponse.getErrorMessage());
					audit.setError(ValidationStatusEnum.Rejected);
					customer.getValidatedProductsError().add(audit);
				} else if (validatedResponse.isError()) {
					customer.getValidatedProductsError().stream()
							.filter(t -> t.getProductName().equals(product.getKey())).findFirst().ifPresentOrElse(
									productAudit -> productAudit
											.setRetryValidationCount(productAudit.getRetryValidationCount() + 1),
									() -> {
										ProductsValidationAudit audit = new ProductsValidationAudit();
										audit.setProductName(product.getKey());
										audit.setCause(validatedResponse.getErrorMessage());
										audit.setError(ValidationStatusEnum.Error);
										audit.setRetryValidationCount(0);
										audit.setRetryValidation(true);
										customer.getValidatedProductsError().add(audit);
									});

				} else {
					// else if validated and is in product error audit remove from error collection
					customer.getValidatedProductsError().removeIf(t -> t.getProductName().equals(product.getKey()));
				}
				return validatedResponse.isValid();

			}).collect(Collectors.toList());

			if (validatedResponseList.contains(false)
					|| !CollectionUtils.isEmpty(customer.getValidatedProductsError())) {
				
				customer.setValidationStatus(ValidationStatusEnum.Rejected);
				customersValidateRepository.save(customer);
			} else {// if listaudit is empty
				customer.setValidationStatus(ValidationStatusEnum.Accepted);
				var customerMain = mapper.convertValue(customer, Customer.class);
				if (customer.getSqlOperation().equals(SqlOperations.SAVE)) {
					customerRepository.save(customerMain);
				} else if (customer.getSqlOperation().equals(SqlOperations.UPDATE)) {
					customerMain.setId(customer.getCustomerId());
					customerService.update(customerMain);

				}

				customersValidateRepository.deleteById(customer.getId());
			}

		});

	}

}
