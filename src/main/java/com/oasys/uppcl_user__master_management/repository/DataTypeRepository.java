package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oasys.uppcl_user__master_management.entity.DataTypeEntity;

@Repository

public interface DataTypeRepository  extends JpaRepository<DataTypeEntity, UUID> {

	@Query("select a from DataTypeEntity a where a.status= true")
	 List <DataTypeEntity> getAllActive();
	
	@Query("SELECT a FROM DataTypeEntity a WHERE UPPER(a.dataTypeName) LIKE %:value%")
	Page<DataTypeEntity> search(Pageable pageable, @Param("value") String value);
}
