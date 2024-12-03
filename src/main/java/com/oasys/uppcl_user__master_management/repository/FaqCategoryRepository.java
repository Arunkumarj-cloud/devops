package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.FaqCategoryEntity;


@Repository

public interface FaqCategoryRepository extends JpaRepository<FaqCategoryEntity, UUID>{
	
	@Query("select a from FaqCategoryEntity a where a.categoryName =:value")
	Optional<FaqCategoryEntity> getByCategoryName(@Param("value") String value);
	
	@Query("select a from FaqCategoryEntity a where a.status = true")
	List<FaqCategoryEntity> getAllActive();
	
	@Query("SELECT a FROM FaqCategoryEntity a WHERE UPPER(a.categoryName) LIKE %:value% or UPPER(a.categoryDescription) LIKE %:value% ")
	Page<FaqCategoryEntity> search(Pageable pageable,@Param("value") String value);

	FaqCategoryEntity findByCategoryNameIgnoreCase(String categoryName);

}


