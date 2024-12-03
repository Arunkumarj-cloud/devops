package com.oasys.feign.dto;

import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseContentDTO {

	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	private String stateName;
	
	private String roleName;
	
	private String branchName;
	
	private String bankName;
	
	private String type;
	
	private Boolean status;
		
	private String districtName;
	
	private String userRoleName;
	
	private String name;
	
	private String agentType;
	
	private String accountType;
	
	private String model;
	
	//private ArrayList<UUID> userType;
	private String pinCode;
	private String divisionName;
	
	private String applicationName;
	
	
	private String username;
	
	private String agentTypeId;

}
