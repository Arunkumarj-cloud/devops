package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.LanguageDTO;

public interface LanguageDao {
	BaseDTO create(LanguageDTO languageDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id,LanguageDTO languageDTO);
	
	BaseDTO delete(UUID id);
	
	BaseDTO softDelete(UUID id);
}
