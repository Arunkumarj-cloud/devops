package com.oasys.uppcl_user__master_management.service;



import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.TalukMasterDTO;


public interface TalukMasterService {
	
    BaseDTO create(TalukMasterDTO talukMasterDTO);
    BaseDTO getAll();
    BaseDTO update(UUID id, TalukMasterDTO talukMasterDTO);
    BaseDTO getById(UUID id);
    BaseDTO getAllActive();
    BaseDTO softDelete(UUID id);
    BaseDTO getByDistrictId(UUID id);
    BaseDTO lazylist(PaginationRequestDTO paginationRequestDTO);
	
	

}
