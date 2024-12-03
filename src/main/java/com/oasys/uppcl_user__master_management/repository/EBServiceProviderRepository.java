//package com.oasys.uppcl_user__master_management.repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.oasys.uppcl_user__master_management.entity.EBServiceProviderEntity;
//
//public interface EBServiceProviderRepository extends JpaRepository<EBServiceProviderEntity, UUID> {
//
//	@Query("select a from EBServiceProviderEntity a where a.status = true ORDER by a.serviceProviderName ")
//	List<EBServiceProviderEntity> getAllActive();
////	where a.status = true
//	@Query("select a from EBServiceProviderEntity a where UPPER(a.serviceProviderName)=:serviceProviderName and a.id != :id")
//	List<EBServiceProviderEntity> checkCode(@Param("serviceProviderName") String serviceProviderName,@Param("id") UUID id);
//
//	@Query("select a from EBServiceProviderEntity a where (UPPER(a.serviceProviderName) LIKE %:serviceProviderName%) and a.stateId.id =:stateid")
//	Optional<EBServiceProviderEntity> check(@Param("serviceProviderName") String serviceProviderName,
//			@Param("stateid") UUID stateid);
//
//	@Query("select a from EBServiceProviderEntity a where a.status = true and a.stateId.id =:stateid ORDER by a.serviceProviderName ASC")
//	List<EBServiceProviderEntity> findByStateId(@Param("stateid") UUID stateid);
//
//	@Query("select a from EBServiceProviderEntity a where a.status = true and a.serviceProviderName=:search ORDER by a.serviceProviderName")
//	List<EBServiceProviderEntity> getAllActiveWithSearch(@Param("search") String search);
//
//	@Query("select a from EBServiceProviderEntity a where a.serviceProviderName =:serviceProviderName")
//	EBServiceProviderEntity getByName(@Param("serviceProviderName") String serviceProviderName);
//	
//	List<EBServiceProviderEntity> findByServiceProviderNameIgnoreCase(String name);
//	
////	@Query("select a from EBServiceProviderEntity a where UPPER(a.serviceProviderName)=:serviceProviderName and a.serviceCategoryId.id =:servicecategoryid")
////	EBServiceProviderEntity getByNameAndId(@Param("serviceProviderName") String serviceProviderName,
////			@Param("servicecategoryid") UUID servicecategoryid);
//	
////	
////	@Query("select a from EBServiceProviderEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid")
////	EBServiceProviderEntity findByServiceCategoryId(@Param("servicecategoryid") UUID servicecategoryid);
//	
//	@Query("select a from EBServiceProviderEntity a where a.status = true and a.stateId.id =:stateid and UPPER(a.serviceProviderName)=:serviceProviderName")
//	EBServiceProviderEntity findByServiceProviderNameAndStateId(@Param("serviceProviderName") String serviceProviderName,@Param("stateid") UUID stateid);
//	
//	@Query("select a from EBServiceProviderEntity a ORDER by a.modifiedDate DESC")
//	List<EBServiceProviderEntity> getAllOrderByModifiedDate();
//
//
//}
