package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceTypeMasterEntity;

public interface ServiceTypeDao {

	ServiceTypeMasterEntity save(ServiceTypeMasterEntity serviceType);

	Optional<ServiceTypeMasterEntity> getById(UUID id);

	BaseDTO getAll();

	BaseDTO getAllActive();

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO delete(UUID id);

	BaseDTO softDelete(UUID id);

	BaseDTO fetchServiceTypeWithServiceList();

	ServiceTypeMasterEntity findByServiceType(String serviceType);

	List<ServiceTypeMasterEntity> findByIdNotIn(UUID id);

}
