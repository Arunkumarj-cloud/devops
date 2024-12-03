package com.oasys.uppcl_user__master_management.service;

import java.util.List;
import java.util.UUID;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ListDto;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SearchFiledsDto;
import com.oasys.uppcl_user__master_management.dto.ServiceDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceDeatilsDto;

public interface ServiceService {
	
	BaseDTO create(ServiceDTO serviceDTO);

	BaseDTO getById(UUID id);

	BaseDTO getAll();
	
	BaseDTO getAllActive();
	
	BaseDTO getLazyList(PaginationRequestDTO requestData);

	BaseDTO update(UUID id,ServiceDTO serviceDTO);
	
	BaseDTO delete(UUID id);
	
	BaseDTO softDelete(UUID id);
	
	BaseDTO getList(ListDto ids);

	BaseDTO getAllActiveWithSearch(SearchDTO searchDTO);
	
	BaseDTO getServiceAndProviderList(List<ServiceDeatilsDto> serviceDeatilsDtos);

	BaseDTO createSearchFields(SearchFiledsDto searchFiledsDto);

	BaseDTO getSearchFields(UUID id);

	BaseDTO getServiceList();

}
