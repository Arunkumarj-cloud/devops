package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, UUID> {

	@Query("select a from SubCategoryEntity a ORDER by a.name")
	List<SubCategoryEntity> getAllActive();

	@Query("select a from SubCategoryEntity a where a.name=:name")
	List<SubCategoryEntity> checkCode(@Param("name") String name);

	@Query("select a from SubCategoryEntity a where (UPPER(a.name) LIKE %:name%) and a.serviceCategoryId.id =:servicecategoryid")
	Optional<SubCategoryEntity> check(@Param("name") String name, @Param("servicecategoryid") UUID servicecategoryid);

	@Query("select a from SubCategoryEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid ORDER by a.name ASC")
	List<SubCategoryEntity> findByServiceCategoryId(@Param("servicecategoryid") UUID servicecategoryid);

	@Query("select a from SubCategoryEntity a where a.status = true and a.name =:search ORDER by a.name")
	List<SubCategoryEntity> getAllActiveWithSearch(@Param("search") String search);

	List<SubCategoryEntity> findByConstantNameIgnoreCase(String name);
	
	SubCategoryEntity findByConstantName(String name);

	@Query("select a from SubCategoryEntity a where a.status = true and a.serviceCategoryId.id =:servicecategoryid and UPPER(a.name)=:categoryName")
	SubCategoryEntity findByServiceAndCategory(@Param("servicecategoryid") UUID servicecategoryid,@Param("categoryName") String categoryName);
	
	@Query("select a from SubCategoryEntity a ORDER by a.modifiedDate DESC ")
	List<SubCategoryEntity> getAllOrderByModifiedDate();
}
