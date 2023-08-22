package com.qb.api.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3380752442074012101L;
	private String message;

	ApplicationException(String message){
		super(message);
		this.message = message;
	}
	

}
