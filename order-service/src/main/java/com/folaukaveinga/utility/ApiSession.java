package com.folaukaveinga.utility;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ApiSession implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date created;
	private Date expiration;
	private String ip;
	private String device;
	
	public ApiSession() {
		this(null,null);
	}
	public ApiSession(String ip) {
		this(ip,null);
	}
	
	public ApiSession(String ip, String device) {
		this.ip = ip;
		this.device = device;
		this.created = new Date();
		this.expiration = new Date(new Date().getTime()+DateTimeUtil.getHoursInMilliseconds(4));
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	/**
	 * Extend by hours
	 * @param hour
	 */
	public void extendExpiration(long hour) {
		this.expiration = new Date(new Date().getTime()+DateTimeUtil.getHoursInMilliseconds(hour));
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String toJson() {
		try {
			return FormatterUtil.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException, msg: "+e.getLocalizedMessage());
			return "{}";
		}
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(expiration).toHashCode();

		// return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ApiSession other = (ApiSession) obj;
		return new EqualsBuilder().append(this.expiration, other.expiration).isEquals();
		
		// return EqualsBuilder.reflectionEquals(this, obj);
	}
}
