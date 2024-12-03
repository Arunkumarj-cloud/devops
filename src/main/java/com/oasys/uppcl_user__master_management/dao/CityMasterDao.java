package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.CityMasterDTO;
import com.oasys.uppcl_user__master_management.entity.CityMasterEntity;

public interface CityMasterDao {
	BaseDTO create(CityMasterDTO cityMasterDTO);
	List<CityMasterEntity>  getAll();
	BaseDTO update( UUID id,CityMasterDTO cityMasterDTO);

}
