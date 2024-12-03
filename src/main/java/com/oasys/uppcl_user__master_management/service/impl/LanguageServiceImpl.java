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
import com.oasys.uppcl_user__master_management.dao.LanguageDao;
import com.oasys.uppcl_user__master_management.dto.LanguageDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.LanguageService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	LanguageDao languageDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(LanguageDTO languageDTO) {
		//log.info(" <----- Language create Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.create(languageDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- LanguageServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- LanguageServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language create Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id,LanguageDTO languageDTO) {
		//log.info(" <----- Language update Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.update(id,languageDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- LanguageServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- LanguageServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language update Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- Language getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.getById(id);
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language getById Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Language getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.getAll();
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAll Language");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language getAll Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Language getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language getAllActive Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Language getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Language delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.delete(id);
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- Language delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = languageDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Language");
		}
		//log.info(" <----- Language softDelete Service END -----> ");
		return response;
	}
 

}
