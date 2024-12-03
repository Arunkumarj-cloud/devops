package com.oasys.uppcl_user__master_management.service;





import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PackageRequestDTO;


public interface PackageService {
	
	BaseDTO save(PackageRequestDTO requestDTO);
	BaseDTO update(PackageRequestDTO requestDTO);
	BaseDTO getByPackageId(UUID packageId);
	BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO);
	BaseDTO getAllPackageListByStatus(Boolean status);
	BaseDTO getDefaultPackage() ;
}



