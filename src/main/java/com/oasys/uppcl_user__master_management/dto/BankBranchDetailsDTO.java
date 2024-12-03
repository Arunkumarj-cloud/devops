package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankBranchDetailsDTO {

    private UUID id;
	
	private String bankId;
	
	@NotBlank
	//@Pattern(regexp = "^[A-Z]{4}[0-9]{7}$", message = "Please Enter Valid Branch IFSC Code")
	private String ifscCode;
	
	private String stateCode;
	
	//@NotBlank
	private String districtName;
	
	//@NotBlank
	private String bankName;
	//@NotBlank
	private String stateName;
	
	@NotBlank
	private String branchName;
	
	//@NotNull
	private Boolean status;
	
	//@NotBlank
	private String stdCode;
	
	//@NotBlank
	private String landlineNumber;
	
	private String branchContactNumber;
	
	private String address;
	
	@NotNull
	private BankNameMasterEntity bankNameId;
	
	private UUID stateId;
	private UUID districtId;
	
	private String remarks;

}
