package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;

public interface BankBranchDetailsRepository extends JpaRepository<BankBranchDetailsEntity, UUID> {

	@Query("select a from BankBranchDetailsEntity a where a.ifscCode= :ifscCode")
	List<BankBranchDetailsEntity> findByIfscCode( @Param ("ifscCode")String ifscCode);

	@Query("select a from BankBranchDetailsEntity a where a.ifscCode =:ifscCode")
	Optional<BankBranchDetailsEntity> findByIfsc(@Param("ifscCode") String ifscCode);

	@Query("select a from BankBranchDetailsEntity a where a.status = true")
	 List <BankBranchDetailsEntity> getAllActive();

/*
	@Query("select a from BankBranchDetailsEntity a where a.status = true and a.bankNameId =:bankNameId ")
	 List <BankBranchDetailsEntity> getByBankNameId(@Param("bankNameId") BankNameMasterEntity bankNameId);
	*/

	@Query("SELECT a FROM BankBranchDetailsEntity a WHERE UPPER(a.ifscCode) LIKE %:value%")
	Page<BankBranchDetailsEntity> search(Pageable pageable,@Param("value") String value);

	@Query("SELECT a FROM BankBranchDetailsEntity a WHERE a.ifscCode=:ifscCode AND a.status=true order by createdDate desc")
	List<BankBranchDetailsEntity> getBankBranchDetails(@Param("ifscCode") String ifscCode);
	
	@Query("SELECT a FROM BankBranchDetailsEntity a WHERE UPPER(a.branchName) LIKE :value%")
	Page<BankBranchDetailsEntity> search1(Pageable pageable,@Param("value") String value);
	
	
	@Query("SELECT a FROM BankBranchDetailsEntity a WHERE UPPER(a.ifscCode) LIKE %:value% or UPPER(a.branchName) LIKE %:value% or UPPER(a.districtName) LIKE %:value% or"
			+ " UPPER(a.bankName) LIKE %:value%  or UPPER(a.stateName) LIKE %:value%  or UPPER(a.branchContactNumber) LIKE %:value% or UPPER(a.address) LIKE %:value% or UPPER(a.stateCode) LIKE %:value%")
	Page<BankBranchDetailsEntity> search2(Pageable pageable,@Param("value") String value);


}
