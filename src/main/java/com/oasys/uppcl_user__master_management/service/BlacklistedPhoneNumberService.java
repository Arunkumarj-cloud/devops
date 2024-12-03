package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.oasys.config.BaseDTO;
import com.oasys.config.FileUploadResponseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.BlacklistedPhoneDTO;

public interface BlacklistedPhoneNumberService {
	FileUploadResponseDTO upload(MultipartFile file);
	BaseDTO validate(String phoneNumber);
	BaseDTO create(BlacklistedPhoneDTO blacklistedPhoneDTO);
	BaseDTO update(UUID id, String phoneNumber);
	BaseDTO getById(UUID id);
	BaseDTO getAll();
	BaseDTO getLazyList(PaginationRequestDTO requestData);


}
