package com.oasys.uppcl_user__master_management.repository;



import java.util.List;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeProofMappingEntity;


public interface ProofTypeProofMappingRepository extends JpaRepository<ProofTypeProofMappingEntity, UUID> {
	
	@Query("select a from ProofTypeProofMappingEntity a where a.status = true and a.proofTypeId.status=true and a.proofId.status=true and a.proofTypeId =:id ORDER BY a.proofId.createdDate,a.proofTypeId.createdDate ASC")
	 List<ProofTypeProofMappingEntity> findByProofTypeId(@Param("id") ProofTypeMasterEntity id);

	@Query("select a from ProofTypeProofMappingEntity a where a.proofTypeId =:id")
	List<ProofTypeProofMappingEntity> findProofTypeId(@Param("id") ProofTypeMasterEntity id);
	
	@Query("select a from ProofTypeProofMappingEntity a where a.status = true")
	 List <ProofTypeProofMappingEntity> getAllActive();
	
	@Query("select a from ProofTypeProofMappingEntity a where a.proofId =:id")
	List<ProofTypeProofMappingEntity> findProofId(@Param("id") ProofMasterEntity id);

}
