package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.LanguageMasterEntity;


public interface LanguageRepository extends JpaRepository<LanguageMasterEntity, UUID>{
	
	@Query("select a from LanguageMasterEntity a where a.status = true ORDER by a.languageName")
	 List <LanguageMasterEntity> getAllActive();
	
	@Query("select a from LanguageMasterEntity a WHERE UPPER(a.languageName) LIKE %:value% or UPPER(a.code) LIKE %:value%")
	 Page <LanguageMasterEntity> search(Pageable pageable,@Param("value") String value);
	
	@Query("select a from LanguageMasterEntity a where a.code =:code")
	List<LanguageMasterEntity> checkCode(@Param("code") String code);

}
