package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.TypeOfAccountMaster;



public interface TypeOfAccountRepository extends JpaRepository<TypeOfAccountMaster, UUID> {

	@Query("select a from TypeOfAccountMaster a where a.status = true ORDER BY a.createdDate ASC")
	List<TypeOfAccountMaster> getAllActive();

	@Query("select a from TypeOfAccountMaster a where UPPER(a.accountType) LIKE %:value%")
	Page<TypeOfAccountMaster> search(Pageable pageable, @Param("value") String value);

	TypeOfAccountMaster findByAccountType(String type);

	@Query("select a from TypeOfAccountMaster a where a.id != :id")
	List<TypeOfAccountMaster> findByIdNotIn(UUID id);
	
	TypeOfAccountMaster findByAccountTypeIgnoreCase(String accountType);
}
