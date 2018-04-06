package com.folaukaveinga.utility;

public abstract class ApiResponse {
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	
	protected String message;
	
	public ApiResponse() {
		this(null);
	}
	
	public ApiResponse(String message) {
		super();
		this.message = message;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiResponse [ message=" + message + "]";
	}
	
}
