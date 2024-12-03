package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProjectTypeResponseDTO {

	private UUID id;
	private String name;
	private Boolean status;
	private String createdDate;
	private String modifiedDate;
	private String remarks;
}
