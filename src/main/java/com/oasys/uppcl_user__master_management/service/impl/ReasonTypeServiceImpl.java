package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import com.oasys.uppcl_user__master_management.dao.ReasonTypeDao;
import com.oasys.uppcl_user__master_management.dto.ReasonTypeDTO;
import com.oasys.uppcl_user__master_management.entity.ReasonTypeEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ReasonTypeService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReasonTypeServiceImpl implements ReasonTypeService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ReasonTypeDao reasonTypeDao;

	@Autowired
	MessageSource messageSource;

	public BaseDTO create(ReasonTypeDTO reasonTypeDTO) {
		// log.info(" <----- ReasonTypeServiceImpl create Service STARTED -----> {}
		// ",reasonTypeDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			if (validateReasonType(reasonTypeDTO.getReasonType())) {
				// log.info("Reason type - Is Validate {}..", reasonTypeDTO.getReasonType());
				ReasonTypeEntity reasonTypeEntity = objectMapper.convertValue(reasonTypeDTO, ReasonTypeEntity.class);
				reasonTypeEntity = reasonTypeDao.save(reasonTypeEntity);
				if (reasonTypeEntity.getId() != null) {
					message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(reasonTypeDTO.getReasonType() + " " + message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					// log.info("Successfully Added", reasonTypeDTO.getReasonType());
				} else {
					message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(reasonTypeDTO.getReasonType() + " " + message);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					// log.info(message+" {} ", reasonTypeEntity);
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Reason Type " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				// log.warn("Reason Type is Already Exists..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ReasonTypeServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- ReasonTypeServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		// log.info(" <----- ReasonType create Service END -----> ");
		return response;
	}

	public BaseDTO update(UUID id, ReasonTypeDTO reasonTypeDTO) {
		// log.info(" <----- ReasonType update ServiceDaoImpl STARTED -----> {} and {}",
		// id, reasonTypeDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean validateResonType = false;
			Optional<ReasonTypeEntity> reasonTypeEntity = reasonTypeDao.findByReasonTypeId(id);
			if (reasonTypeEntity.isPresent()) {
				ReasonTypeEntity beforeUpdate = updatedValues(reasonTypeEntity.get(), reasonTypeDTO);
				List<ReasonTypeEntity> reasonTypeList = reasonTypeDao.exceptId(id);
				for (ReasonTypeEntity reasontype : reasonTypeList) {
					if (reasontype.getId() != beforeUpdate.getId()) {
						if (reasontype.getReasonType().equalsIgnoreCase(beforeUpdate.getReasonType())) {
							validateResonType = true;
							break;
						} else
							validateResonType = false;
					} else
						validateResonType = false;
				}
				if (!validateResonType) {
					beforeUpdate = reasonTypeDao.save(beforeUpdate);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(reasonTypeDTO.getReasonType() + " " + message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					// log.info("Successfully Updated");
				} else {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Reason Type " + message);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					// log.warn(reasonTypeDTO.getReasonType() + " " + message);
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				// log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ReasonTypeServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);

		} catch (Exception e) {
			log.error("<---- ReasonTypeServiceImpl.update() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		// log.info(" <----- ReasonType update ServiceDaoImpl END -----> ");
		return response;
	}
	
	  public BaseDTO getById(UUID id) {
	  //log.info(" <----- ReasonType getById Service STARTED -----> "); 
		  BaseDTO response = new BaseDTO(); try {
			  ReasonTypeEntity reasonTypeEntity =reasonTypeDao.getById(id); 
		  ReasonTypeDTO reason =objectMapper.convertValue(reasonTypeEntity, ReasonTypeDTO.class);
		  if(reasonTypeEntity.getId() == null)
		  {
	  response.setMessage("No Record Found..");
	  response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
	  ///log.error("No Record Found..");
	  } else{
	  response.setResponseContent(reason); 
	  String msg =messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE,null,Locale.US); 
	  response.setMessage(msg);
	  response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
	  //log.info("Successfully Get ReasonType Details..",id); 
	  }
		  } catch (Exception e) { 
			  //log.error("<---- ReasonTypeServiceImpl.getById() ----> EXCEPTION", e);
	  response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	  String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE,null,Locale.US);
	  response.setMessage(msg);  
	  }
	  //log.info(" <----- ReasonType getById Service END -----> "); 
		  return response;
		  }
	 

	
	  public BaseDTO getAll() {
			//log.info(" <----- ReasonType getAll Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			List<ReasonTypeDTO> reasonTypeDTO = new ArrayList<ReasonTypeDTO>();
			try {
				List<ReasonTypeEntity> reasonTypeEntity = reasonTypeDao.getAll();
				for (ReasonTypeEntity reasonType : reasonTypeEntity) {
					ReasonTypeDTO reason = objectMapper.convertValue(reasonType, ReasonTypeDTO.class);
					reasonTypeDTO.add(reason);
				}
				if (reasonTypeDTO.isEmpty()) {
					response.setMessage("No Record Found..");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("No Record Found..");
				} else {
					response.setResponseContent(reasonTypeDTO);
					String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Get  All ReasonType Details..");
				}
			} catch (Exception e) {
				log.error("<---- ReasonTypeServiceImpl.getAll() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
			}
			//log.info(" <----- ReasonType getAll Service END -----> ");
			return response;
		}

	 
	  public BaseDTO getAllActive() {
			//log.info(" <----- ReasonType getAllActive Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			List<ReasonTypeDTO> reasonTypeDTO = new ArrayList<ReasonTypeDTO>();
			try {
				List<ReasonTypeEntity> reasonTypeEntity = reasonTypeDao.getAllActive();
				for (ReasonTypeEntity reasonType : reasonTypeEntity) {
					ReasonTypeDTO reason = objectMapper.convertValue(reasonType, ReasonTypeDTO.class);
					reasonTypeDTO.add(reason);
				}
				if (reasonTypeDTO.isEmpty()) {
					response.setMessage("No Record Found..");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.error("No Record Found..");
				} else {
					response.setResponseContent(reasonTypeDTO);
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Get All Active  ReasonType Details..");
				}
			} catch (Exception e) {
				log.error("<---- ReasonTypeServiceImpl.getAllActive() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			}
			//log.info(" <----- ReasonType getAllActive Service END -----> ");
			return response;
		}

	  public BaseDTO getLazyList(PaginationRequestDTO requestData) {
			//log.info(" <----- ReasonType getLazyList Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			List<ReasonTypeDTO> reasonTypeDTO = new ArrayList<ReasonTypeDTO>();
			try {
				Page<ReasonTypeEntity> reasonTypeEntity = reasonTypeDao.getLazyList(requestData);
				if (reasonTypeEntity.isEmpty()) {
					String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
					response.setMessage( msg);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.warn("No Record Found..");
				} else {
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					response.setNumberOfElements(reasonTypeEntity.getNumberOfElements());
					response.setTotalRecords(reasonTypeEntity.getTotalElements());
					response.setTotalPages(reasonTypeEntity.getTotalPages());
					for (ReasonTypeEntity reasonType : reasonTypeEntity) {
						ReasonTypeDTO reason = objectMapper.convertValue(reasonType, ReasonTypeDTO.class);
						reasonTypeDTO.add(reason);
						//log.info("Sucessfully Get Lazy List Details");
					}
					response.setResponseContents(reasonTypeDTO);
				}
			} catch (Exception e) {
				log.error("<----- ReasonTypeServiceImpl.getLazyList() ----->", e);
				String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			//log.info(" <----- ReasonType getLazyList Service END -----> ");
			return response;
		}
	
	 
	 

	

	
	  private ReasonTypeEntity updatedValues(ReasonTypeEntity reasonTypeEntity,ReasonTypeDTO reasonTypeDTO)
	  {
		  ReasonTypeEntity response = reasonTypeEntity;
	  response.setReasonType(reasonTypeDTO.getReasonType());
	  response.setDescription(reasonTypeDTO.getDescription());
	  response.setStatus(reasonTypeDTO.getStatus());
	  response.setRemarks(reasonTypeDTO.getRemarks());
	  return response;
	  }
	 
	  public BaseDTO delete(UUID id) {
			//log.info(" <----- ReasonType delete Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			try {
				ReasonTypeEntity reasonTypeEntity = reasonTypeDao.getById(id);
				if (reasonTypeEntity != null) {
					reasonTypeEntity = reasonTypeDao.delete(id);
					String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.error("No Record Found..");
				}
			} catch (Exception e) {
				log.error("<---- ReasonTypeServiceImpl.delete() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
			}
			//log.info(" <----- ReasonType delete Service END -----> ");
			return response;
		}

	
	
	  public BaseDTO softDelete(UUID id) {
			//log.info(" <----- ReasonType softDelete Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			try {
				ReasonTypeEntity reasonTypeEntity = reasonTypeDao.getById(id);
				if (reasonTypeEntity != null) {
					reasonTypeEntity.setStatus(false);
					reasonTypeEntity = reasonTypeDao.save(reasonTypeEntity);

					if (reasonTypeEntity.getStatus() == false) {
						response.setMessage("Successfully Deleted");
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Deleted");
					} else {
						response.setMessage("Unable to Delete ReasonType..");
						response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
						//log.error("Unable to Delete ReasonType..");
					}
				} else {
					response.setMessage("No Record Found..");
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.error("No Record Found..");
				}
			} catch (Exception e) {
				log.error("<---- ReasonTypeServiceImpl.softDelete() ----> EXCEPTION", e);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				response.setMessage("Unable to Delete Reason Type");
			}
			//log.info(" <----- ReasonType softDelete Service END -----> ");
			return response;
		}


	private boolean validateReasonType(String reasonType) {
		// log.info(" <----- ReasonType validateReasonType Service STARTED -----> ");
		boolean flag = false;
		try {
			ReasonTypeEntity reasonTypeEntity = reasonTypeDao.validateReasonType(reasonType);
			if (reasonTypeEntity == null) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			log.error("<---- ReasonTypeServiceImpl.validateReasonType() ----> EXCEPTION", e);
		}
		// log.info(" <----- ReasonType validateReasonType Service END -----> ");
		return flag;
	}

}
