package com.oasys.uppcl_user__master_management.service.impl;



import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ServiceDao;
import com.oasys.uppcl_user__master_management.dao.ServiceTypeDao;
import com.oasys.uppcl_user__master_management.dto.ListDto;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SearchFiledsDto;
import com.oasys.uppcl_user__master_management.dto.ServiceAndProviderResponseDto;
import com.oasys.uppcl_user__master_management.dto.ServiceDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceDeatilsDto;
import com.oasys.uppcl_user__master_management.entity.ServiceMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceSlotEntity;
import com.oasys.uppcl_user__master_management.entity.SubCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceSlotRepository;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.ServiceService;

import lombok.extern.log4j.Log4j2;







@Service
@Log4j2
public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	ServiceDao serviceDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ServiceTypeDao serviceTypeDao;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	ServiceSlotRepository serviceSlotRepository;
	
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	@Override
	public BaseDTO create(ServiceDTO serviceDTO) {
		//log.info(" <----- Service create Service STARTED -----> {} ",serviceDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			if (serviceTypeDao.getById(serviceDTO.getServiceTypeId().getId()).isPresent()) {
				response = serviceDao.create(serviceDTO);
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage("Service Type is not found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("Service Type {} - {} ", message, serviceDTO.getServiceTypeId().getId());
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Service create Service END -----> ");
		return response;
	}
	
	public BaseDTO update(UUID id, ServiceDTO serviceDTO) {
		//log.info(" <----- Service update Service STARTED -----> {} and {} ",id,serviceDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			if (serviceTypeDao.getById(serviceDTO.getServiceTypeId().getId()).isPresent()) {
				response = serviceDao.update(id, serviceDTO);
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage("Service Type is not found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("Service Type {} -{} ", message,serviceDTO.getServiceTypeId().getId());
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Service update Service END -----> ");
		return response;
	}
	

	public BaseDTO getById(UUID id) {
		//log.info(" <----- Service getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getById(id);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Service");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getById Service END -----> ");
		return response;
	}
	
	
	public BaseDTO getAll() {
		//log.info(" <----- Service getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getAll();
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getAll Service END -----> ");
		return response;
	}
	
	
	public BaseDTO getAllActive() {
		//log.info(" <----- Service getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getAllActive Service END -----> ");
		return response;
	}
	
	
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Service getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Service delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.delete(id);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- Service delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Service");
		}
		//log.info(" <----- Service delete Service END -----> ");
		return response;
	}


	@Override
	public BaseDTO getList(ListDto ids) {
		//log.info(" <----- Service getList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getList(ids);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getList Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getAllActiveWithSearch(searchDTO);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getAllActive Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getServiceAndProviderList(List<ServiceDeatilsDto> serviceDeatilsDtos) {
		
		BaseDTO baseDTO=new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		ServiceAndProviderResponseDto serviceAndProviderResponseDto=new ServiceAndProviderResponseDto();
		
		List<ServiceMasterEntity> serviceMasterEntities= serviceRepository.findAllById(serviceDeatilsDtos.stream().map(ServiceDeatilsDto::getServiceId).collect(Collectors.toSet()));
		if(serviceMasterEntities!=null&&!serviceMasterEntities.isEmpty())
		{
			serviceAndProviderResponseDto.setService(serviceMasterEntities.stream().collect(
	                Collectors.groupingBy(ServiceMasterEntity::getId)));
		}
		List<ServiceProviderEntity> serviceProviderEntities=serviceProviderRepository.findAllById(serviceDeatilsDtos.stream().map(ServiceDeatilsDto::getServiceProviderId).collect(Collectors.toSet()));
		if(serviceProviderEntities!=null&&!serviceProviderEntities.isEmpty())
		{
			serviceAndProviderResponseDto.setServiceProvider(serviceProviderEntities.stream().collect(
	                Collectors.groupingBy(ServiceProviderEntity::getId)));
		}
		
		List<ServiceSlotEntity> serviceSlotEntities=serviceSlotRepository.findAllById(serviceDeatilsDtos.stream().map(ServiceDeatilsDto::getSlabId).collect(Collectors.toSet()));
		if(serviceSlotEntities!=null&&!serviceSlotEntities.isEmpty())
		{
			serviceAndProviderResponseDto.setSlabs(serviceSlotEntities.stream().collect(
	                Collectors.groupingBy(ServiceSlotEntity::getId)));
		}
		
		List<SubCategoryEntity> subCategoryEntities=subCategoryRepository.findAllById(serviceDeatilsDtos.stream().map(ServiceDeatilsDto::getSubCategoryId).collect(Collectors.toSet()));
		if(subCategoryEntities!=null&&!subCategoryEntities.isEmpty())
		{
			serviceAndProviderResponseDto.setSubCategory(subCategoryEntities.stream().collect(
	                Collectors.groupingBy(SubCategoryEntity::getId)));
		}
		
		baseDTO.setResponseContent(serviceAndProviderResponseDto);
		return baseDTO;
	
	}

	@Override
	public BaseDTO createSearchFields(SearchFiledsDto searchFiledsDto) {
		//log.info(" <----- Service create Service STARTED -----> {} ",serviceDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			response = serviceDao.createSearchFields(searchFiledsDto);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- Service create Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getSearchFields(UUID id) {
		//log.info(" <----- Service getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getSearchFields(id);
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getSearchFields() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Service");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getById Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getServiceList() {
		//log.info(" <----- Service getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = serviceDao.getServiceList();
		} catch (Exception e) {
			log.error("<---- ServiceServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		//log.info(" <----- Service getAllActive Service END -----> ");
		return response;
	}

}
