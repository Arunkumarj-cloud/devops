package com.oasys.feign.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;


@Getter
public abstract class FeignAbstractDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer statusCode;

	String message;

	Object responseContent;
	
	
}