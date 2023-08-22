package com.qb.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

	private String errorMessage;
	private String casue;
}
