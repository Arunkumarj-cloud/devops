package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.config.Trackable;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantTypeMasterDTO  extends Trackable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	@NotBlank
	@Pattern(regexp = "^[a-z A-Z]*$", message = "Please enter Characters only")
	@Size(min = 3, message = "Please enter minimum 3 Characters")
	private String agentType;
	
	//@NotBlank
    //@Size(min= 3, message =  "Please enter minimum 3 Characters ")
	private String description;
	
	@NotNull
	private Boolean status;
	
	private String remarks;

}
