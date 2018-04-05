package com.folaukaveinga.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.folaukaveinga.utility.FormatterUtil;



@JsonInclude(value=Include.NON_NULL)
@Entity(name="restaurant_order")
@Table
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="item")
	private String item;
	
	
	@Column(name="user_id")
	private long userId;


	public Order() {
		this(0);
	}
	
	public Order(long id) {
		this(id,null,0);
	}
	public Order(long id, String item, long userId) {
		super();
		this.id = id;
		this.item = item;
		this.userId = userId;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
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
		return new HashCodeBuilder(17, 37).append(id).toHashCode();

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
		Order other = (Order) obj;
		return new EqualsBuilder().append(this.id, other.id).isEquals();
		
		// return EqualsBuilder.reflectionEquals(this, obj);
	}
}
