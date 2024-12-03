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
import com.oasys.uppcl_user__master_management.dao.CorporationDao;
import com.oasys.uppcl_user__master_management.dto.CorporationDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.CorporationService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CorporationServiceImpl implements CorporationService{

	@Autowired
	CorporationDao corporationDao;
	
	@Autowired
	 MessageSource messageSource;
	
	@Override
	public BaseDTO create(CorporationDTO corporationDTO) {
		//log.info(" <----- CorporationServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.create(corporationDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- CorporationServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Add Corporation");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <-----CorporationServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.getById(id);
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Corporation");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----CorporationServiceImpl.getById() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- CorporationServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.getAll();
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.getAll() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- CorporationServiceImpl.getAll()Active Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.getAll()Active Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- CorporationServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.getLazyList() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- CorporationServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.delete(id);
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.delete() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- CorporationServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Corporation");
		}
		//log.info(" <----- CorporationServiceImpl.delete() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id,CorporationDTO corporationDTO) {
		//log.info(" <----- CorporationServiceImpl.update()Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.update(id,corporationDTO);
		} catch(DataIntegrityViolationException e) {
			log.error("<---- CorporationServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		
		catch (Exception e) {
			log.error("<---- CorporationServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Update Corporation");
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.update()Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getbyDistrictId(UUID id) {
		//log.info(" <----- CorporationServiceImpl.getbyDistrictId() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = corporationDao.getbyDistrictId(id);
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getbyDistrictId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Corporation");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- CorporationServiceImpl.getbyDistrictId() Service END -----> ");
		return response;
	}
	
}
