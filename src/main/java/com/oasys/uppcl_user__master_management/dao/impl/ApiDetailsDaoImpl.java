package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.oasys.uppcl_user__master_management.dao.ApiDetailsDao;
import com.oasys.uppcl_user__master_management.dto.ApiDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.ApiMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ApiDetailsRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ApiDetailsDaoImpl implements ApiDetailsDao {
	
	private static final Logger logger = LogManager.getLogger(ApiDetailsDaoImpl.class);

	@Autowired
	private ApiDetailsRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO save(ApiDetailsDTO dto) {

		BaseDTO response = new BaseDTO();
		//logger.info(" APIDetailsDaoImpl.save(APIDetailsDTO dto) method is called.");
		try {
			ApiMasterEntity api_details_entity = objectMapper.convertValue(dto, ApiMasterEntity.class);
			
			api_details_entity.setStatus(dto.getStatus());
			
			ApiMasterEntity returned_entity = repository.save(api_details_entity);
			if (null != returned_entity.getId()) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				response.setResponseContent(returned_entity);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			
				response.setMessage(msg);
				//logger.info("API Details Saved with ID = " + returned_entity.getId());
			} else {
				//logger.info("Save Operation failed for API Details on " + LocalDate.now() + " at " + LocalTime.now());
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			logger.error("  Exception  Occured in  APIDetailsDaoImpl.save(APIDetailsDTO dto)");
			logger.error("  Exception Name  : " + e.getLocalizedMessage());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" Execution of APIDetailsDaoImpl.save(APIDetailsDTO dto) method is finished.");

		return response;
	}
	
	public BaseDTO retrieve_all(PaginationRequestDTO requestData) {
		BaseDTO response = new BaseDTO();
		//logger.info(" APIDetailsDaoImpl.retrieve_all() method is called.");
		List<ApiMasterEntity> contentList = new ArrayList<ApiMasterEntity>();
		Page<ApiMasterEntity> apiMasterEntity = null;
		Pageable pageRequest;
		try {
			if (requestData.getSearch() != null && requestData.getSearch() != "") {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					apiMasterEntity = repository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					apiMasterEntity = repository.search(pageRequest, requestData.getSearch());
				}
			} else {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					apiMasterEntity = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					apiMasterEntity = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}

			if (apiMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//logger.info("No Record Found..");
			} else {
				response.setNumberOfElements(apiMasterEntity.getNumberOfElements());
				response.setTotalRecords(apiMasterEntity.getTotalElements());
				response.setTotalPages(apiMasterEntity.getTotalPages());
				for (ApiMasterEntity apiMaster : apiMasterEntity) {
					contentList.add(apiMaster);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				//logger.info("Successfully Get all Lazy list Details");
			}

		} catch (Exception e) {
			logger.error("  Exception  Occured in APIDetailsDaoImpl.retrieve_all()   : ");
			logger.error("  Exception Name  : " + e.getLocalizedMessage());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" Execution of APIDetailsDaoImpl.retrieve_all() method is finished.");
		return response;
	}
	
	public BaseDTO retrieve(UUID id) {
		BaseDTO response = new BaseDTO();
		//logger.info(" APIDetailsDaoImpl.retrieve(APIDetailsDTO dto) method is called.");
		try {

			Optional<ApiMasterEntity> response_entity = repository.findById(id);
			//logger.info("Api detaild Id {}",id);
			if (response_entity.isPresent()) {
				response.setResponseContent(response_entity);
				response.setStatusCode(200);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//logger.info("API Details Retrieved for ID = " + id);
			} else {
				//logger.info("API Details Not Found for ID = " + id);
				response.setStatusCode(204);
				response.setMessage("No Cotent Found");
			}
		} catch (Exception e) {
			logger.error("  Exception  Occured in APIDetailsDaoImpl.retrieve(APIDetailsDTO dto)   : ");
			logger.error("  Exception Name  : " + e.getLocalizedMessage());
			//response.setMessage("Unable to Retrieve API Details");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" Execution of APIDetailsDaoImpl.retrieve(APIDetailsDTO dto) method is finished.");
		return response;
	}
	public BaseDTO softDelete(UUID id) {
		//logger.info(" <----- API softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ApiMasterEntity api = repository.getOne(id);
			if (api != null) {
				api.setStatus(false);

				repository.save(api);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//logger.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//logger.info("No Record Found..");
			}
		} catch (Exception e) {
			logger.error("<---- APIServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Api");
		}
		//logger.info(" <----- API softDelete Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//logger.info(" <----- API delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			ApiMasterEntity api = repository.getOne(id);
			if (api != null) {
				repository.delete(api);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//logger.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//logger.info("No Record Found..");
			}
		} catch (Exception e) {
			logger.error("<---- APIServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" <----- APIServiceImpl delete Dao END -----> ");
		return response;
	}
	@Override
	public BaseDTO update(UUID id, ApiDetailsDTO dto) {
		//logger.info(" <----- Api update Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<ApiMasterEntity> optional = repository.findById(id);
			if (optional.isPresent()) {				
				ApiMasterEntity api = new ApiMasterEntity();
				api = optional.get();
				List<ApiMasterEntity> checkName = repository.checkApiName(dto.getApiName());
				for (ApiMasterEntity entity : checkName) {
					if (entity.getApiName().equals(dto.getApiName())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else
						check = true;
                        break;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage(dto.getApiName() + " " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//logger.info("Api Name - {} Already exist",dto.getApiName());
				} else {
					api.setApiName(dto.getApiName());
					api.setContentType(dto.getContentType());
					api.setDescription(dto.getDescription());
					api.setRequestMethod(dto.getRequestMethod());
					api.setResponseBodyType(dto.getResponseBodyType());
					api.setUrl(dto.getUrl());
					api.setModule(dto.getModule());
					api.setModule(dto.getFeature());
					api.setStatus(dto.getStatus());
					ApiMasterEntity afterUpdate = repository.save(api);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(dto.getApiName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//logger.info("Api - {} Updated Successfully");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//logger.info("No Record Found..");
			}
		} catch (Exception e) {
			logger.error("<---- APIServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//logger.info(" <----- APIServiceImpl update Dao END -----> ");
		return response;
	}

	private ApiMasterEntity updatedValues(ApiMasterEntity apiMasterEntity, ApiDetailsDTO dto) {
		ApiMasterEntity api = apiMasterEntity;

		if (null != dto.getApiName() && !dto.getApiName().isEmpty()) {
			api.setApiName(dto.getApiName());
		}
		if (null != dto.getContentType() && !dto.getContentType().isEmpty()) {
			api.setContentType(dto.getContentType());
		}
		if (null != dto.getDescription() && !dto.getDescription().isEmpty()) {
			api.setDescription(dto.getDescription());
		}
		if (null != dto.getRequestMethod() && !dto.getRequestMethod().isEmpty()) {
			api.setRequestMethod(dto.getRequestMethod());
		}
		if (null != dto.getResponseBodyType() && !dto.getResponseBodyType().isEmpty()) {
			api.setResponseBodyType(dto.getResponseBodyType());
		}
		if (null != dto.getUrl() && !dto.getUrl().isEmpty()) {
			api.setUrl(dto.getUrl());
		}
		if (null != dto.getFeature() && !dto.getFeature().isEmpty()) {
			api.setFeature(dto.getFeature());
		}
		if (null != dto.getModule() && !dto.getModule().isEmpty()) {
			api.setModule(dto.getModule());
		}
		if (null != dto.getStatus()) {
			api.setStatus(dto.getStatus());
		}
		return api;

	}

	@Override
	public BaseDTO getAllActive() {
		//logger.info(" <----- APIServiceImpl getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<ApiMasterEntity> api = repository.getAllActive();

			if (api.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//logger.info("No Record Found..");
			} else {
				response.setResponseContents(api);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//logger.info("Successfully Get All Active Api Details");
			}
		} catch (Exception e) {
			logger.error("<----- APIServiceImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//logger.info(" <----- APIServiceImpl getAllActive Dao END -----> ");
		return response;
	}
	
	
	  
	  @Override public BaseDTO getAll() {
	  //logger.info(" <----- APIServiceImpl getAll Dao STARTED -----> "); 
		  BaseDTO response = new BaseDTO(); 
		  try { 
			  List<ApiMasterEntity> getapi = repository.findAll();
	  
	  if (getapi.isEmpty()) { String msg =
	  messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
	   response.setMessage(msg);
	  response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
	  //logger.info("No Record Found..");
	  } 
	  else {
	  response.setResponseContents(getapi);
	  response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
	  //logger.info("Successfully Get All Api Details"); 
	  String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US); 
	  response.setMessage(msg); 
	  }
	  } catch (Exception e) {
	  logger.error("<----- APIServiceImpl.getAll() ----->", e);
	  response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE,null,Locale.US); 
	  response.setMessage(msg); }
	  //logger.info(" <----- APIServiceImpl getAll Dao END -----> "); 
		  return  response; }
	 
	

}
