package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.StateMasterDao;
import com.oasys.uppcl_user__master_management.dto.StateMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.StateMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StateMasterServiceImpl implements StateMasterService {
	
	@Autowired
	private StateMasterDao stateMasterDao;

	@Autowired
	StateMasterRepository stateMasterRepository;

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	MessageSource messageSource;

	public BaseDTO saveState(StateMasterDTO dto) {
		//log.info("Entered in StateMasterServiceImpl.saveState(StateMasterDTO dto)");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			StateMasterEntity entity = stateMasterDao.findByStateCode(dto.getStateCode());
			if (entity != null) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This State Code Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("State Code - {} Already Exist");
				return response;
			}
			entity = mapper.convertValue(dto, StateMasterEntity.class);
			StateMasterEntity responseEntity = stateMasterDao.save(entity);
			if (null == responseEntity) {
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				//log.info(dto.getStateCode() + " " + message + " - {} ", dto.getStateCode());
			} else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage("State " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(dto.getStateCode() + " " + message + " - {} ", dto.getStateCode());
			}
		} catch (DataIntegrityViolationException e) {
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(dto.getStateCode() + " " + message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured : {} ", e);
		} catch (Exception e) {
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(dto.getStateCode() + " " + message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured : {} ", e);
		}
		//log.info("Finished Execution of StateMasterServiceImpl.saveState(StateMasterDTO dto)");
		return response;
	}

	public BaseDTO updateState(UUID id, StateMasterDTO dto) {
		BaseDTO response = new BaseDTO();
		//log.info("Started StateMasterServiceImpl.updateState - {} and - {} ", id, dto);
		String message = "";
		boolean validateStateCode = false;
		try {
			Optional<StateMasterEntity> stateMaster = stateMasterDao.getState(id);
			if (!stateMaster.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				//log.error(message + " - {} ", id);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				return response;
			}
			//log.info("updateState - {} and - {} ", id, dto);
			StateMasterEntity stateMasterEntity = stateMaster.get();
			stateMasterEntity.setStateName(dto.getStateName());
			stateMasterEntity.setStateType(dto.getStateType());
			stateMasterEntity.setStateCode(dto.getStateCode());
			stateMasterEntity.setStatus(dto.getStatus());
			stateMasterEntity.setTaxIdentificationNo(dto.getTaxIdentificationNo());
			
			List<StateMasterEntity> natureOfBusinessList = stateMasterDao.findByIdNotIn(id);
			for (StateMasterEntity natureOfBusinessMasterEntity : natureOfBusinessList) {
				if (natureOfBusinessMasterEntity.getId() != stateMasterEntity.getId()) {
					if (natureOfBusinessMasterEntity.getStateCode()
							.equalsIgnoreCase(stateMasterEntity.getStateCode())) {
						validateStateCode = true;
						break;
					} else
						validateStateCode = false;
				} else
					validateStateCode = false;
			}
			if (!validateStateCode) {
				stateMasterEntity = stateMasterDao.save(stateMasterEntity);
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(dto.getStateCode() + " " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(dto.getStateCode() + " " + message);
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This State Code Already  Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("State Code - {} Already Exist");
			}
		} catch (DataIntegrityViolationException e) {
			log.error(" Exception StateMasterServiceImpl.updateState {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error(" Exception StateMasterServiceImpl.updateState {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("Finished Execution of StateMasterServiceImpl.updateState(StateMasterDTO dto)");
		return response;
	}

	public BaseDTO getState(UUID id) {
		//log.info("Entered in StateMasterServiceImpl.getState(UUID id)");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			log.info("State Master Request ID = " + id);
			Optional<StateMasterEntity> responseEntity = stateMasterDao.getState(id);
			if (responseEntity.isPresent()) {
				StateMasterDTO stateMasterDTO = new StateMasterDTO();
				stateMasterDTO = mapper.convertValue(responseEntity.get(), StateMasterDTO.class);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContent(stateMasterDTO);
				//log.info("State Master Data Found for ID = " + id);
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				log.debug("Requested State Master Data Not Found for ID = " + id);
			}
		} catch (Exception e) {
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}
		//log.info("Finished Execution of StateMasterServiceImpl.getState(UUID id)");
		return response;
	}

	public BaseDTO getAllState(PaginationRequestDTO dto) {
		//log.info("Entered in StateMasterServiceImpl.getAllState(StateMasterDTO dto)");
		BaseDTO response = stateMasterDao.getAllState(dto);
		//log.info("Finished Execution of StateMasterServiceImpl.getAllState(StateMasterDTO dto)");
		return response;
	}

	public BaseDTO deleteState(StateMasterDTO dto) {
		//log.info("Entered in StateMasterServiceImpl.deleteState(StateMasterDTO dto)");
		BaseDTO response = stateMasterDao.deleteState(dto);
		//log.info("Finished Execution of StateMasterServiceImpl.deleteState(StateMasterDTO dto)");
		return response;
	}

	@Override
	public BaseDTO getActiveStateList() {
		//log.info("Entered in StateMasterServiceImpl.getActiveStateList()");
		BaseDTO response = stateMasterDao.getActiveStateList();
		//log.info("Finished Execution of StateMasterServiceImpl.getActiveStateList()");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		BaseDTO response = stateMasterDao.getAll();
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("Entered in StateMasterServiceImpl.getActiveStateList()");
		try {
			StateMasterEntity stateMasterEntity = stateMasterRepository.getOne(id);
			if (stateMasterEntity != null) {
				if (stateMasterEntity.getStatus() == true) {
					stateMasterEntity.setStatus(false);
					stateMasterEntity = stateMasterRepository.save(stateMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete State Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete State Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- StateMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete State Master Details");
		}
		//log.info("Finished Execution of StateMasterServiceImpl.softDelete()");
		return response;
	}

	@Override
	public BaseDTO getstateName(String stateName) {
		//log.info(" <----- StateMasterServiceImpl.getBydistricName() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		StateMasterEntity stateMasterEntity = null;
		try {

			stateMasterEntity = stateMasterRepository.getstateName(stateName.toUpperCase());
			if (stateMasterEntity != null) {
				baseDTO.setResponseContent(stateMasterEntity);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
			} else {
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}

		} catch (Exception e) {
			log.error("<---- StateMasterServiceImpl.getBydistricName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			// baseDTO.setMessage("Unable to Get District Master");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- StateMasterServiceImpl.getBydistricName() Service END -----> ");
		return baseDTO;
	}


}
