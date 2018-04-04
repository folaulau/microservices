package com.folaukaveinga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.folaukaveinga.utility.FormatterUtil;

@Entity(name="user")
@Table
public class User implements Serializable {
	
	public static final String ADMIN = "admin";

	public static final String USER = "user";
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="username", unique=true)
	private String username;
	
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	
	@Fetch(FetchMode.SELECT)
	@ElementCollection(fetch=FetchType.EAGER)
	@Column(name="user_role")
	@CollectionTable(name="role")
	private List<String> roles;
	
	
	public User() {
		this(0);
	}
	
	public User(long id) {
		this(id,null,null,null);
	}
	
	public User(long id, String username, String password, List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	public void addRole(String role) {
		if(this.roles == null){
			this.roles = new ArrayList<>();
		}
		
		this.roles.add(role);
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
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
		return new HashCodeBuilder(17, 37).append(id).append(this.username).toHashCode();

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
		User other = (User) obj;
		return new EqualsBuilder().append(this.id, other.id).append(this.username, other.username).isEquals();
		
		// return EqualsBuilder.reflectionEquals(this, obj);
	}

}
