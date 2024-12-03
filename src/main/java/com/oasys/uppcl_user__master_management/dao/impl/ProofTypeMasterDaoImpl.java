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
import com.oasys.uppcl_user__master_management.dao.ProofTypeMasterDao;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ProofTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ProofTypeMasterDaoImpl implements ProofTypeMasterDao {
	
	@Autowired
	ProofTypeMasterRepository repository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MessageSource messageSource;

	public BaseDTO getAll() {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<==== ProofTypeMasterDaoImpl.getAll() Started ====>");
		List<ProofTypeMasterEntity> prooftypeList = null;
	
	prooftypeList = repository.findAll(Sort.by(Direction.ASC, "proofTypeName"));
	try
	{
			if (prooftypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setResponseContents(prooftypeList);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get All Details");
			} else {
				//log.warn("Proof type Details List is Empty");
				baseDTO.setMessage("proof Type Details List is Empty");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
	}
	catch(Exception e)
	{
		log.error("<== Exception ProofTypeMasterDaoImpl.getAll() ==>", e);
	}
		//log.info("<==== ProofTypeMasterDaoImpl.getAll() Ended ====>");
		return baseDTO;
	}
	public ProofTypeMasterEntity save( ProofTypeMasterEntity proofTypeMaster) {
		log.info(" Started ProofMasterDaoImpl.save() ");
		ProofTypeMasterEntity proofTypeMasterEntity = repository.save(proofTypeMaster);
		log.info(" Started ProofMasterDaoImpl.save() ");
		return proofTypeMasterEntity;
	}

	public ProofTypeMasterEntity findByProofTypeName( String proofType) {
		//log.info(" Started ProofMasterDaoImpl.findByProofTypeName() ");
		ProofTypeMasterEntity proofTypeMasterEntity = repository.findByProofTypeNameIgnoreCase(proofType);
		//log.info(" Started ProofMasterDaoImpl.findByProofTypeName() ");
		return proofTypeMasterEntity;
	}
	
	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  ProofTypeMasterDaoImpl.delete() ===> ");
		BaseDTO baseDTO = new BaseDTO();
		ProofTypeMasterEntity proofTypeMaster = new ProofTypeMasterEntity();
	try
	{
			proofTypeMaster = repository.getOne(id);
			if (proofTypeMaster.getId() != null) {
				repository.delete(proofTypeMaster);
				//log.info("Successfully deleted");
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				//log.error("proof type ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
	}
	catch(Exception e)
	{
			//log.error("<---- ProofTypeMasterDaoImpl.delete()  exception----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
	}
		//log.info("<====Ended ProofTypeMasterDaoImpl.delete() ===> ");
		return baseDTO;
	}

	public Optional<ProofTypeMasterEntity> getById(UUID id) {
		//log.info("<==== Started ProofTypeMasterDaoImpl.getById() ===> ");
		Optional<ProofTypeMasterEntity> proofType = repository.findById(id);
		//log.info("<==== Ended ProofTypeMasterDaoImpl.getById() ===> ");
		return proofType;
	}
	
	  
	public BaseDTO getAllLazy(PaginationRequestDTO requestData) {
		//log.info("<== ProofTypeMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO baseDTO = new BaseDTO();
		Page<ProofTypeMasterEntity> proofTypeMasterList = null;
		List<ProofTypeMasterEntity> contentsList = new ArrayList<ProofTypeMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- ProofTypeMasterDaoImpl.getAllLazy() ---------- >>>>>>>");
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					proofTypeMasterList = repository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					proofTypeMasterList = repository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					proofTypeMasterList = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					proofTypeMasterList = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (proofTypeMasterList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setNumberOfElements(proofTypeMasterList.getNumberOfElements());
				baseDTO.setTotalRecords(proofTypeMasterList.getTotalElements());
				baseDTO.setTotalPages(proofTypeMasterList.getTotalPages());
				for (ProofTypeMasterEntity proofTypMaster : proofTypeMasterList) {
					contentsList.add(proofTypMaster);
				}
				baseDTO.setResponseContents(contentsList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- ProofTypeMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== ProofTypeMasterDaoImpl.getAllLazy() Ended ==>");
		return baseDTO;
	}
	 
	
	public BaseDTO getAllActive() {
		//log.info("<------ProofTypeMasterDaoImpl.getAllActive()------> Started");
		BaseDTO baseDTO = new BaseDTO();
		List<ProofTypeMasterEntity> proofTypeMaster = null;
		try {
			proofTypeMaster = repository.getAllactive(Sort.by(Direction.ASC, "proofTypeName"));
			if (proofTypeMaster != null) {
				baseDTO.setResponseContents(proofTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get All Active Details");
			} else {
				//log.warn("No proof Type is Active");
				baseDTO.setMessage("No proof Type is Active");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------ProofTypeMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<----ProofTypeMasterDaoImpl.getAllActive()------> Ended");
		return baseDTO;
	}
	
}
