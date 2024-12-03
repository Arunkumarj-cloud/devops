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
import com.oasys.uppcl_user__master_management.dao.FieldMasterDao;
import com.oasys.uppcl_user__master_management.dto.FieldMasterDTO;
import com.oasys.uppcl_user__master_management.entity.FieldMasterEntity;
import com.oasys.uppcl_user__master_management.repository.FieldMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class FieldMasterDaoImpl implements FieldMasterDao {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	FieldMasterRepository fieldMasterRepository;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(FieldMasterDTO fieldMasterDTO) {
		//log.info(" <----- FieldMasterDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {

			FieldMasterEntity fieldMasterEntity = new FieldMasterEntity();

			if (fieldMasterDTO.getFieldName() == null || fieldMasterDTO.getFieldName().isEmpty()) {
				//log.info(" Field Name {} - Is Not Null - {} -Is Not Empty", fieldMasterDTO.getFieldName());
				response.setMessage("Field Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (fieldMasterDTO.getStatus() == null) {
				//log.info(" Status {} - Is Not Null", fieldMasterDTO.getStatus());
				response.setMessage("Status is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}
			//Optional<FieldMasterEntity> optional = fieldMasterRepository.check(fieldMasterDTO.getFieldName().toUpperCase());
			fieldMasterEntity = fieldMasterRepository.findByFieldNameIgnoreCase(fieldMasterDTO.getFieldName());
			if (fieldMasterEntity != null) {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Field Name Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Field Name is Already Exist", fieldMasterDTO.getFieldName());

			} else {
				FieldMasterEntity fieldMasterEntity1 = new FieldMasterEntity();

				fieldMasterEntity1.setFieldName(fieldMasterDTO.getFieldName());

				fieldMasterEntity1.setStatus(fieldMasterDTO.getStatus());
				fieldMasterRepository.save(fieldMasterEntity1);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(fieldMasterDTO.getFieldName() + " " + msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added", fieldMasterDTO.getFieldName());
			}

		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FieldMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldMasterDaoImpl.create() END -----> ");
		return response;
	}
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- FieldMasterDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<FieldMasterEntity> fieldName = fieldMasterRepository.findById(id);
			if (fieldName.isPresent()) {
				//log.info("Field Name id -{}", id);
				response.setResponseContent(fieldName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Field Name Details ");
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
		//log.info(" <----- FieldMasterDaoImpl.getById() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- FieldMasterDaoImpl.getAll() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<FieldMasterEntity> fieldName = fieldMasterRepository.findAll();

			if (fieldName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(fieldName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info(" Field Name Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- FieldMasterDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldMasterDaoImpl.getAll() END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- FieldMasterDaoImpl.getAllActive() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
		
			List<FieldMasterEntity> fieldName = fieldMasterRepository.getAllactive();

			if (fieldName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(fieldName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				//log.info(" Field Name Get All Active Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- FieldMasterDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FieldMasterDaoImpl.getAllActive() END -----> ");
		return response;

	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- FieldMasterDaoImpl softDelete()  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			FieldMasterEntity master = fieldMasterRepository.getOne(id);
			if (master != null) {
				master.setStatus(false);
				fieldMasterRepository.save(master);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FieldMasterDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete ");
		}
		//log.info(" <----- FieldMasterDaoImpl softDelete()  END -----> ");
		return response;
	}

	@Override
	public BaseDTO lazyList(PaginationRequestDTO pageData) {
		//log.info("<== FieldMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<FieldMasterEntity> fieldMasterEntity = null;
		List<FieldMasterEntity> contentList = new ArrayList<FieldMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- FieldMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					fieldMasterEntity = fieldMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					fieldMasterEntity = fieldMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					fieldMasterEntity = fieldMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					fieldMasterEntity = fieldMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (fieldMasterEntity != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(fieldMasterEntity.getNumberOfElements());
				response.setTotalRecords(fieldMasterEntity.getTotalElements());
				response.setTotalPages(fieldMasterEntity.getTotalPages());
				for (FieldMasterEntity fieldMaster : fieldMasterEntity) {
					contentList.add(fieldMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details Successfully");
			}
		} catch (Exception e) {
			log.error("<<<< ------- FieldMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== FieldMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, FieldMasterDTO fieldMasterDTO) {
		//log.info(" <----- FieldMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
/*
			if (fieldMasterDTO.getFieldName() == null || fieldMasterDTO.getFieldName().trim().isEmpty()) {
				log.info("Field Name {} - Is Not Null - {} -Is Not Empty", fieldMasterDTO.getFieldName());
				response.setMessage("Field Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}

			if (fieldMasterDTO.getStatus() == null) {
				log.info(" Status {} - Is Not Null", fieldMasterDTO.getStatus());
				response.setMessage("Status is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;
			}*/
			Optional<FieldMasterEntity> optional = fieldMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Field Name id -{}", id);
				FieldMasterEntity fieldMasterEntity = new FieldMasterEntity();
				fieldMasterEntity = optional.get();
				List<FieldMasterEntity> checkCode = fieldMasterRepository.findByFieldId(fieldMasterDTO.getId());
				for (FieldMasterEntity entity : checkCode) { //
					if (entity.getFieldName().equalsIgnoreCase(fieldMasterDTO.getFieldName())) {
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
					response.setMessage("This Field Name " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Filed Name -{} Already Exists", fieldMasterDTO.getStatus());
				} else {
					fieldMasterEntity.setFieldName(fieldMasterDTO.getFieldName());
					fieldMasterEntity.setStatus(fieldMasterDTO.getStatus());
					FieldMasterEntity afterUpdate = fieldMasterRepository.save(fieldMasterEntity);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(fieldMasterDTO.getFieldName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Field - {} Updated Successfully", fieldMasterDTO.getFieldName());
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FieldMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- FieldMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- FieldMasterDaoImpl.update() END -----> ");
		return response;

	}
}
