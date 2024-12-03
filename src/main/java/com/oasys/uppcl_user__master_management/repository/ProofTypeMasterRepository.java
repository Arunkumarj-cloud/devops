package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;

public interface ProofTypeMasterRepository extends JpaRepository<ProofTypeMasterEntity, UUID> {

	@Query("SELECT a FROM ProofTypeMasterEntity a WHERE a.status =1")
	List<ProofTypeMasterEntity> getAllactive(Sort sort);

	@Query("SELECT a FROM ProofTypeMasterEntity a WHERE UPPER(a.proofTypeName) LIKE %:value% or UPPER(a.description) LIKE %:value% ")
	Page<ProofTypeMasterEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("SELECT a FROM ProofTypeMasterEntity a WHERE a.id != :id")
	List<ProofTypeMasterEntity> findByIdNotIn(UUID id);
	
	ProofTypeMasterEntity findByProofTypeNameIgnoreCase(String proofType);
	
	
	
    @Query("select a from ProofTypeMasterEntity a where a.proofTypeName =:ProofTypeMasterEntity")
    List<ProofTypeMasterEntity> findByName(@Param("ProofTypeMasterEntity") String ProofTypeMasterEntity);
    

    @Query("select a from ProofTypeMasterEntity a where a.proofTypeName in :proofTypeNames and a.status =1")
    List<ProofTypeMasterEntity> findByProofTypeNames(@Param("proofTypeNames") List<String> proofTypeNames);
}
