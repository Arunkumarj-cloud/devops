package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.uppcl_user__master_management.dto.CityMasterDTO;

public interface CityMasterService {

	 BaseDTO create(CityMasterDTO cityMasterDTO);
	 BaseDTO getAll();
	 BaseDTO update( UUID id,CityMasterDTO cityMasterDTO);

}
