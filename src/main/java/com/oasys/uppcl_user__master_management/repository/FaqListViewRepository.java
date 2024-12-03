//package com.oasys.uppcl_user__master_management.repository;
//
//import java.util.UUID;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.oasys.uppcl_user__master_management.entity.FaqListViewEntity;
//
//
//public interface FaqListViewRepository extends JpaRepository<FaqListViewEntity, UUID>{
//	
//	@Query("SELECT a FROM FaqListViewEntity a WHERE UPPER(a.categoryName) LIKE %:value%")
//	Page<FaqListViewEntity> search(Pageable pageable, @Param("value") String value);
//}
