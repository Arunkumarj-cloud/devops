package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankBranchDTO {
	
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please Enter AlphaNumeric only")
    @Size(min=3,message="Please Enter minimum 3 Characters")
	private String branchName;
	
	@NotBlank
	@Pattern(regexp = "^[A-Z]{4}[0-9]{7}$", message = "Please Enter Valid Branch IFSC Code")
	private String branchIfscCode;
	
	private Long branchContactNumber;
	@NotNull
	private Boolean status;
	@NotNull
	private DistrictMasterEntity districtId;
	@NotNull
	private BankNameMasterEntity bankNameId;
	
	@NotBlank
	private String address;

	@NotBlank
	@Pattern(regexp = "^[0-9]\\d{2,5}$", message = "Please enter valid numbers only")
	private String stdCode;
	
	@NotBlank
	@Pattern(regexp = "^[2-8]{1}\\d[0-9]\\d{2,8}$", message = "Please enter valid numbers only")
	private String landlineNumber;
	

}
