package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;

public interface BankNameRepository extends JpaRepository<BankNameMasterEntity, UUID> {

	@Query("select a from BankNameMasterEntity a where a.status = true ORDER by a.bankName ASC")
	 List <BankNameMasterEntity> getAllActive();
	/*	
	@Query("select a from BankNameMasterEntity a where a.bankName = ;bankName")
	 List <BankNameMasterEntity> findByBankName(@Param("bankName") String bankName);
	*/
	@Query("select a from BankNameMasterEntity a where a.bankName = :bankName")
	 BankNameMasterEntity getByBankName(@Param("bankName") String bankName);
	
	@Query("SELECT a FROM BankNameMasterEntity a WHERE NOT a.id =:id")
	List<BankNameMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("select a from BankNameMasterEntity a where a.bankName = :bankName")
	 List<BankNameMasterEntity> check(@Param("bankName") String bankName);
	
	@Query("select a from BankNameMasterEntity a where a.bankId = :bankId")
	BankNameMasterEntity getByBankId(@Param("bankId") String bankId);
	
	BankNameMasterEntity findByBankNameIgnoreCase(String bankName);
	
	
}
