package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;



public interface UserTypeRepository extends JpaRepository<UserTypeMasterEntity, UUID>{

	@Query("select a from UserTypeMasterEntity a where a.status = true ORDER by a.userType ASC")
	 List <UserTypeMasterEntity> getAllActive();
	
	@Query("select a from UserTypeMasterEntity a where UPPER(a.userType) LIKE %:value% or UPPER(a.description) like %:value%")
	 Page <UserTypeMasterEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("select a from UserTypeMasterEntity a where UPPER(a.userType) LIKE %:type%")
	 UserTypeMasterEntity getByUserType(@Param ("type") String type);
	
	@Query("select a from UserTypeMasterEntity a where not a.id=:id")
	List<UserTypeMasterEntity> getByExceptId(@Param ("id") UUID id);

	List<UserTypeMasterEntity> findByIdIn(List<UUID> list);
	
	
	 UserTypeMasterEntity getByUserTypeIgnoreCase(String userType);
}
