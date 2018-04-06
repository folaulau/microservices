package com.folaukaveinga.exception;

/**
 * ProcessException is an exception handler for processing resources such as POST, PUT, PATCH, DELETE
 * @author fkaveinga
 *
 */
public class ProcessException extends RuntimeException {
	
	private ApiError error;

	public ProcessException() {
		this(null);
	}

	public ProcessException(ApiError error) {
		super(error.getMessage());
		this.error = error;
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}
}
