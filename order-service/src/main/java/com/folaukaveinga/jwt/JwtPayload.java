package com.folaukaveinga.jwt;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.folaukaveinga.utility.FormatterUtil;


@JsonInclude(value=Include.NON_NULL)
public class JwtPayload {
	private String iss;
	private long sub;
	private String aud;
	private String email;
	private String firstName;
	private String lastName;
	private List<String> roles;
	private Date iat;
	private Date exp;
	private Date nbf;

	public JwtPayload() {
		this(null,0,null);
	}
	
	public JwtPayload(String iss, long sub,Date exp) {
		this(iss,sub,null,null,null,null,null,exp,null);		
	}
	
	public JwtPayload(String iss, long sub, String aud, String email, String firstName, String lastName,
			List<String> roles, Date exp, Date nbf) {
		super();
		this.iss = iss;
		this.sub = sub;
		this.aud = aud;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.exp = exp;
		this.nbf = nbf;
		this.iat = new Date();
	}
	
	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public long getSub() {
		return sub;
	}

	public void setSub(long sub) {
		this.sub = sub;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	public Date getNbf() {
		return nbf;
	}

	public void setNbf(Date nbf) {
		this.nbf = nbf;
	}
	
	public Date getIat() {
		return iat;
	}

	public void setIat(Date iat) {
		this.iat = iat;
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
		return new HashCodeBuilder(17, 37).append(email).toHashCode();

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
		JwtPayload other = (JwtPayload) obj;
		return new EqualsBuilder().append(this.email, other.email).isEquals();
		
		// return EqualsBuilder.reflectionEquals(this, obj);
	}
}
