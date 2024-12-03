package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BankPassbookMasterEntity;

public interface BankPassbookMasterRepository extends JpaRepository<BankPassbookMasterEntity, UUID> {

	@Query("SELECT a FROM BankPassbookMasterEntity a WHERE a.status =1")
	List<BankPassbookMasterEntity> getAllactive();
	
	@Query("SELECT a FROM BankPassbookMasterEntity a WHERE UPPER(a.description) LIKE %:value% or UPPER(a.name) LIKE %:value%")
	Page<BankPassbookMasterEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("SELECT a FROM BankPassbookMasterEntity a WHERE NOT a.id =:id")
	List<BankPassbookMasterEntity> getByExceptId(@Param("id") UUID id);
	
	
	@Query("select a from BankPassbookMasterEntity a where a.name =:name")
	Optional<BankPassbookMasterEntity> findByPassBookName(@Param("name") String name);
	
	@Query("select a from BankPassbookMasterEntity a where a.name =:name")
	List<BankPassbookMasterEntity> findByName(@Param("name") String name);
}
