package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.TermsAndConditionsEntity;



public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditionsEntity, UUID> {
	@Query("SELECT a FROM TermsAndConditionsEntity a WHERE a.status = true")
	List<TermsAndConditionsEntity> getAllActive();
	
	@Query("select a from TermsAndConditionsEntity a where a.description = :description")
	TermsAndConditionsEntity getByDescription(@Param("description") String description);
	
	@Query("SELECT mm FROM TermsAndConditionsEntity mm where UPPER(mm.description) like %:value% " + "or UPPER(mm.createdDate) like %:value%")
	Page<TermsAndConditionsEntity> search(Pageable pageable, @Param("value") String value);
	
	@Query("SELECT a FROM TermsAndConditionsEntity a WHERE NOT a.id =:id")
	List<TermsAndConditionsEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("Select a from TermsAndConditionsEntity a where a.id=:id")
	TermsAndConditionsEntity findOne(@Param("id") UUID id);
	@Query(nativeQuery = true, value="select * from terms_and_conditions  ORDER by created_date DESC")
	 List <TermsAndConditionsEntity> getAll();
	
	@Query("Select a from TermsAndConditionsEntity a where a.status = true")
	TermsAndConditionsEntity getOne();

}
