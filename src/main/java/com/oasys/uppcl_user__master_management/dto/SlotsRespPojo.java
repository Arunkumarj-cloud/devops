package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SlotsRespPojo {

	private List<UUID> slotsIds;

	public SlotsRespPojo(List<UUID> slotsIds) {
		super();
		this.slotsIds = slotsIds;
	}
	
	
}
