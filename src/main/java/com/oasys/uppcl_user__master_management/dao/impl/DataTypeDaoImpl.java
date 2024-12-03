package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.DataTypeDao;
import com.oasys.uppcl_user__master_management.dto.DataTypeDTO;
import com.oasys.uppcl_user__master_management.entity.DataTypeEntity;
import com.oasys.uppcl_user__master_management.repository.DataTypeRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DataTypeDaoImpl implements DataTypeDao {

	@Autowired
	DataTypeRepository dataTypeRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DataTypeDao dataTypeDao;
	
	@Autowired 
	MessageSource messageSource;

	@Override
	public BaseDTO create(DataTypeDTO dataTypeDTO) {
		//log.info(" <----- DataTypeDaoImpl create Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			
			DataTypeEntity dataTypeEntity = new DataTypeEntity();
			if (dataTypeDTO != null) {
				DataTypeEntity dataTypeEntity1 = objectMapper.convertValue(dataTypeDTO, DataTypeEntity.class);
				dataTypeRepository.save(dataTypeEntity1);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( dataTypeDTO.getDataTypeName() + " " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage(dataTypeDTO.getDataTypeName() + " " + msg);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());

				//log.warn("DataType is already exists..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- DataTypeDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- DataTypeDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeDaoImpl.create() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- DataTypeDaoImpl.getAll()  STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					List<DataTypeEntity> list = dataTypeRepository.findAll();
					if (list.isEmpty()) {
						response.setMessage("No Record Found..");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.error("No Record Found..");
					} else {
						response.setResponseContents(list);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
					}
				} catch (Exception e) {
					log.error("<----- DataTypeDaoImpl.getAll() ----->", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- DataTypeDaoImpl.getAll()  END -----> ");
				return response;
			}
	public BaseDTO update(UUID id, DataTypeDTO dataTypeDTO) {
		//log.info(" <----- DataTypeDaoImpl update  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			DataTypeEntity dataTypeEntity = dataTypeRepository.getOne(id);
			if (dataTypeEntity.getId() != null) {
				//log.info("data Type Id - {}",id);
				DataTypeEntity beforeUpdate = updatedValues(dataTypeEntity, dataTypeDTO);
				if (check == false) {
					dataTypeRepository.save(beforeUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated",id);
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- DataTypeDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- DataTypeDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- DataTypeDaoImpl update   END -----> ");
		return response;
	}
	private DataTypeEntity updatedValues(DataTypeEntity dataTypeEntity, DataTypeDTO dataTypeDTO) {
		DataTypeEntity response = dataTypeEntity;
		response.setDataTypeName(dataTypeDTO.getDataTypeName());
		response.setStatus(dataTypeDTO.getStatus());
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- DataTypeDaoImpl.getById()  STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					Optional<DataTypeEntity> optional = dataTypeRepository.findById(id);
					if (optional.isPresent()) {
						//log.info("data Type Id - {}",id);
						response.setResponseContent(optional.get());
						String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Fetched");
					} else {
						response.setMessage("No Record Found..");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.error("No Record Found..");
					}
				} catch (Exception e) {
					log.error("<---- DataTypeDaoImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- DataTypeDaoImpl.getById()  END -----> ");
				return response;
		
	}
	public BaseDTO delete(UUID id) {
		//log.info(" <----- DataTypeDaoImpl.delete()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			DataTypeEntity dataTypeEntity = dataTypeRepository.getOne(id);
			if (dataTypeEntity != null) {
				dataTypeRepository.delete(dataTypeEntity);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}

		} catch (Exception e) {
			log.error("<---- DataTypeDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeDaoImpl.delete()  END -----> ");
		return response;

	
	}
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- DataTypeDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			DataTypeEntity dataTypeEntity = dataTypeRepository.getOne(id);
			if (dataTypeEntity != null) {
				dataTypeEntity.setStatus(false);
				dataTypeRepository.save(dataTypeEntity);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				///log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- DataTypeDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Data Type");
		}
		//log.info(" <----- DataTypeDaoImpl.softDelete() Service END -----> ");
		return response;
	}
	public BaseDTO getAllActive() {
		//log.info(" <----- DataTypeDaoImpl.getAllActive()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<DataTypeEntity> list = dataTypeRepository.getAllActive();
			if (list.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");
			} else {
				response.setResponseContents(list);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get all DataType Details");
			}
		} catch (Exception e) {
			log.error("<----- DataTypeDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		///log.info(" <----- DataTypeDaoImpl.getAllActive()  END -----> ");
		return response;
	}
	public Page<DataTypeEntity> getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- DataTypeServiceImpl getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		Pageable pageRequest;
		Page<DataTypeEntity> dataTypeEntity = null;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					dataTypeEntity = dataTypeRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					dataTypeEntity = dataTypeRepository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					dataTypeEntity = dataTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					dataTypeEntity = dataTypeRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
				response.setResponseContent(dataTypeEntity);
			}
		} catch (Exception e) {
			log.error("<----- DataTypeServiceImpl.getLazyList() ----->", e);
		}
		//log.info(" <----- DataTypeServiceImpl getLazyList Dao END -----> ");
		return dataTypeEntity;
	}
	
}
	


