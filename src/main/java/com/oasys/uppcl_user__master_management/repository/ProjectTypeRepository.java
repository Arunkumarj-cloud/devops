package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.oasys.uppcl_user__master_management.entity.ProjectTypeEntity;




public interface ProjectTypeRepository extends JpaRepository<ProjectTypeEntity, UUID> {

	@Query("select p from ProjectTypeEntity p where upper(p.name)=:name")
	Optional<ProjectTypeEntity> findByNameIgnoreCase(@Param("name") String name);

	@Query("select p from ProjectTypeEntity p where upper(p.name)=:name and p.id !=:id")
	Optional<ProjectTypeEntity> findByNameNotInId(@Param("name") String name, @Param("id") UUID id);
	
	@Query("Select p from ProjectTypeEntity p where p.name ='OssAdmin'")
	Optional<ProjectTypeEntity> getAdminApplicationdetails();
	
	@Query("Select p from ProjectTypeEntity p where p.status =true ORDER BY p.modifiedDate Desc")
	List<ProjectTypeEntity> getAllActive();
	
	@Query("Select p from ProjectTypeEntity p ORDER BY p.modifiedDate Desc")
	List<ProjectTypeEntity> getAll();
}
