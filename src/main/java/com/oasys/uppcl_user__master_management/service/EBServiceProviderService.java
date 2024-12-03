//package com.oasys.uppcl_user__master_management.service;
//
//import java.util.UUID;
//
//import javax.validation.Valid;
//
//import com.oasys.config.BaseDTO;
//import com.oasys.uppcl_user__master_management.dto.EBServiceProviderDTO;
//import com.oasys.uppcl_user__master_management.dto.SearchDTO;
//
//public interface EBServiceProviderService {
//
//	BaseDTO getAllActive();
//
//	BaseDTO update(UUID id, @Valid EBServiceProviderDTO dto);
//
//	BaseDTO delete(UUID id);
//
//	BaseDTO create(@Valid EBServiceProviderDTO dto);
//
//	BaseDTO getBySateId(UUID id);
//
//	BaseDTO getById(UUID id);
//
//	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);
//
//	BaseDTO getByName(String name);
//	
//	BaseDTO getByNameAndId(String name,UUID id);
//
//	BaseDTO getAllOrderByModifiedDate();
//}
