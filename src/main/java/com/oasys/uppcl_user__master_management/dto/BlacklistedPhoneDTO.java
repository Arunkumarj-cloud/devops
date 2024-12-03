package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class BlacklistedPhoneDTO {
	private UUID id;


	private String phoneNumber;
   
	
	public Date createdDate;

	public UUID createdBy;
	
	
	public Date modifiedDate;

	public UUID modifiedBy;

}
