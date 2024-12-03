package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.PackageEntity;


@Repository
public interface PackageRepository  extends JpaRepository<PackageEntity, UUID>{
	
	
	 @Query("SELECT p FROM PackageEntity p where UPPER(p.name) =:name")
	  Optional<PackageEntity> findByPackageName(@Param("name") String name);
	  
  @Query("SELECT p FROM PackageEntity p where UPPER(p.isDefaultPackage) =:isDefaultPackage")
	   Optional<PackageEntity> findByIsDefaultPackage(@Param("isDefaultPackage") Boolean isDefaultPackage);

	  
	 @Query("SELECT new map(p.id as id, p.name as name,p.amount as amount, p.isDefaultPackage as isDefaultPackage, p.status as status, p.modifiedDate as modifiedDate) FROM PackageEntity p where p.status =:status order by p.modifiedDate desc") 
	 List<Map<String,String>> findByStatus(@Param("status") Boolean status);
	 
	@Query("SELECT p FROM PackageEntity p where UPPER(p.id) !=:id")
	List<PackageEntity> getAllPackagesNotInId(@Param("id") UUID id);
	
	@Query("SELECT new map(p.id as id, p.name as name,p.amount as amount, p.isDefaultPackage as isDefaultPackage, p.status as status, p.modifiedDate as modifiedDate) FROM PackageEntity p order by p.modifiedDate desc")
	List<Map<String,String>> findAllPackages();
	
	@Query("SELECT case when (count(p) > 0)  then true else false end  FROM PackageEntity p inner join p.servceCategoryList s where p.isDefaultPackage= true and s.id=:serviceCategoryId")
	Boolean isServiceExistInDefaultPackage(	@Param("serviceCategoryId") UUID serviceCategoryId);

}


