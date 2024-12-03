package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.FaqEntity;


@Repository

public interface FaqRepository  extends JpaRepository<FaqEntity, UUID>{
	
	@Query("select a from FaqEntity a where a.categoryId.id =:type and a.status=true")
	 List <FaqEntity> getByCategoryId(@Param("type") UUID type);
	
	@Query("select a from FaqEntity a where a.categoryId.id =:type and a.status=true and UPPER(a.language) like %:lang% ")
	 List <FaqEntity> getByCategoryIdLang(@Param("type") UUID type, @Param("lang") String lang);
//	@Query("SELECT a FROM FaqEntity a WHERE a.status = true")
//	 List <FaqEntity> getAllActive();
	//@Query("SELECT a FROM FaqEntity a WHERE a.faqType =:type and UPPER(a.question) LIKE %:value% or UPPER(a.answer) LIKE %:value% ")
	//Page<FaqEntity> search(Pageable pageable,@Param("value") String value,@Param("type") String type);

	//@Query("SELECT a FROM FaqEntity a WHERE a.status = true and a.faqType =:type ")
	//Page<FaqEntity> withoutSearch(Pageable pageable,@Param("type") String type);
	
	@Query("SELECT a FROM FaqEntity a WHERE UPPER(a.question) LIKE %:value% or UPPER(a.answer) LIKE %:value% ")
	Page<FaqEntity> search(Pageable pageable,@Param("value") String value);

	@Query("SELECT a FROM FaqEntity a WHERE a.status = true ")
	Page<FaqEntity> withoutSearch(Pageable pageable);
	
	@Query(nativeQuery = true, value="select * from faq  ORDER by created_date DESC")
	 List <FaqEntity> getAll();
	@Query("select a from FaqEntity a where a.status = true")
	 List <FaqEntity> getAllActive();
	
	@Query("select a from FaqEntity a where a.categoryId.id =:id and a.language =:language")
	FaqEntity getByCategoryIdAndLanguage(@Param("id") UUID id,@Param("language") String language);
	
}
