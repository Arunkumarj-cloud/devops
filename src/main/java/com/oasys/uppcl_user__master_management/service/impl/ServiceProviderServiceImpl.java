package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.ServiceProviderDao;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ServiceProviderService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceProviderServiceImpl implements ServiceProviderService {


	@Autowired
	ServiceProviderDao serviceProviderDao;
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(ServiceProviderDTO dto) {
		//log.info(" <----- ServiceProviderServiceImpl.create() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.create(dto);
		} catch(DataIntegrityViolationException e) {
			
			log.error("<---- ServiceProviderServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Add ");
		}
		//log.info(" <----- ServiceProviderServiceImpl.create() Service END -----> ");
		return baseDTO;
	}
	
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ServiceProviderServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceProviderDao.getById(id);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- ServiceProviderServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <-----ServiceProviderServiceImpl.delete() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.delete(id);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----ServiceProviderServiceImpl.delete() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO update(UUID id,ServiceProviderDTO dto) {
		//log.info(" <-----ServiceProviderServiceImpl.update() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.update(id,dto);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- ServiceProviderServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----ServiceProviderServiceImpl.update() Service END -----> ");
		return baseDTO;
	}
	
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- ServiceProviderServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceProviderServiceImpl.getAllActive() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getByServiceCategoryId(UUID id) {
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.getByServiceCategoryId(id);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getByServiceCategoryId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service END -----> ");
		return baseDTO;
	}


	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		//log.info(" <----- ServiceProviderServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.getAllActiveWithSearch(searchDTO);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceProviderServiceImpl.getAllActive() Service END -----> ");
		return baseDTO;
	}


	@Override
	public BaseDTO getByName(String name) {
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.getByName(name);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getByName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByNameAndId(String name,UUID id) {
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = serviceProviderDao.getByNameAndId(name,id);
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getByNameAndId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceProviderServiceImpl.getByServiceCategoryId() Service END -----> ");
		return baseDTO;
	}
	
	
	public BaseDTO getAllOrderByModifiedDate() {	
		BaseDTO response = new BaseDTO();
		try {
			response = serviceProviderDao.getAllOrderByModifiedDate();
		} catch (Exception e) {
			log.error("<---- ServiceProviderServiceImpl.getAllOrderByModifiedDate() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);

		}
		
		return response;
	}
}
