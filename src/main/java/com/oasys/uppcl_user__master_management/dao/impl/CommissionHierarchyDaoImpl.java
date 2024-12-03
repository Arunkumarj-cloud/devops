package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.CommissionHierarchyDao;
import com.oasys.uppcl_user__master_management.dto.CommissionHierarchyResponseDTO;
import com.oasys.uppcl_user__master_management.entity.CommissionHierarchyEntity;
import com.oasys.uppcl_user__master_management.mapper.CommissionHierarchyMapper;
import com.oasys.uppcl_user__master_management.repository.CommissionHierarchyRepository;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class CommissionHierarchyDaoImpl implements CommissionHierarchyDao {

	@Autowired
	CommissionHierarchyRepository commissionHierarchyRepository;

//	@Autowired
//	ModelRepository modelRepo;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	 private CommissionHierarchyMapper  commissionHierarchyMapper;  

	@Override
	public BaseDTO getAllActive(Boolean status) {
		BaseDTO response = new BaseDTO();
		List<CommissionHierarchyEntity> commissionHierarchyEntities = null;
		try {
			if (Objects.isNull(status)) {
				commissionHierarchyEntities = commissionHierarchyRepository.getAllActive();
			} else {
				commissionHierarchyEntities = commissionHierarchyRepository.getAllActive(status);
			}
            if(commissionHierarchyEntities.isEmpty()) {
				response.setMessage("No Record Found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			} else {
				List<CommissionHierarchyResponseDTO> commissionHierarchyResponseDTO = commissionHierarchyEntities
						.stream().map(commissionHierarchyMapper :: convertEntityToResponseDTO).collect(Collectors.toList());
				response.setResponseContents(commissionHierarchyResponseDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setMessage(" Get All Details Fetched successfully");
			}
		} catch (Exception e) {
			log.error("<----- CommissionHierarchyDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable To Get Details");
		}

		return response;
	}

}
