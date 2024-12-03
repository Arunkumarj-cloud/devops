package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class ProjectTypeRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private Boolean status;
	private String remarks;
}
