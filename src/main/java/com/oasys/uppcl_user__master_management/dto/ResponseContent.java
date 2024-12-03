package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseContent {
	private UUID id;
	private String username;
	private String agentId;
	private String userId;

}
