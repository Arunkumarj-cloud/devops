package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.cache.impl.ServiceCacheImpl;
import com.oasys.uppcl_user__master_management.dao.ServiceCategoryDao;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceMasterCacheDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ServiceCategoryDaoImpl implements ServiceCategoryDao {

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	ServiceCacheImpl serviceCacheImpl;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public BaseDTO create(ServiceCategoryDTO dto) {
		//log.info(" <----- ServiceCategoryDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			if (validateName(dto.getName()) == true) {
				
				ServiceCategoryEntity name = objectMapper.convertValue(dto, ServiceCategoryEntity.class);
				name = serviceCategoryRepository.save(name);
				serviceCacheImpl.put(name.getId().toString(),commonUtil.modalMap(name,ServiceMasterCacheDTO.class));
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(dto.getName() + "  " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage("This Service Category " + msg  );
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceCategoryDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			log.warn("Exception" + dto );
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			
		}
		//log.info(" <----- ServiceCategoryDaoImpl.create() END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ServiceCategoryDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<ServiceCategoryEntity> name = serviceCategoryRepository.findById(id);
			if (name.isPresent()) {
			
				response.setResponseContent(name);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg );
		}
		//log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
		return response;
	}

	
	@Override
	public BaseDTO update(UUID id, ServiceCategoryDTO dto) {
		//log.info(" <----- ServiceCategoryDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<ServiceCategoryEntity> optional = serviceCategoryRepository.findById(id);
			if (optional.isPresent()) {
				ServiceCategoryEntity name = new ServiceCategoryEntity();
				name = optional.get();
				List<ServiceCategoryEntity> checkName = serviceCategoryRepository.check(dto.getName().toUpperCase());
				for (ServiceCategoryEntity entity : checkName) {
					if (entity.getName().equalsIgnoreCase(dto.getName())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else	
						check = true;

					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage("This Service Category " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					
				} else {
					name.setName(dto.getName());
					name.setStatus(dto.getStatus());
					name.setRemarks(dto.getRemarks());
					ServiceCategoryEntity afterUpdate = serviceCategoryRepository.save(name);
					serviceCacheImpl.put(afterUpdate.getId().toString(), commonUtil.modalMap(afterUpdate,ServiceMasterCacheDTO.class));
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(dto.getName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceCategoryDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ServiceCategoryDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- ServiceCategoryDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceCategoryEntity> entity = serviceCategoryRepository.getAllActive();

			if (entity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg );
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(entity);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			
			}
		} catch (Exception e) {
			log.error("<----- ServiceCategoryDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- ServiceCategoryDaoImpl.getAllActive() END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- ServiceCategoryDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ServiceCategoryEntity entity = serviceCategoryRepository.getOne(id);
			if (entity != null) {
				serviceCategoryRepository.delete(entity);
				serviceCacheImpl.delete(id.toString());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg + " " + "to Delete ");
		}
		//log.info(" <-----  ServiceCategoryDaoImpl.delete() END -----> ");
		return response;
	}

	private ServiceCategoryEntity updatedValues(ServiceCategoryEntity entityMaster, ServiceCategoryDTO dto) {
		ServiceCategoryEntity entity = entityMaster;
		entity.setName(dto.getName());
		entity.setStatus(dto.getStatus());
		return entity;
	}

	private boolean validateName(String str1) {

		ServiceCategoryEntity entity = serviceCategoryRepository.findByNameIgnoreCase(str1);
		if (entity == null) {
			log.info("Valid ------>");
			return true;
		} else {
			log.warn("Invalid ------>");
			return false;
		}
	}

	@Override
	public BaseDTO getByName(String name) {
		// log.info(" <----- ServiceCategoryDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<ServiceCategoryEntity> getId = serviceCategoryRepository.findByName(name);
			if (getId.isPresent()) {

				response.setResponseContent(getId);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());

			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.getByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
		return response;
	}
	

	
	
	@Override
	public ServiceProviderEntity getChargesByName(String name) {
		// log.info(" <----- ServiceCategoryDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
		String serviceCategoryId = null;
		try {
			 serviceProviderEntity = serviceProviderRepository.getByName(name);
			
			if (serviceProviderEntity  == null) {				
				log.info("No Data Found");

			} 
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.getChargesByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
		return serviceProviderEntity;
	}
	
	
	
	@Override
	public ServiceProviderEntity getAadhaarChargesByName(String name,UUID uuid) {
		// log.info(" <----- ServiceCategoryDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
		String serviceCategoryId = null;
		try {
			 serviceProviderEntity = serviceProviderRepository.getByNameAndId(name.toUpperCase(), uuid);
			
			if (serviceProviderEntity  == null) {				
				log.info("No Data Found");

			} 
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.getChargesByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
		return serviceProviderEntity;
	}

	@Override
	public ServiceCategoryEntity getByNameIgnoreCase(String name) {
		// log.info(" <----- ServiceCategoryDaoImpl.getById() STARTED -----> ");
				BaseDTO response = new BaseDTO();
				ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
				String serviceCategoryId = null;
				try {
					serviceCategoryEntity = serviceCategoryRepository.findByNameIgnoreCase(name);
					
					if (serviceCategoryEntity  == null) {				
						log.info("No Data Found");

					} 
				} catch (Exception e) {
					log.error("<---- ServiceCategoryDaoImpl.getByNameIgnoreCase() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
					response.setMessage(msg);
				}
				// log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
				return serviceCategoryEntity;
	}

	@Override
	public ServiceCategoryEntity getMinMaxAmountByName(String name) {
		BaseDTO response = new BaseDTO();
		ServiceCategoryEntity serviceCategoryEntity = new ServiceCategoryEntity();
		try {
			serviceCategoryEntity = serviceCategoryRepository.findByNameIgnoreCase(name);
			if (serviceCategoryEntity == null) {
								
					log.info("No Data Found");

				}
		} catch (Exception e) {
			log.error("<---- ServiceCategoryDaoImpl.getByName() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		// log.info(" <----- ServiceCategoryDaoImpl.getById() END -----> ");
		return serviceCategoryEntity;
	}
	 public BaseDTO getAllOrderByModifiedDate() {
			
			BaseDTO response = new BaseDTO();
			try {
				List<ServiceCategoryEntity> entity = serviceCategoryRepository.getAllOrderByModifiedDate();

				if (entity.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
					response.setMessage(msg );
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					
				} else {
					response.setResponseContents(entity);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
			} catch (Exception e) {
				log.error("<----- ServiceCategoryDaoImpl.getAllOrderByModifiedDate() ----->", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			}
			
			return response;
		}


}
