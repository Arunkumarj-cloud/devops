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
import com.oasys.uppcl_user__master_management.dao.BlockDao;
import com.oasys.uppcl_user__master_management.dto.BlockDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BlockService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BlockServiceImpl implements  BlockService{
	
	@Autowired
	BlockDao blockDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(BlockDTO blockDTO) {
		//log.info(" <----- BlockServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.create(blockDTO);
		} catch(DataIntegrityViolationException e) {
			log.error("<---- BlockServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- BlockServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.create() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id,BlockDTO blockDTO) {
		//log.info(" <----- BlockServiceImpl.update() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.update(id,blockDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- BlockServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- BlockServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.update() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- BlockServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.getById(id);
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Block");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- BlockServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.getAll();
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAll Block");
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.getAll() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- BlockServiceImpl.getAllActive()Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAllActive Block");
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.getAllActive()Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BlockServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to getLazyList Block");
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.getLazyList() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BlockServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.delete(id);
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Delete Block");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.delete() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BlockServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Block");
		}
		//log.info(" <----- BlockServiceImpl.softDelete() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getByDistrictId(UUID id) {
		//log.info(" <----- BlockServiceImpl.getByDistrictId() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = blockDao.getByDistrictId(id);
		} catch (Exception e) {
			log.error("<---- BlockServiceImpl.getByDistrictId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Block");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BlockServiceImpl.getByDistrictId() Service END -----> ");
		return response;
	}

}
