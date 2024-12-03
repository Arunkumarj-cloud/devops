package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BankTypeMasterEntity;

public interface BankTypeMasterRepository extends JpaRepository<BankTypeMasterEntity, UUID> {

	@Query("SELECT a FROM BankTypeMasterEntity a WHERE a.status =1")
	List<BankTypeMasterEntity> getAllactive(Sort sort);
	
	@Query("SELECT t FROM BankTypeMasterEntity t where UPPER(t.type) LIKE %:value%")
	Page<BankTypeMasterEntity> search(Pageable pageable,@Param("value") String value);

	@Query("SELECT a FROM BankTypeMasterEntity a WHERE a.type= :name")
	Optional<BankTypeMasterEntity> findByBankType(@Param("name") String name);
	
	@Query("SELECT a FROM BankTypeMasterEntity a WHERE NOT a.id =:id")
	List<BankTypeMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("SELECT a FROM BankTypeMasterEntity a WHERE a.type= :name")
	List<BankTypeMasterEntity> check(@Param("name") String name);

	BankTypeMasterEntity findByTypeIgnoreCase(String bankType);
	
}
