package com.folaukaveinga.exception;


public class ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;

	ApiSubError(String object, String message) {
		this(object, null, null, message);
	}

	public ApiSubError(String object, String field, Object rejectedValue, String message) {
		super();
		this.object = object;
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}



	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApiSubError [object=" + object + ", field=" + field + ", rejectedValue=" + rejectedValue + ", message="
				+ message + "]";
	}
}
