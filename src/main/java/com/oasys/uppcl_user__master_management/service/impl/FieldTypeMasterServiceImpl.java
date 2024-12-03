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
import com.oasys.uppcl_user__master_management.dao.FieldTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.FieldTypeMasterDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.FieldTypeMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FieldTypeMasterServiceImpl implements FieldTypeMasterService {

	@Autowired
	FieldTypeMasterDao fieldTypeMasterDao;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(FieldTypeMasterDTO fieldTypeMasterDTO) {
		//log.info(" <----- FieldTypeMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldTypeMasterDao.create(fieldTypeMasterDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldTypeMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- FieldTypeMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- FieldTypeMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldTypeMasterDao.getById(id);
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterServiceImpl.getById() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <-----FieldTypeMasterServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldTypeMasterDao.getAll();
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		//log.info(" <-----FieldTypeMasterServiceImpl.getAll() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- FieldTypeMasterServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = fieldTypeMasterDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterServiceImpl.getLazyList()-{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterServiceImpl.getLazyList() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, FieldTypeMasterDTO fieldTypeMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started FieldTypeMasterServiceImpl.update() ===>");
		try {
			response = fieldTypeMasterDao.update(id, fieldTypeMasterDTO);

		} catch (DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error(" Exception FieldTypeMasterServiceImpl.update()" + e);

			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended FieldTypeMasterServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started FieldTypeMasterServiceImpl.getAllActive() ===>");
		try {
			response = fieldTypeMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception FieldTypeMasterServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended FieldTypeMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started FieldTypeMasterServiceImpl.softDelete() ===>");
		try {
			response = fieldTypeMasterDao.softDelete(id);
		} catch (Exception e) {
			log.error(" Exception FieldTypeMasterServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended FieldTypeMasterServiceImpl.softDelete() ===>");
		return response;
	}
}
