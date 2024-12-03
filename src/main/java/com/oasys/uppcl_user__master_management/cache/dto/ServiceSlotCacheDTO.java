package com.oasys.uppcl_user__master_management.cache.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ServiceSlotCacheDTO implements CacheDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7224395993047333563L;

	private UUID id;

	private long startRange;

	private long endRange;

	private String slot;

	private Boolean status;

	private Long position;

	private String remarks;

}
