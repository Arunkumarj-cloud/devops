package com.oasys.uppcl_user__master_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.PrivilegenameEntity;
@Repository
public interface PrivilageRepository extends JpaRepository<PrivilegenameEntity, String> {

//	public List<PrivilegenameEntity> findAllByOrderByCreatedDateAsc();
	
	@Query("select a from PrivilegeEntity a where a.privilegeName = :privilage")
		 List <PrivilegenameEntity> findByprivilegeName(@Param("privilage") String privilage);
	
	
}
