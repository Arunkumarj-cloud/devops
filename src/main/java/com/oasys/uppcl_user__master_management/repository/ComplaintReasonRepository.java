package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ComplaintReasonEntity;

public interface ComplaintReasonRepository extends JpaRepository<ComplaintReasonEntity, UUID> {

	List<ComplaintReasonEntity> findByStatusTrue();

	@Query("select a from ComplaintReasonEntity a where a.reasonCode =:code")
	 List <ComplaintReasonEntity> checkCode(@Param("code") String code);
	
	@Query("SELECT mm FROM ComplaintReasonEntity mm WHERE UPPER(mm.reasonName) LIKE %:value% or UPPER(mm.reasonTypeId.reasonType) LIKE %:value% or (mm.reasonCode) LIKE %:value% or (mm.status) LIKE %:value%")
	Page<ComplaintReasonEntity> search(Pageable pageRequest,@Param("value")  String value);

	@Query("SELECT a FROM ComplaintReasonEntity a WHERE NOT a.id =:id")
	List<ComplaintReasonEntity> getByExceptId(@Param("id") UUID id);
}
