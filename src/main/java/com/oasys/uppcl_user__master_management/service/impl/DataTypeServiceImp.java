package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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
import com.oasys.uppcl_user__master_management.service.DataTypeService;

import lombok.extern.log4j.Log4j2;

@Service("DataTypeServiceImpl")
@Log4j2
public class DataTypeServiceImp implements  DataTypeService {
	@Autowired
    DataTypeDao	dataTypeDao;
	@Autowired
    DataTypeRepository dataTypeRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;
	
	public BaseDTO create(DataTypeDTO dataTypeDTO) {
		//log.info(" <-----DataTypeServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.create(dataTypeDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- DataTypeServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Add DataType");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error("<---- DataTypeServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Add DataType");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- DataTypeServiceImpl.getAll() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = dataTypeDao.getAll();
				} catch (Exception e) {
					log.error("<---- DataTypeServiceImpl.getAll() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//response.setMessage("Unable to GetAll DataType");
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- DataTypeServiceImpl.getAll() Service END -----> ");
				return response;
		
	}
	
	public BaseDTO getById(UUID id) {
		//log.info(" <-----DataTypeServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.getById(id);
		} catch (Exception e) {
			log.error("<---- DataTypeServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get DataType");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----DataTypeServiceImpl.getById() Service END -----> ");
		return response;
	}
	public BaseDTO delete(UUID id) {
		//log.info(" <----- DataTypeServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.delete(id);
		} catch (Exception e) {
			log.error("<---- DataTypeServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Delete DataType");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeServiceImpl.delete() Service END -----> ");
		return response;
	}
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- DataTypeServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- DataTypeServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete DataType");
		}
		//log.info(" <----- DataTypeServiceImpl.softDelete() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id,DataTypeDTO dataTypeDTO) {
		//log.info(" <----- DataTypeServiceImpl.update()Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.update(id,dataTypeDTO);
		} catch(DataIntegrityViolationException e) {
			log.error("<---- DataTypeServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Update DataType");
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
	}catch (Exception e) {
			log.error("<---- DataTypeServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Update DataType");
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeServiceImpl.update()Service END -----> ");
		return response;
	}
	public BaseDTO getAllActive() {
		//log.info(" <----- DataTypeServiceImpl.getAll()Active Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = dataTypeDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- CorporationServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAllActive Corporation");
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- DataTypeServiceImpl.getAll()Active Service END -----> ");
		return response;
	}
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- DataTypeServiceImpl getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<DataTypeDTO> dataTypeDTO = new ArrayList<DataTypeDTO>();
		try {
			Page<DataTypeEntity> dataTypeEntity = dataTypeDao.getLazyList(requestData);
			if (dataTypeEntity.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(dataTypeEntity.getNumberOfElements());
				response.setTotalRecords(dataTypeEntity.getTotalElements());
				response.setTotalPages(dataTypeEntity.getTotalPages());
				for (DataTypeEntity dataType : dataTypeEntity) {
					DataTypeDTO reason = objectMapper.convertValue(dataType, DataTypeDTO.class);
					dataTypeDTO.add(reason);
				}
				response.setResponseContents(dataTypeDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			}
		} catch (Exception e) {
			log.error("<----- ReasonTypeServiceImpl.getLazyList() ----->", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ReasonType getLazyList Service END -----> ");
		return response;
	}
}
