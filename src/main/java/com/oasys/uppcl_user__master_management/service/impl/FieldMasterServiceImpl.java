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
import com.oasys.uppcl_user__master_management.dao.FieldMasterDao;
import com.oasys.uppcl_user__master_management.dto.FieldMasterDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.FieldMasterService;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class FieldMasterServiceImpl implements FieldMasterService {

	@Autowired(required = false)
	FieldMasterDao fieldMasterDao;

	@Autowired(required = false)
	MessageSource messageSource;

	@Override
	public BaseDTO create(FieldMasterDTO fieldMasterDTO) {
		//log.info(" <----- FieldMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.create(fieldMasterDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldMasterServiceImpl.create() Service END -----> ");
		return response;
	}
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- FieldMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.getById(id);
		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		//log.info(" <----- FieldMasterServiceImpl.getById() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <-----FieldMasterServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.getAll();
		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		//log.info(" <-----FieldMasterServiceImpl.getAll() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----FieldMasterServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----FieldMasterServiceImpl.getAllActive() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <-----FieldMasterServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----FieldMasterServiceImpl.softDelete() Service END -----> ");
		return response;

	}

	@Override
	public BaseDTO lazyList(PaginationRequestDTO pageData) {
		//log.info(" <----- FieldMasterServiceImpl.lazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldMasterDao.lazyList(pageData);
		} catch (Exception e) {
			log.error("<---- FieldMasterServiceImpl.lazyList()-{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldMasterServiceImpl.lazyList() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, FieldMasterDTO fieldMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started FieldMasterServiceImpl.update() ===>");
		try {
			response = fieldMasterDao.update(id, fieldMasterDTO);

		} catch (DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error(" Exception FieldMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended FieldMasterServiceImpl.update() ===>");
		return response;
	}


	
}
