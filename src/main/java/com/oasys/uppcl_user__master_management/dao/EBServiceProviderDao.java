//package com.oasys.uppcl_user__master_management.dao;
//
//import java.util.UUID;
//
//import com.oasys.config.BaseDTO;
//import com.oasys.uppcl_user__master_management.dto.EBServiceProviderDTO;
//import com.oasys.uppcl_user__master_management.dto.SearchDTO;
//
//public interface EBServiceProviderDao {
//	
//	BaseDTO create(EBServiceProviderDTO dto);
//
//	BaseDTO delete(UUID id);
//
//	BaseDTO update(UUID id, EBServiceProviderDTO dto);
//
//	BaseDTO getAllActive();
//
//	BaseDTO getBySateId(UUID id);
//
//	BaseDTO getById(UUID id);
//
//	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);
//
//	BaseDTO getByName(String name);
//
//	BaseDTO getByNameAndId(String name, UUID id);
//
//	BaseDTO getAllOrderByModifiedDate();
//
//}
