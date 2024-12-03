package com.oasys.uppcl_user__master_management.service.impl;


import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ProofTypeProofMappingDao;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ProofTypeProofMappingService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProofTypeProofMappingServiceImpl implements ProofTypeProofMappingService  {
	
		@Autowired
		ProofTypeProofMappingDao proofTypeProofMappingDao;

	@Autowired
	MessageSource messageSource;
	
	public BaseDTO create(ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info(" <----- ProofTypeProofMapping create Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.create(proofTypeProofMappingDTO);
		} catch(DataIntegrityViolationException e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping create Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO update(UUID id, ProofTypeProofMappingDTO proofTypeProofMappingDTO) {
		//log.info(" <----- ProofTypeProofMapping update Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.update(id,proofTypeProofMappingDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping update Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ProofTypeProofMapping getById Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.getById(id);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping getById Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAll() {
		//log.info(" <----- ProofTypeProofMapping getAll Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.getAll();
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.getAll() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping getAll Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAllActive() {
		//log.info(" <----- ProofTypeProofMapping getAllActive Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping getAllActive Service END -----> ");
		return baseDTO;
	}

	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ProofTypeProofMapping getLazyList Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.getLazyList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping getLazyList Service END -----> ");
		return baseDTO;
	}

	public BaseDTO delete(UUID id) {
		//log.info(" <----- ProofTypeProofMapping delete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.delete(id);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping delete Service END -----> ");
		return baseDTO;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- ProofTypeProofMapping softDelete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete ProofTypeProofMapping");
		}
		//log.info(" <----- ProofTypeProofMapping softDelete Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getByProofTypeId(UUID id) {
		//log.info(" <----- ProofTypeProofMapping getByProofTypeId Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = proofTypeProofMappingDao.getByProofTypeId(id);
		} catch (Exception e) {
			log.error("<---- ProofTypeProofMappingServiceImpl.getByProofTypeId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- ProofTypeProofMapping getByProofTypeId Service END -----> ");
		return baseDTO;
	}
	
}
