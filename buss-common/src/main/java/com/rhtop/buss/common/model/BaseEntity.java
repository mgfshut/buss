package com.rhtop.buss.common.model;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	final static private ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		try {
			return OBJECT_MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}
		return super.toString();
	}
}
