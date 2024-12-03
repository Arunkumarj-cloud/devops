package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.DistrictMasterDTO;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;

@Service
public interface DistrictMasterDAO {

	BaseDTO create(DistrictMasterDTO districtMasterDTO);
	
	List<DistrictMasterEntity> getAll();
	
	BaseDTO getById(UUID id);
	
	BaseDTO getByIds(Set<UUID> id);
	
	BaseDTO delete(UUID id);
	
	BaseDTO update(UUID id,DistrictMasterDTO districtMasterDTO);

	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO getAllActive();

	BaseDTO softDelete(UUID id);
	
	BaseDTO getByStateId(UUID id);

	BaseDTO getBydistricName(String districName, UUID stateId);

	BaseDTO getByName(String name);


}
