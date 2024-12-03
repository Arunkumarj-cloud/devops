package com.oasys.uppcl_user__master_management.dto;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProofMasterDTO {

	private UUID id;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z ]*$", message = "Please enter characters only")
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String proofName;
	@NotBlank
	@Size(min = 3, message = "Please enter minimum 3 characters")
	private String description;
	@NotNull
	private Boolean status;

	private ProofTypeMasterEntity proofTypeId;
	@NotNull
	private List<ProofTypeMasterEntity> proofType;
	
	private String remarks;

}
