package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.exception.RoutingEngineException;
import com.oasys.feign.client.RoutingEngineFeignClient;
import com.oasys.uppcl_user__master_management.cache.dto.ServiceProviderCacheDTO;
import com.oasys.uppcl_user__master_management.cache.impl.ServiceProviderCacheImpl;
import com.oasys.uppcl_user__master_management.dao.ServiceProviderDao;
import com.oasys.uppcl_user__master_management.dto.RoutingEngineResponseDTO;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceProviderDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceProviderEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceProviderRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ServiceproviderDaoImpl implements ServiceProviderDao {

	@Autowired
	ServiceProviderRepository serviceProviderRepository;

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	ServiceProviderDao serviceProviderDao;


	@Autowired
	MessageSource messageSource;

	@Autowired
	ServiceProviderCacheImpl serviceProviderCacheImpl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RoutingEngineFeignClient routingEngineFeignClient;
	
	@Override
	public BaseDTO create(ServiceProviderDTO dto) {
		//log.info(" <-----ServiceproviderDaoImpl.create() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			if (null != dto) {

				ServiceProviderEntity ServiceProviderEntity = new ServiceProviderEntity();
	
									Optional<ServiceProviderEntity> optional = serviceProviderRepository.check(
											dto.getServiceProviderName().toUpperCase(),
											dto.getServiceCategoryId().getId());
									if (optional.isPresent()) {
										String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,
												Locale.US);
										baseDTO.setMessage("This Service Provider " + msg);
										baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
									}

									else {
										ServiceProviderEntity.setServiceProviderName(dto.getServiceProviderName());
										ServiceProviderEntity.setStatus(dto.getStatus());
										ServiceProviderEntity.setServiceCategoryId(dto.getServiceCategoryId());
										ServiceProviderEntity.setRemarks(dto.getRemarks());
										ServiceProviderEntity=serviceProviderRepository.save(ServiceProviderEntity);
										serviceProviderCacheImpl.put(ServiceProviderEntity.getId().toString(),commonUtil.modalMap(ServiceProviderEntity, ServiceProviderCacheDTO.class));
										String msg = messageSource.getMessage(
												ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
										baseDTO.setMessage(dto.getServiceProviderName() + " " + msg);
										baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
									}
							
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----ServiceproviderDaoImpl.create() Dao END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- ServiceproviderDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<ServiceProviderEntity> entity = serviceProviderRepository.findById(id);
			if (entity.isPresent()) {
				response.setResponseContent(entity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg );
		}
		//log.info(" <----- ServiceproviderDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----ServiceproviderDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<ServiceProviderEntity> entity = serviceProviderRepository.getAllActive();

			if (entity.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(entity);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
			}

		} catch (Exception e) {
			log.error("<----- ServiceproviderDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----ServiceproviderDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- ServiceproviderDaoImpl.delete() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			ServiceProviderEntity entity = serviceProviderRepository.getOne(id);
			if (entity.getStatus() == true) {
//				serviceProviderRepository.delete(entity);
				entity.setStatus(false);
				serviceProviderRepository.save(entity);
				serviceProviderCacheImpl.delete(entity.getId().toString());
				
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				baseDTO.setMessage("Record has been deleted already..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}

		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceproviderDaoImpl.delete() Dao END -----> ");
		return baseDTO;
	}
	
	
	public RoutingEngineResponseDTO routingEngineCall(UUID providerId,UUID serviceId, ServiceProviderDTO dto) {
		// TODO Auto-generated method stub
		RoutingEngineResponseDTO responseDTO = new RoutingEngineResponseDTO();
		String serviceCategoryId = null;
		String subCategoryId = null;
		String serviceProviderId = null;
		try {
			BaseDTO object = routingEngineFeignClient.updateByProviderId(providerId,serviceId,dto);
			
			log.info("Routing engine response === > "+object);
			
			/*
			 * final JsonNode arrNode = new
			 * ObjectMapper().readTree(object.getBody().toString()) .get("responseContent");
			 */
			if(Objects.nonNull(object) && object.getStatusCode() == 200)
			{
				responseDTO.setStatus("S");	
			}else if(Objects.nonNull(object) && object.getStatusCode() == 204)
			{
				responseDTO.setStatus("S");	
			}
			else
			{
				

			
				throw new RoutingEngineException("Technical Error in Routing Engine.Please try after sometime.");
			}
		}catch (Exception e) {
			log.info("Exception in Routing Engine call -- {} ",e);
			throw new RoutingEngineException(e);
			
			// TODO: handle exception
		}
		return responseDTO;
	}

	private ServiceProviderEntity updatedValues(ServiceProviderEntity servicemaster,
			ServiceProviderDTO dto) {
		ServiceProviderEntity entity = servicemaster;
		entity.setServiceCategoryId(dto.getServiceCategoryId());
		entity.setServiceProviderName(dto.getServiceProviderName());
		entity.setStatus(dto.getStatus());
	
		return entity;
	}

	@Override
	@Transactional
	public BaseDTO update(UUID id, ServiceProviderDTO dto) {
		//log.info(" <----- ServiceproviderDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		RoutingEngineResponseDTO responseDTO = new RoutingEngineResponseDTO();
		
		try {
			boolean check = false;
			Optional<ServiceProviderEntity> optional = serviceProviderRepository.findById(id);
			if (optional.isPresent()) {
				ServiceProviderEntity entity1 = new ServiceProviderEntity();
				entity1 = optional.get();
				/*
				 * List<ServiceProviderEntity> checkCode = serviceProviderRepository
				 * .checkCode(dto.getServiceProviderName().toUpperCase(),id,dto.
				 * getServiceCategoryId()); for (ServiceProviderEntity entity : checkCode) { if
				 * (entity.getServiceProviderName().equalsIgnoreCase(dto.getServiceProviderName(
				 * ))) { if (id.equals(entity.getId())) { check = false; } else check = true; }
				 * else { check = false; } }
				 */
				/*if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Service Provider " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());						
				} else {*/
					//entity1.setServiceProviderName(dto.getServiceProviderName());					
					//entity1.setServiceCategoryId(dto.getServiceCategoryId());
					entity1.setStatus(dto.getStatus());
					entity1.setRemarks(dto.getRemarks());
					 //if(dto.getStatus() == false) { 
				    responseDTO = routingEngineCall(id,entity1.getServiceCategoryId().getId(),dto); 
					//}
					ServiceProviderEntity afterUpdate = serviceProviderRepository.save(entity1);
					serviceProviderCacheImpl.put(afterUpdate.getId().toString(),commonUtil.modalMap(afterUpdate, ServiceProviderCacheDTO.class));
					
					 
					 
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(entity1.getServiceProviderName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					
				//}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ServiceproviderDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ServiceproviderDaoImpl.update() END -----> ");
		return response;
	}

	
	@Override
	public BaseDTO getByServiceCategoryId(UUID id) {
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {

			List<ServiceProviderEntity> name = serviceProviderRepository.findByCategoryId(id);

			if (!name.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(name);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		// log.info(" <-----ServiceproviderDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			List<ServiceProviderEntity> entity = null;

			if (searchDTO.getSearch() == null) {
				entity = serviceProviderRepository.getAllActive();
			} else {
				entity = serviceProviderRepository.getAllActiveWithSearch(searchDTO.getSearch());
			}
			if (entity.isEmpty()) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				// log.info("No Record Found..");
			} else {
				baseDTO.setResponseContents(entity);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE,
						null, Locale.US);
				baseDTO.setMessage(msg);
			}

		} catch (Exception e) {
			log.error("<----- ServiceproviderDaoImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----ServiceproviderDaoImpl.getAllActive() Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByName(String name) {
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {

			List<ServiceProviderEntity> list= serviceProviderRepository.findByServiceProviderNameIgnoreCase(name);

			if (!list.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContents(list);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.getByName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getByNameAndId(String name,UUID id) {
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {

			ServiceProviderEntity data= serviceProviderRepository.findByServiceProviderNameAndServiceId(name.toUpperCase(),id);

			if (data != null) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setResponseContent(data);
				//log.info("Successfully Fetched");
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ServiceproviderDaoImpl.getByNameAndId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- ServiceproviderDaoImpl.getById() Dao END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getAllOrderByModifiedDate() {
		BaseDTO response = new BaseDTO();
		try {
			List<ServiceProviderEntity> entity = serviceProviderRepository.getAllOrderByModifiedDate();

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
			log.error("<----- ServiceproviderDaoImpl.getAllOrderByModifiedDate() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		
		return response;
	}
}
