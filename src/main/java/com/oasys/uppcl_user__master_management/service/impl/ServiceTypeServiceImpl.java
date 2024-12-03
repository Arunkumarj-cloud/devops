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
import com.oasys.uppcl_user__master_management.dao.ServiceTypeDao;
import com.oasys.uppcl_user__master_management.dto.ServiceTypeDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceTypeMasterEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ServiceTypeService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceTypeServiceImpl implements ServiceTypeService{
	
	@Autowired
	ServiceTypeDao serviceTypeDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public BaseDTO create(ServiceTypeDTO serviceTypeDTO) {
		//log.info(" <----- ServiceType create Service STARTED -----> {} ", serviceTypeDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			ServiceTypeMasterEntity serviceType = serviceTypeDao.findByServiceType(serviceTypeDTO.getServiceType());
			if (serviceType != null) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Service Type " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("Service Type is Already Exists..{} ",serviceType);
				return response;
			}
			serviceType = objectMapper.convertValue(serviceTypeDTO, ServiceTypeMasterEntity.class);
			serviceType = serviceTypeDao.save(serviceType);
			if (serviceType == null) {
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(serviceTypeDTO.getServiceType() + " " + message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				//log.info(serviceTypeDTO.getServiceType() + " " + message);
			} else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(serviceTypeDTO.getServiceType() + " " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(serviceTypeDTO.getServiceType() + " " + message);
			}
		}catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceTypeServiceImpl.create() ----> EXCEPTION - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.create() ----> EXCEPTION - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- ServiceType create Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id, ServiceTypeDTO serviceTypeDTO) {
		//log.info(" <----- ServiceType updateServiceType Dao STARTED -----> {} - {} ", id, serviceTypeDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean validateServiceType = false;
			Optional<ServiceTypeMasterEntity> serviceType = serviceTypeDao.getById(id);
			if (serviceType.isPresent()) {
				//log.info("Service type {} ", serviceType);
				ServiceTypeMasterEntity updateServiceType = updatedValues(serviceType.get(), serviceTypeDTO);
				List<ServiceTypeMasterEntity> exceptServiceType = serviceTypeDao.findByIdNotIn(id);
				for (ServiceTypeMasterEntity serviceTypeMasterEntity : exceptServiceType) {
					if (serviceTypeMasterEntity.getId() != updateServiceType.getId()) {
						if (serviceTypeMasterEntity.getServiceType().equalsIgnoreCase(updateServiceType.getServiceType())) {
							validateServiceType = true;
							break;
						} else {
							validateServiceType = false;
						}
					} else {
						validateServiceType = false;
					}
				}
				if (!validateServiceType) {
					updateServiceType = serviceTypeDao.save(updateServiceType);
					response.setResponseContent(updateServiceType);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(serviceTypeDTO.getServiceType() + " " + message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated");
				} else {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Service Type " + message);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error(serviceTypeDTO.getServiceType() + " " + message);
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error(message);
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceTypeServiceImpl.update() ----> EXCEPTION - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.update() ----> EXCEPTION - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- ServiceType update Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ServiceType getById Service STARTED -----> {} ", id);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			Optional<ServiceTypeMasterEntity> serviceType = serviceTypeDao.getById(id);
			if (serviceType.isPresent()) {
				//log.info("Service type Id {} " ,id);
				response.setResponseContent(serviceType);
				 message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get ServiceType Details.. {} ",serviceType);
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found.. {} ",id);
			}
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.getById() ----> EXCEPTION - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( message);
		}
		//log.info(" <----- ServiceType getById Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- ServiceType getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.getAll();
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- ServiceType getAll Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- ServiceType getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- ServiceType getAllActive Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ServiceType getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- ServiceType getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- ServiceType delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.delete(id);
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- ServiceType delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- ServiceType delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete ServiceType");
		}
		///log.info(" <----- ServiceType delete Service END -----> ");
		return response;
	}


	@Override
	public BaseDTO fetchServiceTypeWithServiceList() {
		//log.info(" <----- ServiceType fetchServiceTypeWithServiceList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceTypeDao.fetchServiceTypeWithServiceList();
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.fetchServiceTypeWithServiceList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Fetch List");
		}
		//log.info(" <----- ServiceType fetchServiceTypeWithServiceList Service END -----> ");
		return response;
	}
	
	private ServiceTypeMasterEntity updatedValues(ServiceTypeMasterEntity serviceType,ServiceTypeDTO serviceTypeDTO) {
		ServiceTypeMasterEntity serviceTypeMaster = serviceType;
		serviceTypeMaster.setServiceType(serviceTypeDTO.getServiceType());
		serviceTypeMaster.setStatus(serviceTypeDTO.getStatus());
		return serviceTypeMaster;
	}

}
