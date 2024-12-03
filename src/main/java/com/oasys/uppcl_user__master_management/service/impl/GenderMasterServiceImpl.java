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
import com.oasys.uppcl_user__master_management.dao.GenderMasterDao;
import com.oasys.uppcl_user__master_management.dto.GenderMasterDTO;
import com.oasys.uppcl_user__master_management.entity.GenderMasterEntity;
import com.oasys.uppcl_user__master_management.repository.GenderMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.GenderMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GenderMasterServiceImpl implements GenderMasterService {
	@Autowired
	GenderMasterDao genderMasterDao;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	GenderMasterRepository genderMasterRepository;
	    
	      @Override
		public BaseDTO create(GenderMasterDTO genderMasterDTO){
			BaseDTO response = new BaseDTO();
			//log.info("<===Started GenderkMasterServiceImpl.create() ===>");
			try {
				response = genderMasterDao.create(genderMasterDTO);
				
			}catch(DataIntegrityViolationException e) {
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				log.error("Excetion in GenderMasterServiceImpl.create():" + e);
				
			}catch (Exception e) {
				
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				log.error("Excetion in GenderMasterServiceImpl.create():" + e);
			}
			//log.info("<=== Ended GenderMasterServiceImpl.create() ===>");
			return response;
		
	}
  
	      @Override
	  	public BaseDTO update(UUID id, GenderMasterDTO genderMasterDTO) {
	  		BaseDTO response = new BaseDTO();
	  		//log.info("<===Started GenderMasterServiceImpl.update() ===>");
	  		try {
	  			response = genderMasterDao.update(id, genderMasterDTO);
	  		}catch(DataIntegrityViolationException e) {
	  			log.error(" Exception GenderMasterServiceImpl.update()" + e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
	  			response.setMessage(msg);
	  		}catch (Exception e) {
	  			log.error(" Exception GenderMasterServiceImpl.update()" + e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
	  			response.setMessage(msg);
	  		}
	  		//log.info("<=== Ended GenderMasterServiceImpl.update() ===>");
	  		return response;	
		
}
	      @Override
	  	public BaseDTO getById(UUID id) {
	  		BaseDTO response = new BaseDTO();
	  		//log.info("<=== Started GenderMasterServiceImpl.getById()===>");
	  		try {
	  			response = genderMasterDao.getById(id);
	  		} catch (Exception e) {
	  			log.error(" Exception GenderMasterServiceImpl.getById() " + e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  		}
	  		//log.info("<=== Ended GenderMasterServiceImpl.getById() ===>");
	  		return response;
	}

	      @Override
	  	public BaseDTO getAllLazy(PaginationRequestDTO requestData) {
	  		//log.info(" <----- GenderMasterServiceImpl.getLazyList() Service STARTED -----> ");
	  		BaseDTO response = new BaseDTO();
	  		try {
	  			response = genderMasterDao.getLazyList(requestData);
	  		} catch (Exception e) {
	  			log.error("<---- GenderMasterServiceImpl.getLazyList()-{}", e.getMessage());
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
	  			response.setMessage(msg);
	  		}
	  		//log.info(" <----- GenderMasterServiceImpl.getLazyList() Service END -----> ");
	  		return response;
	  	}

	      
	      @Override
	  	public BaseDTO getAll() {
	  		BaseDTO response = new BaseDTO();
	  		//log.info("<===Started GenderMasterServiceImpl.getAll() ===>");
	  		try {
	  			response = genderMasterDao.getAll();
	  		} catch (Exception e) {
	  			log.error(" Exception GenderMasterServiceImpl.getAll()" + e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  		}
	  		//log.info("<=== Ended GenderMasterServiceImpl.getAll() ===>");
	  		return response;
}
	      
	      @Override
	  	public BaseDTO getAllActive() {
	  		BaseDTO response = new BaseDTO();
	  		//log.info("<===Started StatusMasterServiceImpl.getAllActive() ===>");
	  		try {
	  			response = genderMasterDao.getAllActive();
	  		} catch (Exception e) {
	  			log.error(" Exception GenderMasterServiceImpl.getAllActive()" + e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  		}
	  		//log.info("<=== Ended GenderMasterServiceImpl.getAllActive() ===>");
	  		return response;
	  	}
	  	
	  	@Override
	  	public BaseDTO softDelete(UUID id) {
	  		BaseDTO response = new BaseDTO();
	  		//log.info("<=== Started GenderMasterServiceImpl.softDelete()===>");
	  		try {
	  			GenderMasterEntity genderMasterEntity = genderMasterRepository.getOne(id);
	  			if (genderMasterEntity != null) {
	  				if (genderMasterEntity.getStatus() == true) {
	  					genderMasterEntity.setStatus(false);
	  					genderMasterEntity = genderMasterRepository.save(genderMasterEntity);
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
	  			log.error("<---- GenderMasterServiceImpl.softDelete() ----> EXCEPTION", e);
	  			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  			response.setMessage("Unable to Delete");
	  		}
	  		//log.info("<=== Ended GenderMasterServiceImpl.softDelete() ===>");
	  		return response;
	  	} 

}
