package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeProofMappingEntity;

public interface ProofMasterDao {

	ProofMasterEntity save(ProofMasterEntity proofEntity);

	ProofMasterEntity getById(UUID id);

	List<ProofMasterEntity> getAll();

	List<ProofMasterEntity> getAllActive();

	List<Map<String, Object>> getLazyList(PaginationRequestDTO requestData);

	ProofMasterEntity delete(UUID id);

	ProofMasterEntity validateProofName(String name);

	List<ProofMasterEntity> exceptId(UUID id);
	
	List<ProofTypeProofMappingEntity> getByProofType(UUID id);

	ProofTypeProofMappingEntity saveProofTypeProofMapping(ProofTypeProofMappingEntity entity);

	List<ProofTypeProofMappingEntity> getByProofId(ProofMasterEntity proofEntity);

	ProofTypeProofMappingEntity deleteProofMapping(ProofTypeProofMappingEntity deleteProofMapping);

	Boolean validate(ProofTypeProofMappingDTO mappingDTO);

}
