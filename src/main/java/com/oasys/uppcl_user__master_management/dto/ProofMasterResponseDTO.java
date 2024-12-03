package com.oasys.uppcl_user__master_management.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class ProofMasterResponseDTO {
	
	private UUID id;	
	private String proofName;
	private String description;
	private Boolean status;
	private Date createdDate;
	private List<Map<String, Object>> proofTypeName;
	private String remarks;
	private Date modifiedDate;

}
