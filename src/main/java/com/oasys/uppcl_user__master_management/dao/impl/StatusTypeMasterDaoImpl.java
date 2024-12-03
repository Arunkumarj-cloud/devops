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

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.StatusTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.StatusTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StatusTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StatusTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class StatusTypeMasterDaoImpl implements StatusTypeMasterDao{
	@Autowired
	StatusTypeMasterRepository statusTypeMasterRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(StatusTypeMasterDTO statusTypeMasterDTO) {
		//log.info(" <----- StatusTypeMasterDaoImpl.create() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
	
			StatusTypeMasterEntity statusTypeMasterEntity = new StatusTypeMasterEntity();
			
			if(statusTypeMasterDTO.getStatusTypeName().trim().isEmpty()) {
				//log.info(" Status Type Name {} - Is Not Null - {} -Is Not Empty",statusTypeMasterDTO.getStatusTypeName() );
				response.setMessage(" Status Type Name is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				return response;	
			}
			
			Optional<StatusTypeMasterEntity> optional = statusTypeMasterRepository.check(statusTypeMasterDTO.getStatusTypeName() );
						if(optional.isPresent()) {
							String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							response.setMessage(" Status Type Name is Already exist");
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.info("Status Type Name is Already exist",statusTypeMasterDTO.getStatusTypeName() );
						
						}else {
							statusTypeMasterEntity.setStatusTypeName(statusTypeMasterDTO.getStatusTypeName());
							statusTypeMasterEntity.setStatus(statusTypeMasterDTO.getStatus());
							statusTypeMasterRepository.save(statusTypeMasterEntity);
						String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage( statusTypeMasterDTO.getStatusTypeName() + " " + msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Added",statusTypeMasterDTO.getStatusTypeName());
						}
						
		} catch (DataIntegrityViolationException e) {
			log.error("<---- StatusTypeMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- StatusTypeMasterDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusTypeMasterDaoImpl.create() END -----> ");
		return response;
	}
	
	
	public BaseDTO getById(UUID id) {
		//log.info(" <----- StatusTypeMasterDaoImpl.getById() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<StatusTypeMasterEntity> statusTypeName = statusTypeMasterRepository.findById(id);
			if (statusTypeName.isPresent()) {
				//log.info("Status Type Name id -{}",id);
				response.setResponseContent(statusTypeName);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Status Type Name Details ");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- StatusTypeMasterDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg );
		}
		//log.info(" <----- StatusTypeMasterDaoImpl.getById() END -----> ");
		return response;
	}
	
	
	public BaseDTO getAll() {
		//log.info(" <----- StatusTypeMasterDaoImpl.getAll() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<StatusTypeMasterEntity> statusTypeName = statusTypeMasterRepository.findAll();

			if (statusTypeName.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(statusTypeName);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Status Type Name Get All Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- StatusTypeMasterDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- StatusTypeMasterDaoImpl.getAll() END -----> ");
		return response;
	}
	
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info("<== StatusTypeMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<StatusTypeMasterEntity> statusTypeMasterList = null;
		List<StatusTypeMasterEntity> contentList = new ArrayList<StatusTypeMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- StatusTypeMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					statusTypeMasterList = statusTypeMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					statusTypeMasterList = statusTypeMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					statusTypeMasterList = statusTypeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					statusTypeMasterList = statusTypeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (statusTypeMasterList != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(statusTypeMasterList.getNumberOfElements());
				response.setTotalRecords(statusTypeMasterList.getTotalElements());
				response.setTotalPages(statusTypeMasterList.getTotalPages());
				for (StatusTypeMasterEntity statusTypeMaster : statusTypeMasterList) {
					contentList.add(statusTypeMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" No Record Found");
			}
		} catch (Exception e) {
			log.error("<<<< ------- StatusTypeMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== StatusTypeMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}
	
	public BaseDTO getAllActive() {
		//log.info("<------StatusTypeMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<StatusTypeMasterEntity> statusTypeMasterEntity = null;
		try {
			statusTypeMasterEntity = statusTypeMasterRepository.getAllactive();
			if (statusTypeMasterEntity != null) {
				response.setResponseContents(statusTypeMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get All Active Details Fetched Successfully");
			} else {
				log.error("No Records Found");
				response.setMessage("No Record Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------StatusTypeMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------StatusTypeMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id, StatusTypeMasterDTO statusTypeMasterDTO) {
		//log.info(" <----- StatusTypeMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		StatusTypeMasterEntity statusTypeMasterEntity = new StatusTypeMasterEntity();
		try {
			boolean check = false;
	
			Optional<StatusTypeMasterEntity> optional = statusTypeMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Status Name id -{}",id);
				statusTypeMasterEntity = optional.get();
			
				if(statusTypeMasterDTO.getStatusTypeName().trim().isEmpty()) {
					//log.info(" Status Type Name {} - Is Not Null - {} -Is Not Empty",statusTypeMasterDTO.getStatusTypeName() );
					response.setMessage(" Status Type Name is Required");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					return response;	
				}
			
				List<StatusTypeMasterEntity> checkCode = statusTypeMasterRepository.findByName(statusTypeMasterDTO.getStatusTypeName());
				for (StatusTypeMasterEntity entity : checkCode) {
					if (entity.getStatusTypeName().equals(statusTypeMasterDTO.getStatusTypeName())) {
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
					response.setMessage(statusTypeMasterDTO.getStatusTypeName() + " " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Status Name -{} Already Exists",statusTypeMasterDTO.getStatusTypeName());
				} else {
					statusTypeMasterEntity.setStatusTypeName(statusTypeMasterDTO.getStatusTypeName());
					statusTypeMasterEntity.setStatus(statusTypeMasterDTO.getStatus());
					StatusTypeMasterEntity afterUpdate = statusTypeMasterRepository.save(statusTypeMasterEntity);
					//response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(statusTypeMasterDTO.getStatusTypeName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					///log.info("Status Type - {} Updated Successfully", statusTypeMasterDTO.getStatusTypeName());
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- StatusTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- StatusTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- StatusTypeMasterDaoImpl.update() END -----> ");
		return response;
	}


}
