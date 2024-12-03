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
import com.oasys.uppcl_user__master_management.dao.UserTypeDao;
import com.oasys.uppcl_user__master_management.dto.UserTypeDTO;
import com.oasys.uppcl_user__master_management.entity.UserTypeMasterEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.UserTypeService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserTypeServiceImpl implements UserTypeService {
	
	@Autowired
	UserTypeDao userTypeDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(UserTypeDTO userTypeDTO) {
		//log.info(" <----- UserType create Service STARTED -----> {} ",userTypeDTO);
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.create(userTypeDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType create Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id,UserTypeDTO userTypeDTO) {
		//log.info(" <----- UserType update Service STARTED -----> {} and {} ",id,userTypeDTO);
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.update(id,userTypeDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		
		catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType update Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- UserType getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.getById(id);
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.getById() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get UserType");
		}
		//log.info(" <----- UserType getById Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- UserType getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.getAll();
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getAll Service END -----> ");
		return response;
	}
	

	public BaseDTO getAllActive() {
		//log.info(" <----- UserType getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getAllActive Service END -----> ");
		return response;
	}
	
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- UserType getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- UserType delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.delete(id);
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserType delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- UserType softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete UserType");
		}
		//log.info(" <----- UserType softDelete Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByName(String userTypeName) {
		//log.info(" <----- UserType getById Service STARTED -----> {}",userTypeName);
		BaseDTO response = new BaseDTO();
		try {
			UserTypeMasterEntity userType = userTypeDao.getByName(userTypeName);
			if (userType != null) {
				response.setResponseContent(userType);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get UserType");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found - {}",userTypeName);
			}	 
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.getById() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get UserType");
		}
		//log.info(" <----- UserType getById Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO validateUserType(String userTypeName) {
		//log.info(" <----- UserType validateUserType Service STARTED -----> {}", userTypeName);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			UserTypeMasterEntity userType = userTypeDao.getByName(userTypeName);
			if (userType == null) {
				message = "Valid User Type";		
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(message + " - {}", userTypeName);
			} else {
				message = "Invalid User Type";
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("User Type Already Exists");
			}
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- UserTypeServiceImpl.validateUserType() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Validate UserType");
		}
		//log.info(" <----- UserType validateUserType Service END -----> ");
		return response;
	}
}
