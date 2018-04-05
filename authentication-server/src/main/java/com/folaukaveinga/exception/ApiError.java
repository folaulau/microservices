package com.folaukaveinga.exception;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class ApiError {

	private HttpStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<ApiSubError> errors;
	private Throwable ex;
	
	public ApiError(HttpStatus status, String message) {
		this(status,message,null);
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this(status,message,null,ex);
	}
	
	public ApiError(HttpStatus status, String message, List<ApiSubError> subErrors, Throwable ex) {
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.debugMessage = (ex!=null) ? ex.getLocalizedMessage() : null;
		this.errors = subErrors;
		this.setEx(ex);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<ApiSubError> getErrors() {
		return errors;
	}

	public void setErrors(List<ApiSubError> subErrors) {
		this.errors = subErrors;
	}
	
	public void addSubError(ApiSubError subError) {
		if(this.errors==null){
			this.errors = new ArrayList<>();
		}
		this.errors.add(subError);
	}

	public Throwable getEx() {
		return ex;
	}

	public void setEx(Throwable ex) {
		this.ex = ex;
	}
	
	
}
