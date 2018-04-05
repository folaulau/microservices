package com.folaukaveinga.exception;

/**
 * GetException is an exception handler for getting resources such as GET
 * @author fkaveinga
 *
 */
public class GetException extends RuntimeException {
	
	private ApiError error;

	public GetException() {
		this(null);
	}

	public GetException(ApiError error) {
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
