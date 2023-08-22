package com.qb.api.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatedResponse {

	private String customerId;
	private boolean valid;
	//IF fails due to network issue/ timeout etc
	private boolean error;
	private String errorMessage;
}