package com.oasys.uppcl_user__master_management.dto;

	import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;

import lombok.Data;

		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public class ServiceSlotDTO {
			private UUID id;
			
			@NotBlank	
			private String slot;
			
			@NotNull
			private ServiceCategoryEntity serviceCategoryId;
			
			@NotNull
			private Boolean status;
			
			private Long position;
			private String remarks;
			
			private SubCategoryEntity subCategoryId;

	}





