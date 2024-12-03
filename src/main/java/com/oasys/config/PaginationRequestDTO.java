package com.oasys.config;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PaginationRequestDTO {
	@NotNull
	Integer pageNo;

	@NotNull
	Integer paginationSize;
	@NotBlank
	String sortField;
	@NotBlank
	String sortOrder;

	Map<String, Object> filters;
	
	String search;

	String fromDate;
	String toDate;

}
