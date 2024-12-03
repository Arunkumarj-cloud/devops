package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.MaritalStatusMasterEntity;

public interface MaritalStatusDao {
	MaritalStatusMasterEntity save(MaritalStatusMasterEntity maritalStatus);

	public BaseDTO getMaritalStatus();

	Optional<MaritalStatusMasterEntity> getMaritalStatusById(UUID id);

	public BaseDTO softDelete(UUID id);

	public BaseDTO getAllMaritalStatuslazy(PaginationRequestDTO pageData);

	public BaseDTO getAllActive();

	public BaseDTO deleteMarital(UUID id);

	MaritalStatusMasterEntity findByName(String maritalStatus);

	List<MaritalStatusMasterEntity> findByIdNotIn(UUID id);

	MaritalStatusMasterEntity findByNameIgnoreCase(String name);
}
