package com.oasys.uppcl_user__master_management.cache.dto;

import java.util.UUID;

import lombok.Data;
@Data
public class ServiceProviderCacheDTO implements CacheDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6101731968110636355L;

	private UUID id;

	private String serviceProviderName;

	private Boolean status;

	private String remarks;

}
