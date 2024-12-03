package com.oasys.uppcl_user__master_management.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.FaqDTO;
import com.oasys.uppcl_user__master_management.dto.FaqRequestDTO;
import com.oasys.uppcl_user__master_management.entity.FaqEntity;

@Service

public interface FaqDao {
	
    BaseDTO create(FaqDTO faqDTO);
	
	BaseDTO createList(List<FaqDTO> faqDTO);
	
	BaseDTO updateList(List<FaqDTO> faqDTO);
	
	BaseDTO FAQGetAll();
	
	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getByCategoryId(UUID id);
	
	BaseDTO getByCategoryIdLang(UUID id, String language);
	
	BaseDTO LazyList(PaginationRequestDTO paginationRequestDTO);
	
	BaseDTO deleteById(UUID id);
	
	FaqEntity save(FaqEntity faqEntity);

	FaqEntity getById(UUID id);

	BaseDTO softDeleteAll(FaqRequestDTO listOfIds);

	BaseDTO getDetailsById(UUID id);
}


