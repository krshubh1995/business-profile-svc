package com.qb.api.dao.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsValidationAudit {
	
	private String productName;
	private ValidationStatusEnum error;
	private String cause;
	private boolean retryValidation;
	private Integer retryValidationCount;

}
