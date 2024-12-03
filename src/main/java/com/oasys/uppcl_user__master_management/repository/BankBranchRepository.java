package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.BankBranchMasterEntity;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;


public interface BankBranchRepository extends JpaRepository<BankBranchMasterEntity, UUID> {
	
	@Query("select a from BankBranchMasterEntity a where a.status = true ORDER by a.branchName")
	 List <BankBranchMasterEntity> getAllActive();
	
	@Query("select a from BankBranchMasterEntity a where a.branchContactNumber = :phone")
	  BankBranchMasterEntity getByPhoneNo( @Param("phone") Long phone );
	
	@Query("select a from BankBranchMasterEntity a where a.branchName = :branchName")
	  BankBranchMasterEntity getByBranchName( @Param("branchName") String phone );
	
	@Query("select a from BankBranchMasterEntity a where a.address = :address")
	  BankBranchMasterEntity getByAddress( @Param("address") String phone );
	
	@Query("select a from BankBranchMasterEntity a where not a.id = :id")
	 List< BankBranchMasterEntity> getByExcepted( @Param("id") UUID id );
	
	@Query("select a from BankBranchMasterEntity a where a.status = true and a.bankNameId =:bankname ORDER BY a.branchName ASC")
	 List <BankBranchMasterEntity> getByBankName(@Param("bankname") BankNameMasterEntity bankname);
	
	
	@Query("select a from BankBranchMasterEntity a where a.landlineNumber = :landlineNumber")
	  BankBranchMasterEntity getByLandLineNumber( @Param("landlineNumber") Long landlineNumber );
	
	@Query("select a from BankBranchMasterEntity a where UPPER(a.branchIfscCode) LIKE :branchIfscCode%")
	Optional<BankBranchMasterEntity> findByIfscCode( @Param("branchIfscCode") String branchIfscCode );

	@Query("select a from BankBranchMasterEntity a where a.branchIfscCode= :branchIfscCode")
	List<BankBranchMasterEntity> code( @Param ("branchIfscCode")String branchIfscCode);

}
