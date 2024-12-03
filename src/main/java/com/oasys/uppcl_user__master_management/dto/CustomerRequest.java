package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequest {

	private UUID id;						
	
 	@NotBlank
	@Pattern(regexp = "^[A-Z]{4}[0-9]{7}$", message = "Please Enter Valid Branch IFSC Code")
	private String ifscCode;
	
	private String bankName;
			
	private String branchName;
	
	private Long phoneNumber;
	
	private String rationCardNumber;
	
	private String smartCardNumber;
	
	private String customerName;
	
	private String stateName;
	
	private String districtName;
	
	private String accountNumber;
	
}
