package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.UserTypeRoleMappingMaster;



public interface UserTypeRoleMappingRepository extends JpaRepository<UserTypeRoleMappingMaster, UUID> {
	
	@Query("select a from UserTypeRoleMappingMaster a where a.status = true")
	 List <UserTypeRoleMappingMaster> getAllActive();

	@Query("select a from UserTypeRoleMappingMaster a where a.userTypeId =:id and a.status = true and a.userTypeId.status=true and a.roleId.status=true")
	 List<UserTypeRoleMappingMaster> findByUserTypeId(@Param("id") UserTypeMasterEntity id);

	@Query("select a from UserTypeRoleMappingMaster a where a.userTypeId =:id")
	List<UserTypeRoleMappingMaster> findUserTypeId(@Param("id") UserTypeMasterEntity id);
	
	@Query("select a from UserTypeRoleMappingMaster a where a.userTypeId.id =:userTypeId and a.roleId.id = :roleId and a.status = true and a.userTypeId.status=true and a.roleId.status=true")
	 Optional<UserTypeRoleMappingMaster> check(@Param("userTypeId") UUID userTypeId,@Param("roleId") UUID roleId);

	
}
