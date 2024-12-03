package com.oasys.uppcl_user__master_management.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oasys.uppcl_user__master_management.entity.SearchFieldsEntity;

public interface SearchFieldsRepository extends JpaRepository<SearchFieldsEntity, UUID> {

	@Query("Select a from SearchFieldsEntity a where a.serviceId=:id order by a.showOrder asc")
	List<SearchFieldsEntity> getByServiceId(@Param("id") UUID id);

}
