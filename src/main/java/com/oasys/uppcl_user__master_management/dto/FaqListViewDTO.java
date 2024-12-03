package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class FaqListViewDTO {
	
    private UUID id;
	
	private UUID categoryId;
	@NotBlank
	private  String categoryName;
	@NotNull
	private Boolean status;


}
