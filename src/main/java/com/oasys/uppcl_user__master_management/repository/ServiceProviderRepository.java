package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;

public interface ServiceProviderRepository extends JpaRepository<ServiceProviderEntity, UUID> {

	@Query("select a from ServiceProviderEntity a  ORDER by a.serviceProviderName")
	List<ServiceProviderEntity> getAllActive();
//	where a.status = true
	@Query("select a from ServiceProviderEntity a where UPPER(a.serviceProviderName)=:serviceProviderName and a.id != :id")
	List<ServiceProviderEntity> checkCode(@Param("serviceProviderName") String serviceProviderName,@Param("id") UUID id);

	@Query("select a from ServiceProviderEntity a where (UPPER(a.serviceProviderName) LIKE %:serviceProviderName%) and a.serviceCategoryId.id =:servicecategoryid")
	Optional<ServiceProviderEntity> check(@Param("serviceProviderName") String serviceProviderName,
			@Param("servicecategoryid") UUID servicecategoryid);

	@Query("select a from ServiceProviderEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid ORDER by a.serviceProviderName ASC")
	List<ServiceProviderEntity> findByCategoryId(@Param("servicecategoryid") UUID servicecategoryid);

	@Query("select a from ServiceProviderEntity a where a.status = true and a.serviceProviderName=:search ORDER by a.serviceProviderName")
	List<ServiceProviderEntity> getAllActiveWithSearch(@Param("search") String search);

	@Query("select a from ServiceProviderEntity a where a.serviceProviderName =:serviceProviderName")
	ServiceProviderEntity getByName(@Param("serviceProviderName") String serviceProviderName);
	
	List<ServiceProviderEntity> findByServiceProviderNameIgnoreCase(String name);
	
	@Query("select a from ServiceProviderEntity a where UPPER(a.serviceProviderName)=:serviceProviderName and a.serviceCategoryId.id =:servicecategoryid")
	ServiceProviderEntity getByNameAndId(@Param("serviceProviderName") String serviceProviderName,
			@Param("servicecategoryid") UUID servicecategoryid);
	
	
	@Query("select a from ServiceProviderEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid")
	ServiceProviderEntity findByServiceCategoryId(@Param("servicecategoryid") UUID servicecategoryid);
	
	@Query("select a from ServiceProviderEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid and UPPER(a.serviceProviderName)=:serviceProviderName")
	ServiceProviderEntity findByServiceProviderNameAndServiceId(@Param("serviceProviderName") String serviceProviderName,@Param("servicecategoryid") UUID servicecategoryid);
	
	@Query("select a from ServiceProviderEntity a ORDER by a.modifiedDate DESC")
	List<ServiceProviderEntity> getAllOrderByModifiedDate();


}
