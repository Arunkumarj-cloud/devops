
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
import com.oasys.uppcl_user__master_management.dao.ReligionMasterDao;
import com.oasys.uppcl_user__master_management.dto.ReligionMasterDTO;
import com.oasys.uppcl_user__master_management.entity.ReligionMaster;
import com.oasys.uppcl_user__master_management.repository.ReligionMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ReligionMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReligionMasterServiceImpl implements ReligionMasterService {

	@Autowired
	ReligionMasterDao religionMasterDao;

	@Autowired
	ReligionMasterRepository religionMasterRepository;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ReligionMasterServiceImpl.getAll()===>");
		try {
			response = religionMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception ReligionMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
		}
		return response;
	}

	public BaseDTO create(ReligionMasterDTO religionDTO) {
		//log.info("<== Started ReligionMasterServiceImpl.create ==>");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			ReligionMaster religionMaster = religionMasterDao.findByReligionName(religionDTO.getName());
			if(religionMaster != null) {	
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Religion Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("Religion - {} Already Exist");
				return response;
			}
			religionMaster = objectMapper.convertValue(religionDTO, ReligionMaster.class);
			religionMaster = religionMasterDao.save(religionMaster);
			if(religionMaster == null) {
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				//log.error(religionDTO.getName() +" "+ message);
			}else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(religionDTO.getName() +" "+ message);
			}			
		} catch (DataIntegrityViolationException e) {
			log.error("<== ReligionMasterServiceImpl.create Exception {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( message);
		} catch (Exception e) {
			log.error("<== ReligionMasterServiceImpl.create Exception {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		}
		//log.info("<== Ended ReligionMasterServiceImpl.create ==>");
		return response;
	}

	public BaseDTO update(UUID id, ReligionMasterDTO religionMasterDTO) {
		//log.info("<== Started ReligionMasterServiceImpl.update ==>");
		BaseDTO response = new BaseDTO();
		String message = "";
		boolean validateReligionName = false;
		try {
			Optional<ReligionMaster> religionMaster = religionMasterDao.getById(id);
			if(!religionMaster.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.info(message + " - {} ", id);
			}
			ReligionMaster updatedReligion = updatedValues(religionMaster.get(), religionMasterDTO);
			List<ReligionMaster> exceptReligionMaster = religionMasterDao.findByIdNotIn(id);
			for (ReligionMaster religionName : exceptReligionMaster) {
				if (religionName.getId() != updatedReligion.getId()) {
					if (religionName.getName().equalsIgnoreCase(updatedReligion.getName())) {
						validateReligionName = true;
						break;
					} else {
						validateReligionName = false;
					}
				} else {
					validateReligionName = false;
				}
			}
			if (!validateReligionName) {
				updatedReligion = religionMasterDao.save(updatedReligion);
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(updatedReligion.getName() + " " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(religionMasterDTO.getName() + " " + message);
			}else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Religion Already exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("Religion - {} Already exist");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<== Exception in ReligionMasterServiceImpl.update - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<== Exception in ReligionMasterServiceImpl.update - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( message);
		}
		//log.info("<== Ended ReligionMasterServiceImpl.update ==>");
		return response;
	}

	public BaseDTO getById(UUID id) {
		//log.info("<== Started ReligionMasterServiceImpl.getById ==>");
		BaseDTO response = new BaseDTO();
		ReligionMasterDTO religionMasterDTO = new ReligionMasterDTO();
		String message = "";
		try {
			Optional<ReligionMaster> religionMaster = religionMasterDao.getById(id);
			if(religionMaster.isPresent()) {
				religionMasterDTO = objectMapper.convertValue(religionMaster.get(), ReligionMasterDTO.class);
				response.setResponseContent(religionMasterDTO);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details  - {} ", religionMasterDTO);
			}else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error(message ," - {} ", id);
			}
		} catch (Exception e) {
			log.error("<== Exception in ReligionMasterServiceImpl.getById {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( message);
		}
		//log.info("<== Ended ReligionMasterServiceImpl.getById ==>");
		return response;
	}


	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ReligionMasterServiceImpl.delete() ===>");
		try {
			response = religionMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception ReligionMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ReligionMasterServiceImpl.delete() ===>");
		return response;
	}

	
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ReligionMasterServiceImpl.getAllActive()===>");
		try {
			response = religionMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception ReligionMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ReligionMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	
	public BaseDTO getAllLazy(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ReligionMasterServiceImpl.getAllLazy()===>");
		try {
			response = religionMasterDao.getAllLazy(pageData);
		} catch (Exception e) {
			log.error(" Exception ReligionMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ReligionMasterServiceImpl.getAllLazy() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ReligionMasterServiceImpl.softDelete()===>");
		try {
			ReligionMaster religionMaster = religionMasterRepository.getOne(id);
			if (religionMaster != null) {
				if (religionMaster.getStatus() == true) {
					religionMaster.setStatus(false);
					religionMaster = religionMasterRepository.save(religionMaster);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete  Religion Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Religion Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ReligionMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Religion Master Details");
		}
		//log.info("<=== Ended ReligionMasterServiceImpl.softDelete() ===>");
		return response;
	}
	
	private ReligionMaster updatedValues(ReligionMaster religionMaster,ReligionMasterDTO religionDTO ) {
		ReligionMaster religion=religionMaster;
		religion.setName(religionDTO.getName());
		religion.setRemarks(religionDTO.getRemarks());
		religion.setStatus(religionDTO.getStatus());
		return religion;		
	}
}
