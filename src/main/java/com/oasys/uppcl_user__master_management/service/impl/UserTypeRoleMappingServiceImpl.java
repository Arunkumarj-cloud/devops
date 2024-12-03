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
import com.oasys.uppcl_user__master_management.dao.UserTypeRoleMappingDao;
import com.oasys.uppcl_user__master_management.dto.UserTypeRoleMappingDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.UserTypeRoleMappingService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserTypeRoleMappingServiceImpl implements UserTypeRoleMappingService{
	
	@Autowired
	UserTypeRoleMappingDao userTypeRoleMappingDao;
	
	@Autowired
	MessageSource messageSource;
	@Override
	public BaseDTO create(UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info(" <----- UserTypeRoleMapping create Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.create(userTypeRoleMappingDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping create Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id,UserTypeRoleMappingDTO userTypeRoleMappingDTO) {
		//log.info(" <----- UserTypeRoleMapping update Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.update(id,userTypeRoleMappingDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping update Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- UserTypeRoleMapping getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.getById(id);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get UserTypeRoleMapping");
		}
		//log.info(" <----- UserTypeRoleMapping getById Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- UserTypeRoleMapping getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.getAll();
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getAll Service END -----> ");
		return response;
	}
	

	public BaseDTO getAllActive() {
		//log.info(" <----- UserTypeRoleMapping getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getAllActive Service END -----> ");
		return response;
	}
	
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- UserTypeRoleMapping getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- UserTypeRoleMapping delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.delete(id);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- UserTypeRoleMapping delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- UserTypeRoleMapping softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete UserTypeRoleMapping");
		}
		//log.info(" <----- UserTypeRoleMapping softDelete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getByUserTypeId(UUID id) {
		//log.info(" <----- UserTypeRoleMapping getByUserTypeId Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = userTypeRoleMappingDao.getByUserTypeId(id);
		} catch (Exception e) {
			log.error("<---- UserTypeRoleMappingServiceImpl.getByUserTypeId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get Role");
		}
		//log.info(" <----- UserTypeRoleMapping getByUserTypeId Service END -----> ");
		return response;
	}

}
