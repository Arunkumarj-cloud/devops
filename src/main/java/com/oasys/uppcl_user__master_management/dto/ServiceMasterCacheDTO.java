package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import com.oasys.uppcl_user__master_management.cache.dto.CacheDTO;

import lombok.Data;
@Data
public class ServiceMasterCacheDTO implements CacheDTO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6393204427771204907L;

	private UUID id;

	private String name;

	private String chargeType;

	private Boolean status;

}
