package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;


public interface MerchantTypeMasterRepository extends JpaRepository<MerchantTypeMasterEntity, UUID> {

	@Query("SELECT a FROM MerchantTypeMasterEntity a WHERE a.status =1 ORDER BY a.agentType ASC")
	List<MerchantTypeMasterEntity> getAllactive();

	@Query("SELECT mm FROM MerchantTypeMasterEntity mm where UPPER(mm.agentType) LIKE %:value% or UPPER(mm.description) like %:value%")
	Page<MerchantTypeMasterEntity> search(Pageable pageable, @Param("value") String value);

	List<MerchantTypeMasterEntity> findByIdIn(List<UUID> list);
	
	@Query("SELECT a FROM MerchantTypeMasterEntity a WHERE UPPER(a.agentType) LIKE %:name%")
	Optional<MerchantTypeMasterEntity> findByAgentType(@Param("name") String name);
	
	@Query("SELECT a FROM MerchantTypeMasterEntity a WHERE NOT a.id =:id")
	List<MerchantTypeMasterEntity> getByExceptId(@Param("id") UUID id);
	
	@Query("SELECT a FROM MerchantTypeMasterEntity a WHERE UPPER(a.agentType) LIKE %:name%")
	List<MerchantTypeMasterEntity> check(@Param("name") String name);
	
	@Query("SELECT a FROM MerchantTypeMasterEntity a WHERE a.agentType= :agentType")
	Optional<MerchantTypeMasterEntity> getByAgentType(@Param("agentType") String agentType);
	
	MerchantTypeMasterEntity findByAgentTypeIgnoreCase(String agentType);
 

}
