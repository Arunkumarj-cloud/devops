package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SubscriptionMasterDTO {
	
	private UUID id;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9&.\\-_ ]*$", message = "Please  Enter AlphaNumeric Only")
	@Size(min = 3, message = "Please Enter Minimum 3 characters")
	private String subscriptionName;
	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Integer numOfDays;
	
	private Boolean isDefault;
	
	private Boolean isCustom;

	@NotNull
	private Boolean isActive;
	@NotBlank
	@Size(min = 3, message = "Please Enter Minimum 3 Characters")
	private String description;
	
	private Date createdDate;

}
