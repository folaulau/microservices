package com.folaukaveinga.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	private String status;
	private String message;
	private String token;
	
	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Status(String status) {
		this(status,null,null);
	}
	public Status(String status, String message, String token) {
		super();
		this.status = status;
		this.message = message;
		this.token = token;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Status [status=" + status + ", message=" + message + ", token=" + token + "]";
	}
}