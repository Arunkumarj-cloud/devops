package com.oasys.uppcl_user__master_management.dto;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class PackageResponseDTO {
private UUID id;
	private String name;
	private Double amount;
	private List<ServiceCategoryResponseDTO> serviceCategoryList;
	private Boolean status;
}


