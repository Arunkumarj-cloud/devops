package com.oasys.uppcl_user__master_management.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.ReligionMaster;

@Repository
public interface ReligionMasterRepository extends JpaRepository<ReligionMaster, UUID> {

	@Query("SELECT a FROM ReligionMaster a WHERE a.status =1 ORDER BY a.createdDate ASC")
	List<ReligionMaster> getAllactive();

	@Query("select a from ReligionMaster a where UPPER(a.name) LIKE %:value%")
	Page<ReligionMaster> search(Pageable pageable, @Param("value") String value);

	@Query("select a from ReligionMaster a where a.id != :id")
	List<ReligionMaster> findByIdNotIn(UUID id);

	ReligionMaster findByName(String religion);
	
	ReligionMaster findByNameIgnoreCase(String name);

}
