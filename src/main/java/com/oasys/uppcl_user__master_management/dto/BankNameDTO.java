package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BankNameDTO {
private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9 &.\\-_]*$", message = "Please enter Characters only")
	@Size(min=3,message="Please Enter Minimum 3 Characters ")
	private String bankName;
	
	//private BankTypeMasterEntity bankTypeId;
	@NotNull
	private Boolean status;
	
	private String bankId;

    private String remarks;

}
