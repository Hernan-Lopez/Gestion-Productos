package com.hernan.gestionproductos.execption;

import org.springframework.http.HttpStatus;
import com.hernan.gestionproductos.domain.ErrorResponse;

public class ProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus httpStatus;
	private final ErrorResponse errorResponse;

	public ProductException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.errorResponse = new ErrorResponse(httpStatus.value(), message, "Error", "Detalles no especificados.");
	}

	public ProductException(ErrorResponse errorResponse, HttpStatus httpStatus) {
		super(errorResponse.getMessage());
		this.httpStatus = httpStatus;
		this.errorResponse = errorResponse;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	
}
