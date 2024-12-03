package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.FaqDTO;
import com.oasys.uppcl_user__master_management.dto.FaqRequestDTO;

@Service
public interface FaqService {

	BaseDTO create(FaqDTO faqDTO);
	
	BaseDTO createList(List<FaqDTO> faqDTO);
	
	BaseDTO FAQGetAll();

	BaseDTO updateList(List<FaqDTO> faqDTO);
	
	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getByCategoryId(UUID id);
	
	BaseDTO getByCategoryIdLang(UUID id, String language);
	
	BaseDTO LazyList(PaginationRequestDTO paginationRequestDTO);
	
	BaseDTO deleteById(UUID id);

	BaseDTO CategoryLazyList(PaginationRequestDTO paginationRequestDTO);
	
	BaseDTO softDelete(UUID id);

	BaseDTO softDeleteAll(FaqRequestDTO listOfIds);

	BaseDTO getDetailsById(UUID id);

	BaseDTO updateFaq(UUID id, FaqDTO request);

}
