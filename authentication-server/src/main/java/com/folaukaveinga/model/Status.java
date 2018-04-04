package com.folaukaveinga.model;

import java.io.Serializable;

public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String message;
	private String token;
	
	public Status() {
		super();
		// TODO Auto-generated constructor stub
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