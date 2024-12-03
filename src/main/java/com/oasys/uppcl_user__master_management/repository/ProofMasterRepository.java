package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;

public interface ProofMasterRepository extends JpaRepository<ProofMasterEntity, UUID> {

	@Query("SELECT a FROM ProofMasterEntity a WHERE a.status =1")
	List<ProofMasterEntity> getAllActive();

	@Query("SELECT a FROM ProofMasterEntity a WHERE UPPER(a.proofName) LIKE %:value% or UPPER(a.description) LIKE %:value% ")
	Page<ProofMasterEntity> search(Pageable pageable, @Param("value") String value);

	ProofMasterEntity findByProofNameIgnoreCase(String name);

	@Query("SELECT a FROM ProofMasterEntity a WHERE a.id != :id")
	List<ProofMasterEntity> findByIdNotIn(UUID id);

	@Query("SELECT a.proofName FROM ProofMasterEntity a WHERE a.id =:id ORDER BY a.proofName ASC")
	String getProofNameById(@Param("id") UUID id);

}
