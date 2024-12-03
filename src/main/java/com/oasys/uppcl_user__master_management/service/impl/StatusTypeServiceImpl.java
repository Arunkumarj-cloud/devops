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
import com.oasys.uppcl_user__master_management.dao.StatusTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.StatusTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StatusTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StatusTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.StatusTypeMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StatusTypeServiceImpl implements StatusTypeMasterService{
	
	@Autowired
	StatusTypeMasterDao statusTypeMasterDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	StatusTypeMasterRepository statusTypeMasterRepository;
	
	@Override
	public BaseDTO create(StatusTypeMasterDTO statusTypeMasterDTO) {
		//log.info(" <----- StatusTypeServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = statusTypeMasterDao.create(statusTypeMasterDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- StatusTypeServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}	catch (Exception e) {
			log.error("<---- StatusTypeServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusTypeServiceImpl.create() Service END -----> ");
		return response;
}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- StatusTypeServiceImpl.getById() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = statusTypeMasterDao.getById(id);
				} catch (Exception e) {
					log.error("<---- StatusTypeServiceImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
				//log.info(" <----- StatusTypeServiceImpl.getById() Service END -----> ");
				return response;
			}


	@Override
	public BaseDTO getAll() {
		//log.info(" <-----StatusTypeServiceImpl.getAll() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = statusTypeMasterDao.getAll();
				} catch (Exception e) {
					log.error("<---- StatusTypeServiceImpl.getAll() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
				//log.info(" <-----StatusTypeServiceImpl.getAll() Service END -----> ");
				return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- StatusTypeServiceImpl.getLazyList() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = statusTypeMasterDao.getLazyList(requestData);
				} catch (Exception e) {
					log.error("<---- StatusTypeServiceImpl.getLazyList()-{}", e.getMessage());
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- StatusTypeServiceImpl.getLazyList() Service END -----> ");
				return response;
			}

	

	public BaseDTO update(UUID id, StatusTypeMasterDTO statusTypeMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started StatusTypeServiceImpl.update() ===>");
		try {
			response = statusTypeMasterDao.update(id, statusTypeMasterDTO);
			
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error(" Exception StatusTypeServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended StatusTypeServiceImpl.update() ===>");
		return response;
	}
	
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started StatusTypeServiceImpl.getAllActive() ===>");
		try {
			response = statusTypeMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception StatusTypeServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended StatusTypeServiceImpl.getAllActive() ===>");
		return response;
	}
	
		
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started StatusTypeServiceImpl.softDelete()===>");
		try {
			StatusTypeMasterEntity statusTypeMasterEntity = statusTypeMasterRepository.getOne(id);
			if (statusTypeMasterEntity != null) {
				if (statusTypeMasterEntity.getStatus() == true) {
					statusTypeMasterEntity.setStatus(false);
					statusTypeMasterEntity = statusTypeMasterRepository.save(statusTypeMasterEntity);
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
			log.error("<---- StatusTypeServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete");
		}
		//log.info("<=== Ended StatusTypeServiceImpl.softDelete() ===>");
		return response;
	}
}
