package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.PinCodeMasterDao;
import com.oasys.uppcl_user__master_management.entity.PinCodeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.PinCodeMasterRepository;

@Service
public class PinCodeMasterDaoImpl implements PinCodeMasterDao {
	
	
	@Autowired
	PinCodeMasterRepository pinCodeMasterRepository;

	@Override
	public PinCodeMasterEntity findByPinCode(String pinCode) {
		PinCodeMasterEntity entity = pinCodeMasterRepository.findByPinCode1(pinCode);
		return entity;
	}


	@Override
	public PinCodeMasterEntity save(PinCodeMasterEntity regEntity) {
		// TODO Auto-generated method stub
		PinCodeMasterEntity entity  = new PinCodeMasterEntity();
		return entity;
	}
	
	
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		Page<PinCodeMasterEntity> entity = null;
		List<PinCodeMasterEntity> contentsList = new ArrayList<PinCodeMasterEntity>();
		Pageable pageRequest;
		try {if (!StringUtils.isEmpty(pageData.getSearch())) {
			if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
				pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
						Sort.by(Direction.ASC, pageData.getSortField()));
				entity = pinCodeMasterRepository.search1(pageRequest, pageData.getSearch());
			} else {
				pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
						Sort.by(Direction.DESC, pageData.getSortField()));
				entity = pinCodeMasterRepository.search1(pageRequest, pageData.getSearch());
			}
		} else {

			if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
				entity = pinCodeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
						pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
			} else {
				entity = pinCodeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
						pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
			}
		}
			
			if (entity.isEmpty()) {
				response.setMessage("No Record Found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			} else {
				
				response.setMessage("Successfully Get Details");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				for (PinCodeMasterEntity pinCodeMasterEntityList : entity) {
				
					contentsList.add(pinCodeMasterEntityList);
					
				}
				response.setResponseContents(contentsList);
				response.setNumberOfElements(entity.getNumberOfElements());
				response.setTotalRecords(entity.getTotalElements());
				response.setTotalPages(entity.getTotalPages());
				
			}
		} catch (Exception e) {
		
			response.setMessage("unable to get");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			
		}
		
		return response;
	}


}
