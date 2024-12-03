package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.CityMasterDao;
import com.oasys.uppcl_user__master_management.dto.CityMasterDTO;
import com.oasys.uppcl_user__master_management.entity.CityMasterEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.CityMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CityMasterServiceImpl implements CityMasterService {

	@Autowired
	CityMasterDao cityMasterDao;
	
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(CityMasterDTO cityMasterDTO) {
		
		//log.info(" <----- CityMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		
		try {
			baseDTO =cityMasterDao.create(cityMasterDTO);
		} 
		
		catch(DataIntegrityViolationException e) {
			
			log.error("<---- CityMasterServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		
		catch (Exception e) {
			log.error("<---- CityMasterServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Add City");
		}
		//log.info(" <----- CityMasterServiceImpl.create() Service END -----> ");
		return baseDTO;
		
		
		
	}
	
	
	@Override
	public BaseDTO getAll() {
		
		BaseDTO baseDTO = new BaseDTO();
		List<CityMasterEntity> cityMasterEntity = null;
		try {
			cityMasterEntity  = cityMasterDao.getAll();
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
			baseDTO.setResponseContents(cityMasterEntity);
			baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getAll() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- District getAll Service END -----> ");
		return baseDTO;
	}
	
	  @Override
	 public BaseDTO update( UUID id,CityMasterDTO cityMasterDTO) {
		  
			//log.info(" <-----CityMasterServiceImpl.update() Service STARTED -----> ");
			BaseDTO baseDTO = new BaseDTO(); 
			try {
				baseDTO = cityMasterDao.update(id,cityMasterDTO);
			}catch(DataIntegrityViolationException e) {
				log.error("<----CityMasterServiceImpl.update() ----> EXCEPTION", e);
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
			}catch (Exception e) {
				log.error("<---- CityMasterServiceImpl.update() ----> EXCEPTION", e);
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
			}
			//log.info(" <-----CityMasterServiceImpl.update() Service END -----> ");
			return baseDTO;
		  
	  }

}
