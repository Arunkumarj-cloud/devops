package com.oasys.feign.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowBaseDTO extends FeignAbstractDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer statusCode = 500;

	String message;

	ResponseContentDTO responseContent;

	Object notification;

	List<ResponseContentDTO> responseContents;

	Integer pageNo;

	Integer PageSize;

	Integer totalRecords;

	Integer totalPages;

	Integer numberOfElements;

	private String transactionId;

	List<Map<String, Map<UUID, String>>> res;
}