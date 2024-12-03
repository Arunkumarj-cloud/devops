package com.oasys.uppcl_user__master_management.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PinCodeMasterDTO;


@Service
public interface PinCodeMasterService {
	
	BaseDTO create(PinCodeMasterDTO pinCodeMasterDTO);
	
	BaseDTO getAll();
	
	BaseDTO getById(UUID id);
	
	BaseDTO getByIds(Set<UUID> id);
	
	BaseDTO delete(UUID id);
	
	BaseDTO update(UUID id,PinCodeMasterDTO pinCodeMasterDTO);

	BaseDTO getAllActive();

	BaseDTO softDelete(UUID id);
	
	BaseDTO getByDistrictId(UUID id);
	BaseDTO findByPinCode(String pincode);

	BaseDTO uploadExcelFile(MultipartFile file);

	BaseDTO getLazyList(PaginationRequestDTO pageData);

}
