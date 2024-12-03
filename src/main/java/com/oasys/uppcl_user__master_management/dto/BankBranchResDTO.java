package com.oasys.uppcl_user__master_management.dto;


import lombok.Data;
@Data
public class BankBranchResDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	
	private String status;
	private String message;
	
	private BankResponseDTO data;

}
