package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.TemplateDTO;

public interface TemlpateService {

	public BaseDTO createTemplate(TemplateDTO templateDTO);

	public BaseDTO getAll();
	
	public BaseDTO get();

	public BaseDTO getById(UUID id);

	public BaseDTO getByName(String name);

	public BaseDTO lazyList(PaginationRequestDTO pageData);
	
	public BaseDTO lazyList1(PaginationRequestDTO pageData);

	public BaseDTO update(UUID id, TemplateDTO templateDTO);

	public BaseDTO getAllActive();

	public BaseDTO softDelete(UUID id);

}
