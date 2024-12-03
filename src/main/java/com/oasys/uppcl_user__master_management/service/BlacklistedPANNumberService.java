package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.config.FileUploadResponseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BlacklistedPanDTO;

public interface BlacklistedPANNumberService {

	FileUploadResponseDTO upload(MultipartFile file);
	BaseDTO validate(String panNumber);
	BaseDTO create(BlacklistedPanDTO blacklistedPanDTO);
	BaseDTO update(UUID id, String panNumber);
	BaseDTO getById(UUID id);
	BaseDTO getAll();
	BaseDTO getLazyList(PaginationRequestDTO requestData);
}
