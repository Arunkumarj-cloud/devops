package com.oasys.uppcl_user__master_management.dao.impl;


import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.FieldTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.FieldTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.FieldTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.FieldTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Repository
@Log4j2

public class FieldTypeMasterDaoImpl implements FieldTypeMasterDao {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	FieldTypeMasterRepository fieldTypeMasterRepository;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(FieldTypeMasterDTO fieldTypeMasterDTO) {
		//log.info(" <----- FieldTypeMasterDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {

			FieldTypeMasterEntity fieldTypeMasterEntity = new FieldTypeMasterEntity();

			if (fieldTypeMasterDTO.getFieldTypeName() == null || fieldTypeMasterDTO.getFieldTypeName().isEmpty()) {
				/*log.info(" Field  Type name {} - Is Not Null - {} -Is Not Empty",
						fieldTypeMasterDTO.getFieldTypeName());*/
				response.setMessage("Field  Type Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (fieldTypeMasterDTO.getStatus() == null) {
				log.info(" Status {} - Is Not Null", fieldTypeMasterDTO.getStatus());
				response.setMessage("Status is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}
			/*
			 * Optional<FieldTypeMasterEntity> optional = fieldTypeMasterRepository
			 * .check(fieldTypeMasterDTO.getFieldTypeName());
			 */			
			fieldTypeMasterEntity = fieldTypeMasterRepository.findByFieldTypeNameIgnoreCase(fieldTypeMasterDTO.getFieldTypeName());
			if (fieldTypeMasterEntity != null) {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Field Type Name is Already exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Field  Type Name Already Exist", fieldTypeMasterDTO.getFieldTypeName());

			} else {
				FieldTypeMasterEntity fieldTypeMasterEntity1 = new FieldTypeMasterEntity();

				fieldTypeMasterEntity1.setFieldTypeName(fieldTypeMasterDTO.getFieldTypeName());
				fieldTypeMasterEntity1.setStatus(fieldTypeMasterDTO.getStatus());
				fieldTypeMasterRepository.save(fieldTypeMasterEntity1);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(fieldTypeMasterDTO.getFieldTypeName() + " " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added", fieldTypeMasterDTO.getFieldTypeName());
			}

		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldTypeMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterDaoImpl.create() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- FieldTypeMasterDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<FieldTypeMasterEntity> fieldTypeName = fieldTypeMasterRepository.findById(id);
			if (fieldTypeName.isPresent()) {
				//log.info("Field Type  Name id -{}", id);
				response.setResponseContent(fieldTypeName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Field Type Name Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- FieldTypeMasterDaoImpl.getAll() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FieldTypeMasterEntity> fieldTypeName = fieldTypeMasterRepository.findAll();

			if (fieldTypeName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(fieldTypeName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info("Field Type Name Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- FieldTypeMasterController.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldTypeMasterDaoImpl.getAll() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info("<== FieldTypeMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<FieldTypeMasterEntity> fieldMasterList = null;
		List<FieldTypeMasterEntity> contentList = new ArrayList<FieldTypeMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- FieldTypeMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					fieldMasterList = fieldTypeMasterRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					fieldMasterList = fieldTypeMasterRepository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					fieldMasterList = fieldTypeMasterRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					fieldMasterList = fieldTypeMasterRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (fieldMasterList != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(fieldMasterList.getNumberOfElements());
				response.setTotalRecords(fieldMasterList.getTotalElements());
				response.setTotalPages(fieldMasterList.getTotalPages());
				for (FieldTypeMasterEntity statusMaster : fieldMasterList) {
					contentList.add(statusMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details Successfully");
			}
		} catch (Exception e) {
			log.error("<<<< ------- FieldTypeMasterController.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== FieldTypeMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, FieldTypeMasterDTO fieldTypeMasterDTO) {
		//log.info(" <----- FieldTypeMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;

			if (fieldTypeMasterDTO.getFieldTypeName() == null
					|| fieldTypeMasterDTO.getFieldTypeName().trim().isEmpty()) {
				//log.info("Field Name {} - Is Not Null - {} -Is Not Empty", fieldTypeMasterDTO.getFieldTypeName());
				response.setMessage("Field Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (fieldTypeMasterDTO.getStatus() == null) {
				//log.info(" Status {} - Is Not Null", fieldTypeMasterDTO.getStatus());
				response.setMessage("Status is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			Optional<FieldTypeMasterEntity> optional = fieldTypeMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Field Name id -{}", id);
				FieldTypeMasterEntity fieldMasterEntity = new FieldTypeMasterEntity();
				fieldMasterEntity = optional.get();
				List<FieldTypeMasterEntity> checkCode = fieldTypeMasterRepository
						.getFieldTypeName(fieldTypeMasterDTO.getFieldTypeName().toUpperCase());
				for (FieldTypeMasterEntity entity : checkCode) {
					if (entity.getStatus().equals(fieldTypeMasterDTO.getStatus())) {
						if (id.equals(entity.getId())) {
							check = false;
						} else
							check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Field Type Name " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Field Name - {} Already Exists", fieldTypeMasterDTO.getFieldTypeName());
				} else {
					fieldMasterEntity.setFieldTypeName(fieldTypeMasterDTO.getFieldTypeName());
					fieldMasterEntity.setStatus(fieldTypeMasterDTO.getStatus());
					FieldTypeMasterEntity afterUpdate = fieldTypeMasterRepository.save(fieldMasterEntity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(fieldTypeMasterDTO.getFieldTypeName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Status - {} Updated Successfully", fieldTypeMasterDTO.getFieldTypeName());
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- FieldTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- FieldTypeMasterDaoImpl.update() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info("<------FieldTypeMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<FieldTypeMasterEntity> fieldTypeMasterEntity = null;
		try {
			fieldTypeMasterEntity = fieldTypeMasterRepository.getAllactive();
			if (fieldTypeMasterEntity != null) {
				response.setResponseContents(fieldTypeMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get All Active Details Fetched Successfully");
			} else {
				//log.error("No Records Found");
				response.setMessage("No Record Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------FieldTypeMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------FieldTypeMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started FieldTypeMasterDaoImpl.softDelete()===>");
		try {
			FieldTypeMasterEntity fieldTypeMasterEntity = fieldTypeMasterRepository.getOne(id);
			if (fieldTypeMasterEntity != null) {
				if (fieldTypeMasterEntity.getStatus() == true) {
					fieldTypeMasterEntity.setStatus(false);
					fieldTypeMasterEntity = fieldTypeMasterRepository.save(fieldTypeMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete ");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FieldTypeMasterDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete");
		}
		//log.info("<=== Ended FieldTypeMasterDaoImpl.softDelete() ===>");

		return response;
	}
}