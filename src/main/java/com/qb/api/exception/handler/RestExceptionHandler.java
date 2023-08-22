package com.qb.api.exception.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qb.api.exception.ApplicationException;
import com.qb.api.model.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ErrorResponse noSuchElementException(NoSuchElementException exception, HttpServletRequest httpRequest) {
		log.error("failed Api url: {}, errorDetails: {}", httpRequest.getRequestURI(), exception.getMessage());
		return ErrorResponse.builder().errorMessage("No Record Available").build();
	}

	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse applicationException(ApplicationException exception, HttpServletRequest httpRequest) {
		log.error("failed Api url: {}, errorDetails: {}", httpRequest.getRequestURI(), exception.getMessage());
		return ErrorResponse.builder().errorMessage(exception.getMessage()).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest httpRequest) {
		log.error("failed Api url: {}, errorDetails: {}", httpRequest.getRequestURI(), ex.getMessage());
		return ErrorResponse.builder().errorMessage(ex.getBindingResult().getFieldError().getDefaultMessage()).build();

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse globalException(Exception ex, HttpServletRequest httpRequest) {
		log.error("failed Api url: {}, errorDetails: {}", httpRequest.getRequestURI(), ex.getMessage());
		return ErrorResponse.builder().errorMessage("Failed to proccess your request").casue(ex.getLocalizedMessage()).build();

	}

}
