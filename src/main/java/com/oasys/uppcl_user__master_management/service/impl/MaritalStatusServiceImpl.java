package com.oasys.uppcl_user__master_management.service.impl;


import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.MaritalStatusDao;
import com.oasys.uppcl_user__master_management.dto.MaritalStatusDTO;
import com.oasys.uppcl_user__master_management.entity.MaritalStatusMasterEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.MaritalStatusService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MaritalStatusServiceImpl implements MaritalStatusService  {
	
	
	@Autowired
	MaritalStatusDao  maritalStatusDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public BaseDTO createMaritalStatus(MaritalStatusDTO maritalStatusDTO) {
		//log.info("<===Started MaritalStatusServiceImpl.createMaritalStatus - {} ", maritalStatusDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			MaritalStatusMasterEntity maritalStatus = maritalStatusDao.findByNameIgnoreCase(maritalStatusDTO.getName());
			if (maritalStatus != null) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Marital Status Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				log.warn("Marital Status  -{} Already Exist");
				return response;
			}
			maritalStatus = objectMapper.convertValue(maritalStatusDTO, MaritalStatusMasterEntity.class);
			maritalStatus = maritalStatusDao.save(maritalStatus);
			if(maritalStatus == null) {
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage("Marital Status "+ message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				log.warn(maritalStatusDTO.getName()+" "+ message);
			}else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage("Marital Status "+ message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				log.warn(maritalStatusDTO.getName()+" "+ message);
			}		
		}catch(DataIntegrityViolationException e) {
			log.error("Exception MaritalStatusServiceImpl.createMaritalStatus : {} " , e);
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}catch(Exception e) {
			log.error("Exception MaritalStatusServiceImpl.createMaritalStatus : {} " , e);
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Ended MaritalStatusServiceImpl.createMaritalStatus ===>");
		return response;
	}
	
	public BaseDTO getMaritalStatus() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusServiceImpl.getMaritalStatus ===>");
		try {
			response = maritalStatusDao.getMaritalStatus();
			
		}catch(Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.getMaritalStatus " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended MaritalStatusServiceImpl.getMaritalStatus ===>");
		return response;
		
	}
	public BaseDTO getMaritalStatusById(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusServiceImpl.getMaritalStatusById - {} ",id);
		String message = "";
		try {
			Optional<MaritalStatusMasterEntity> maritalStatus = maritalStatusDao.getMaritalStatusById(id);
			if(maritalStatus.isPresent()) {
				//log.info("Maritalstatus Id {}", id);
				MaritalStatusMasterEntity maritalStatusMaster = maritalStatus.get();
				MaritalStatusDTO maritalStatusDTO = new MaritalStatusDTO();
				maritalStatusDTO.setId(maritalStatusMaster.getId());
				maritalStatusDTO.setName(maritalStatusMaster.getName());
				maritalStatusDTO.setRemarks(maritalStatusMaster.getRemarks());
				maritalStatusDTO.setStatus(maritalStatusMaster.getStatus());
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(message);
				response.setResponseContent(maritalStatusDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get MaritalStatus Details");
			}else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn(message,id);
			}			
		}catch(Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.getMaritalStatusById - {} " , e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		}
		///log.info("<===Ended MaritalStatusServiceImpl.getMaritalStatusById ===>");
		return response;	
	}
	
	public BaseDTO updateMaritalStatus(UUID id, MaritalStatusDTO maritalStatusDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MaritalStatusServiceImpl.updateMaritalStatus - {} and - {} ",id,maritalStatusDTO);
		String message = "";
		boolean validateMaritalStatus = false;
		try {
			Optional<MaritalStatusMasterEntity> maritalStatus = maritalStatusDao.getMaritalStatusById(id);
			if (!maritalStatus.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn(message + " - {} ", id);
				return response;
			}
			MaritalStatusMasterEntity maritalStatusMaster = maritalStatus.get();
			maritalStatusMaster.setName(maritalStatusDTO.getName());
			maritalStatusMaster.setStatus(maritalStatusDTO.getStatus());
			maritalStatusMaster.setRemarks(maritalStatusDTO.getRemarks());
			List<MaritalStatusMasterEntity> exceptMaritalStatus = maritalStatusDao.findByIdNotIn(id);
			for (MaritalStatusMasterEntity marital : exceptMaritalStatus) {
				if (marital.getId() != maritalStatusMaster.getId()) {
					if (marital.getName().equalsIgnoreCase(maritalStatusMaster.getName())) {
						validateMaritalStatus = true;
						break;
					} else
						validateMaritalStatus = false;
				} else
					validateMaritalStatus = false;
			}
			if (!validateMaritalStatus) {
				maritalStatusMaster = maritalStatusDao.save(maritalStatusMaster);
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage("Marital Status " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(maritalStatusDTO.getName() + " " + message);
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Marital Status Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Marital Status  - {} Already Exist");
			}
		} catch (DataIntegrityViolationException e) {
			log.error(" Exception MaritalStatusServiceImpl.updateMaritalStatus - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.updateMaritalStatus - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("<===Ended MaritalStatusServiceImpl.updateMaritalStatus ===>");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusServiceImpl.softDelete ===>");
		try {
			response = maritalStatusDao.softDelete(id);
			
		}catch(Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.deleteMarital " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===ended MaritalStatusServiceImpl.softDelete ===>");
		return response;
		
	}
	
	
	public BaseDTO getAllMaritalStatuslazy(PaginationRequestDTO pageData) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusServiceImpl.getAllMaritalStatuslazy ===>");
		try {
			response = maritalStatusDao.getAllMaritalStatuslazy(pageData);
			
		}catch(Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.getAllMaritalStatuslazy " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===ended MaritalStatusServiceImpl.getAllMaritalStatuslazy ===>");
		return response;
		
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started MaritalStatusServiceImpl.getAllActive()===>");
		try {
			response = maritalStatusDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception MaritalStatusServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended MaritalStatusServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO deleteMarital(UUID id) {
		//log.info(" <----- MaritalStatusServiceImpl.deleteMarital() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = maritalStatusDao.deleteMarital(id);
		} catch (Exception e) {
			log.error("<---- MaritalStatusServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Delete Marital Status");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- MaritalStatusServiceImpl.deleteMarital() Service END -----> ");
		return response;
	}

}
