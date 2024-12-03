package com.oasys.uppcl_user__master_management.cache.dto;

import java.util.UUID;

import lombok.Data;


@Data
public class SubCategoryCacheDTO implements CacheDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4611775876108993755L;

	private UUID id;

	private String name;

	private Boolean status;

	private Double minAmount;

	private Double maxAmount;

	private String subscriberId;

	private String length;

	private String constantName;

	private String remarks;
}
