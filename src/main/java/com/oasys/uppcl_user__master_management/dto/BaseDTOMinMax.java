package com.oasys.uppcl_user__master_management.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class BaseDTOMinMax {
	private static final long serialVersionUID = 1L;

	Integer statusCode = 500;

	String message;

	Object responseContent;

	List<?> responseContents;
}
