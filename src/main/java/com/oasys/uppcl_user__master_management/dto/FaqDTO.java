package com.oasys.uppcl_user__master_management.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.FaqCategoryEntity;

import lombok.Data;
@Data
public class FaqDTO extends PaginationRequestDTO {
	
	private UUID id;
	
//	@NotBlank
//	@Size (min = 3, message = "Please enter minimum 3 Characters")
    private String question;
	
//	@NotBlank
//	@Size (min = 3, message = "Please enter minimum 3 Characters")
    private String answer;
	private String remarks;
	private Integer category_Id;
	@NotNull 
	private FaqCategoryEntity categoryId;
	
	@NotNull
	private Boolean status;
	
	@NotNull
	private String language;
	@NotNull
	private String htmlcontent;
	
	private String categoryName;
	private String image;

}

