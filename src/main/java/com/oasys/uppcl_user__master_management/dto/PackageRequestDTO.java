package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import lombok.Data;






@Data
public class PackageRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private UUID id;
	
	private Double amount;
	
	private Set<UUID> serviceCategoryIds;
	private Boolean isDefaultPackage;
	private Boolean status;
	private String remarks;
}

