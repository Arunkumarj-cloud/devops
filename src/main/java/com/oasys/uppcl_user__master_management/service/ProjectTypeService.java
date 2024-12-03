package com.oasys.uppcl_user__master_management.service;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeRequestDTO;
@Service
@Component
public interface ProjectTypeService {
	
	BaseDTO add(ProjectTypeRequestDTO requestDTO);
	BaseDTO update(ProjectTypeRequestDTO requestDTO);
	BaseDTO getById(UUID id);
	BaseDTO getBySearchFilter(PaginationRequestDTO requestDTO) ;
	BaseDTO getDistributor(UUID roleId, UUID  userTypeId);
	BaseDTO getAllActive();
	BaseDTO getByName(String name);
	BaseDTO getAll();
}
