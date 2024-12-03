package com.oasys.uppcl_user__master_management.dao;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.FaqCategoryDTO;
@Service
public interface FaqCategoryDao {
	BaseDTO create(FaqCategoryDTO faqCategoryDTO);
	
	BaseDTO update(UUID id,FaqCategoryDTO faqCategoryDTO);
	
	BaseDTO getByID(UUID id);
	
	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO delete(UUID id);
	
	BaseDTO lazylist(PaginationRequestDTO paginationRequestDTO);
}