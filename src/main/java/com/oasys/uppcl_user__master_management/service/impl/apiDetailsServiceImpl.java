package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Locale;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ApiDetailsDao;
import com.oasys.uppcl_user__master_management.dao.impl.ApiDetailsDaoImpl;
import com.oasys.uppcl_user__master_management.dto.ApiDetailsDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ApiDetailsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class apiDetailsServiceImpl implements ApiDetailsService{

	
	private static final Logger logger = LogManager.getLogger(ApiDetailsDaoImpl.class);

	@Autowired
	private ApiDetailsDao api_details_dao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO save(ApiDetailsDTO dto) {
	
		BaseDTO response = api_details_dao.save(dto);
		
		return response;
	}

	public BaseDTO retrieve_all(PaginationRequestDTO dto) {
		//logger.info(" APIDetailsServiceImpl.retrieve_all() method is called.");
		BaseDTO response = api_details_dao.retrieve_all(dto);
		//logger.info(" Execution of APIDetailsServiceImpl.retrieve_all() method is finished.");
		return response;
	}
	
	public BaseDTO retrieve(UUID id) {
		//logger.info(" APIDetailsServiceImpl.retrieve(APIDetailsDTO dto) method is called.");
		BaseDTO response = api_details_dao.retrieve(id);
		//logger.info(" Execution of APIDetailsServiceImpl.retrieve(APIDetailsDTO dto) method is finished.");
		return response;
	}

	public BaseDTO update(UUID id, ApiDetailsDTO dto) {
		//logger.info(" APIDetailsServiceImpl.update(APIDetailsDTO dto) method is called.");
		BaseDTO response = api_details_dao.update(id, dto);
		//logger.info(" Execution of APIDetailsServiceImpl.update(APIDetailsDTO dto) method is finished.");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//logger.info(" <----- APIDetailsServiceImpl softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = api_details_dao.softDelete(id);
		} catch (Exception e) {
			logger.error("<---- APIDetailsServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Api Details Master");
		}
		//logger.info(" <----- APIDetailsServiceImpl softDelete Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {

		//logger.info(" <----- APIDetailsServiceImpl delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = api_details_dao.delete(id);
		} catch (Exception e) {
			logger.error("<---- APIDetailsServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" <----- APIDetailsServiceImpl delete Service END -----> ");
		return response;

	}

	@Override
	public BaseDTO getAllActive() {
		//logger.info(" <----- APIDetailsServiceImpl getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = api_details_dao.getAllActive();
		} catch (Exception e) {
			logger.error("<---- APIDetailsServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" <----- APIDetailsServiceImpl getAllActive Service END -----> ");
		return response;
	}

	
	
	 
	  @Override 
	  public BaseDTO getAll() {
	  //logger.info(" <----- APIDetailsServiceImpl getAll Service STARTED -----> ");
	   BaseDTO response = new BaseDTO(); 
	   try { 
		   response =api_details_dao.getAll();
} 
	   catch (Exception e) {
	  //logger.error("<---- APIDetailsServiceImpl.getAll() ----> EXCEPTION", e);
	  response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode()); String
	  msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE,null,Locale.US);
	   response.setMessage(msg); 
	   }
	  //logger.info(" <----- APIDetailsServiceImpl getAll Service END -----> ");
	  return response; 
	  }
	 


}
