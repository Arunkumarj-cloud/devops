package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ReligionMasterDao;
import com.oasys.uppcl_user__master_management.entity.ReligionMaster;
import com.oasys.uppcl_user__master_management.repository.ReligionMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;


@Repository
@Log4j2
public class ReligionMasterDaoImpl implements ReligionMasterDao {

	@Autowired
	ReligionMasterRepository religionMasterRepository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MessageSource messageSource;


	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== ReligionMasterDaoImpl.getAll() Started ====>");
		List<ReligionMaster> religionList = null;
		try {

			religionList = religionMasterRepository.findAll();
			if (religionList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(religionList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get all details");
			} else {
				//log.warn("religion List is Empty");
				response.setMessage("religion List is Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception ReligionMasterDaoImpl.getAll() ==>", e);
		}
		//log.info("<==== ReligionMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public ReligionMaster save(ReligionMaster religion) {
		//log.info("<== Started ReligionMasterDaoImpl.create() ==>");
		ReligionMaster religionMaster	= religionMasterRepository.save(religion);
		//log.info("<== Ended ReligionMasterDaoImpl.create() ==>");
		return religionMaster;
	}
	
	public ReligionMaster findByReligionName(String religion) {
		//log.info("<== Started ReligionMasterDaoImpl.create() ==>");
		ReligionMaster religionMaster	= religionMasterRepository.findByNameIgnoreCase(religion);
		//log.info("<== Ended ReligionMasterDaoImpl.create() ==>");
		return religionMaster;
	}
	
	public List<ReligionMaster> findByIdNotIn(UUID id) {
		//log.info("<== Started ReligionMasterDaoImpl.create() ==>");
		List<ReligionMaster> religionMaster	= religionMasterRepository.findByIdNotIn(id);
		//log.info("<== Ended ReligionMasterDaoImpl.create() ==>");
		return religionMaster;
	}

	
	public Optional<ReligionMaster> getById(UUID id) {
		//log.info("<== Started ReligionMasterDaoImpl.getById ==>");
			Optional<ReligionMaster> religionMaster = religionMasterRepository.findById(id);		
		//log.info("<== Ended ReligionMasterDaoImpl.getById ==>");
		return religionMaster;
	}
	
	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  ReligionMasterDaoImpl.delete() ===> ");
		BaseDTO response = new BaseDTO();
		ReligionMaster contents = new ReligionMaster();
		try {
			contents = religionMasterRepository.getOne(id);
			if (contents.getId() != null) {
		         // log.info("Religion Id {}",id);
				religionMasterRepository.delete(contents);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Religion deleted successfully");
			} else {
				//log.error("religion ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- ReligionMasterDaoImpl.delete()  exception----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<====Ended ReligionMasterDaoImpl.delete() ===> ");
		return response;
	}

	public BaseDTO getAllLazy(PaginationRequestDTO requestData) {
		//log.info("<== ReligionMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<ReligionMaster> religionMasterList = null;
		List<ReligionMaster> contentList = new ArrayList<ReligionMaster>();
		Pageable pageRequest;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					religionMasterList = religionMasterRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					religionMasterList = religionMasterRepository.search(pageRequest, requestData.getSearch());
				}
			} else {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					religionMasterList = religionMasterRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					religionMasterList = religionMasterRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (religionMasterList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(religionMasterList.getNumberOfElements());
				response.setTotalRecords(religionMasterList.getTotalElements());
				response.setTotalPages(religionMasterList.getTotalPages());
				for (ReligionMaster i : religionMasterList) {
					contentList.add(i);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- ReligionMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== ReligionMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------ReligionMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<ReligionMaster> religionTypeMaster = null;
		try {
			religionTypeMaster = religionMasterRepository.getAllactive();
			if (religionTypeMaster != null) {
				response.setResponseContents(religionTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Religion Get all Active Details Fetched Successfully");
			} else {
				//log.error("No religion type is Active");
				response.setMessage("No religion type is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------ReligionMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------ReligionMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}


	
}
