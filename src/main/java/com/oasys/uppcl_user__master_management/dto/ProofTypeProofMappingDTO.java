package com.oasys.uppcl_user__master_management.dto;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;

import lombok.Data;

@Data
public class ProofTypeProofMappingDTO {
	
private UUID id;
	
	@NotNull
	private ProofTypeMasterEntity proofTypeId;
	
	@NotNull
	private ProofMasterEntity proofId;
	
	@NotNull
	private Boolean status;
	
	@NotNull
	private String description;
	
	
	private String remarks;
	
	private Date createdDate;
	
	private Date modifiedDate;

}
