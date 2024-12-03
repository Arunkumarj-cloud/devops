package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ReasonTypeEntity;

public interface ReasonTypeRepository extends  JpaRepository<ReasonTypeEntity, UUID>{
	
	@Query("SELECT s FROM ReasonTypeEntity s  WHERE UPPER(s.reasonType) LIKE %:value% OR UPPER(s.description) LIKE %:value%")
	Page<ReasonTypeEntity> search(Pageable pageable, @Param("value") String value);
	
	List<ReasonTypeEntity> findByStatusTrue(Sort sort);

	@Query("SELECT s FROM ReasonTypeEntity s  where s.id != :id")
	List<ReasonTypeEntity> findByIdNotIn(UUID id);
			
	ReasonTypeEntity findByReasonTypeIgnoreCase(String reasonTye);
		
	@Query("SELECT a FROM ReasonTypeEntity a WHERE NOT a.id =:id")
	List<ReasonTypeEntity> getByExceptId(@Param("id") UUID id);
		
		
}
