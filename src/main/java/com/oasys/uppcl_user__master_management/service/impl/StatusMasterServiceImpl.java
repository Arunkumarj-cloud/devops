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
import com.oasys.uppcl_user__master_management.dao.StatusMasterDao;
import com.oasys.uppcl_user__master_management.dto.StatusMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StatusMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StatusMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.StatusMasterService;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class StatusMasterServiceImpl implements StatusMasterService{
	
	@Autowired
	StatusMasterDao statusMasterDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Override
	public BaseDTO create(StatusMasterDTO statusMasterDTO) {
		//log.info(" <----- StatusMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = statusMasterDao.create(statusMasterDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- StatusMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}	catch (Exception e) {
			log.error("<---- StatusMasterServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusMasterServiceImpl.create() Service END -----> ");
		return response;
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- StatusMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = statusMasterDao.getById(id);
		} catch (Exception e) {
			log.error("<---- StatusMasterServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- StatusMasterServiceImpl.getById() Service END -----> ");
		return response;
	}
	@Override
	public BaseDTO getAll() {
		//log.info(" <-----StatusMasterServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = statusMasterDao.getAll();
		} catch (Exception e) {
			log.error("<---- StatusMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <-----StatusMasterServiceImpl.getAll() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- StatusMasterServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = statusMasterDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- StatusMasterServiceImpl.getLazyList()-{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusMasterServiceImpl.getLazyList() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, StatusMasterDTO statusMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started StatusMasterServiceImpl.update() ===>");
		try {
			response = statusMasterDao.update(id, statusMasterDTO);
			
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error(" Exception StatusMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended StatusMasterServiceImpl.update() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started StatusMasterServiceImpl.getAllActive() ===>");
		try {
			response = statusMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception StatusMasterServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended StatusMasterServiceImpl.getAllActive() ===>");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started StatusMasterServiceImpl.softDelete()===>");
		try {
			StatusMasterEntity statusMasterEntity = statusMasterRepository.getOne(id);
			if (statusMasterEntity != null) {
				if (statusMasterEntity.getStatus() == true) {
					statusMasterEntity.setStatus(false);
					statusMasterEntity = statusMasterRepository.save(statusMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete ");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- StatusMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete");
		}
		//log.info("<=== Ended StatusMasterServiceImpl.softDelete() ===>");
		return response;
	}


}
