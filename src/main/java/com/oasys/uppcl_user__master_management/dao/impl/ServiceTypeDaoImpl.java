package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ServiceTypeDao;
import com.oasys.uppcl_user__master_management.dto.ServiceDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceTypeDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceTypeRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ServiceTypeDaoImpl implements ServiceTypeDao {

	@Autowired
	ServiceTypeRepository serviceTypeRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public ServiceTypeMasterEntity save(ServiceTypeMasterEntity serviceType) {
		//log.info(" <----- ServiceType create Dao STARTED -----> ");
		ServiceTypeMasterEntity serviceTypeMasterEntity= serviceTypeRepository.save(serviceType);
		//log.info(" <----- ServiceType create Dao END -----> ");
		return serviceTypeMasterEntity;
	}
	
	public ServiceTypeMasterEntity findByServiceType(String serviceType) {
		//log.info(" <----- ServiceType findByServiceType Dao STARTED -----> ");
		ServiceTypeMasterEntity serviceTypeMasterEntity= serviceTypeRepository.findByServiceTypeIgnoreCase(serviceType);
		//log.info(" <----- ServiceType findByServiceType Dao END -----> ");
		return serviceTypeMasterEntity;
	}
	@Override
	public List<ServiceTypeMasterEntity> findByIdNotIn(UUID id) {
		//log.info(" <----- ServiceType findByIdNotIn Dao STARTED -----> ");
		List<ServiceTypeMasterEntity> serviceTypeMasterEntity= serviceTypeRepository.findByIdNotIn(id);
		//log.info(" <----- ServiceType findByServiceType Dao END -----> ");
		return serviceTypeMasterEntity;
	}


	public Optional<ServiceTypeMasterEntity> getById(UUID id) {
		//log.info(" <----- ServiceType getById Dao STARTED -----> ");
			Optional<ServiceTypeMasterEntity> serviceType = serviceTypeRepository.findById(id);
		return serviceType;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- ServiceType getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceTypeMasterEntity> ServiceType = serviceTypeRepository.findAll();

			if (ServiceType.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(ServiceType);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setMessage("SuccessfullSy Get All the ServiceType Details..");
				//log.info("SuccessfullSy Get All the ServiceType Details");
			}
		} catch (Exception e) {
			log.error("<----- ServiceTypeDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ServiceType getAll Dao END -----> ");
		return response;
	}

	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- ServiceType getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<ServiceTypeMasterEntity> contentList = new ArrayList<ServiceTypeMasterEntity>();
		Page<ServiceTypeMasterEntity> serviceTypeList = null;
		Pageable pageRequest;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					serviceTypeList = serviceTypeRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					serviceTypeList = serviceTypeRepository.search(pageRequest, requestData.getSearch());
				}
			} else {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					serviceTypeList = serviceTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					serviceTypeList = serviceTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}

			if (serviceTypeList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setNumberOfElements(serviceTypeList.getNumberOfElements());
				response.setTotalRecords(serviceTypeList.getTotalElements());
				response.setTotalPages(serviceTypeList.getTotalPages());
				for (ServiceTypeMasterEntity ServiceType : serviceTypeList) {
					contentList.add(ServiceType);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				//log.info("Successfully get Details");
			}
		} catch (Exception e) {
			log.error("<----- ServiceTypeDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ServiceType getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- ServiceType getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceTypeMasterEntity> serviceTypeList = serviceTypeRepository.getAllActive();
			if (serviceTypeList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {

				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(serviceTypeList);
				//log.info("SuccessfullSy Get All Active  ServiceType Details");
			}

		} catch (Exception e) {
			log.error("<----- ServiceTypeDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ServiceType getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- ServiceType delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ServiceTypeMasterEntity serviceType = serviceTypeRepository.getOne(id);
			if (serviceType != null) {
				serviceTypeRepository.delete(serviceType);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//	log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceTypeDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ServiceType delete Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- ServiceType softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ServiceTypeMasterEntity serviceType = serviceTypeRepository.getOne(id);
			if (serviceType != null) {
				serviceType.setStatus(false);
				serviceTypeRepository.save(serviceType);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceTypeServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete ServiceType");
		}
		//log.info(" <----- ServiceType softDelete Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO fetchServiceTypeWithServiceList() {
		BaseDTO response = new BaseDTO();
		//log.info(" <----- Favourite fetchServiceTypeWithServiceList Service END -----> ");
		try {

			List<ServiceTypeDTO> list = new ArrayList<ServiceTypeDTO>();
			List<ServiceTypeMasterEntity> serviceTypeList = serviceTypeRepository.getAllActive();
			for (ServiceTypeMasterEntity serviceTypeMasterEntity : serviceTypeList) {
				List<ServiceMasterEntity> serviceList = serviceRepository
						.findByServiceTypeId(serviceTypeMasterEntity.getId());

				ServiceTypeDTO obj = new ServiceTypeDTO();
				obj.setId(serviceTypeMasterEntity.getId());
				obj.setServiceType(serviceTypeMasterEntity.getServiceType());

				List<ServiceDTO> serviceList1 = new ArrayList<ServiceDTO>();

				for (ServiceMasterEntity ServiceMasterEntity : serviceList) {

					ServiceDTO entity = new ServiceDTO();
					entity.setId(ServiceMasterEntity.getId());
					entity.setServiceName(ServiceMasterEntity.getServiceName());
					entity.setBaseURL(ServiceMasterEntity.getBaseURL());

					serviceList1.add(entity);

				}
				obj.setServiceList(serviceList1);

				list.add(obj);
			}
			//log.info("<--- Successfully Fetched --->");
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
			response.setResponseContents(list);
		} catch (Exception e) {
			log.error("<--- Exception --->" + e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getErrorCode());
		}

		//log.info(" <----- Favourite fetchServiceTypeWithServiceList Service END -----> ");
		return response;

	}

	
	

}
