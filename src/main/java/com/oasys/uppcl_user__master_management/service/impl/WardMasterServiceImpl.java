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
import com.oasys.uppcl_user__master_management.dao.MuncipalityDao;
import com.oasys.uppcl_user__master_management.dao.WardMasterDao;
import com.oasys.uppcl_user__master_management.dto.WardMasterDTO;
import com.oasys.uppcl_user__master_management.entity.WardMasterEntity;
import com.oasys.uppcl_user__master_management.repository.WardMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.WardMasterService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class WardMasterServiceImpl implements WardMasterService {

	@Autowired
	WardMasterDao wardMasterDao;

	@Autowired
	WardMasterRepository wardMasterRepository;
	
	@Autowired
	ObjectMapper objectMapper;
		
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	MuncipalityDao muncipalityDao;
	
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started WardMasterServiceImpl.getAll()===>");
		try {
			response = wardMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("data doesn't exist");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		return response;
	}

	@Override
	public BaseDTO create(WardMasterDTO wardMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started WardMasterServiceImpl.create() - {} ",wardMasterDTO);
		String message = "";
		try {
			WardMasterEntity wardMasterEntity = wardMasterDao.findByWardName(wardMasterDTO.getWardName());
			if (wardMasterEntity == null) {
				if (muncipalityDao.getMuncipalityById(wardMasterDTO.getMunicipalitynameId().getId())
						.isPresent()) {
				wardMasterEntity = objectMapper.convertValue(wardMasterDTO, WardMasterEntity.class);
				wardMasterEntity = wardMasterDao.save(wardMasterEntity);
				}else {
					message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.warn("Municipality Name Not Found - {} ",wardMasterDTO.getMunicipalitynameId().getId());
				}
				if (wardMasterEntity == null) {
					//log.error("Ward Failed Added - {} ", wardMasterEntity);
					message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				} else {
					//log.info("Ward Successfully Added - {} ", wardMasterEntity);
					message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				}
			} else {
				//log.error("Ward Name is already Exist");
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			}
		} catch (DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in WardMasterServiceImpl.create(): - {} " , e);
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in WardMasterServiceImpl.create(): - {} " , e);
		}
		//log.info("<=== Ended WardMasterServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started WardMasterServiceImpl.delete() ===>");
		try {
			response = wardMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended WardMasterServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, WardMasterDTO wardMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started WardMasterServiceImpl.update() ===>");
		boolean validateWardName = false;
		String message = "";
		try {
			Optional<WardMasterEntity> wardMaster = wardMasterDao.findById(id);
			if (wardMaster.isPresent()) {
				if (!muncipalityDao.getMuncipalityById(wardMasterDTO.getMunicipalitynameId().getId()).isPresent()) {
					message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
					//log.warn("Municipality Name Not Found - {} ", wardMasterDTO.getMunicipalitynameId().getId());
					return response;
				}
				//log.info("Ward id - {} and Details - {} ", id, wardMasterDTO);
				WardMasterEntity updateWardMaster = updatedValues(wardMaster.get(), wardMasterDTO);
				List<WardMasterEntity> exceptWardMasterEntity = wardMasterDao.findByIdNotIn(id);
				for (WardMasterEntity wardMasterEntity : exceptWardMasterEntity) {
					if (wardMasterEntity.getId() != updateWardMaster.getId()) {
						if (wardMasterEntity.getWardName().equalsIgnoreCase(updateWardMaster.getWardName())) {
							validateWardName = true;
							break;
						} else 
							validateWardName = false;
					} else 
						validateWardName = false;
				}
				if (!validateWardName) {
					updateWardMaster = wardMasterDao.save(updateWardMaster);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(wardMasterDTO.getWardName() + " " + message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info(wardMasterDTO.getWardName() + " " + message);
				} else {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage(wardMasterDTO.getWardName() + " " + message);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error(wardMasterDTO.getWardName() + " " + message);
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error(" Exception WardMasterServiceImpl.update() - {} " , e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.update() - {} " , e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("<=== Ended WardMasterServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info(" Started WardMasterServiceImpl.getById() - {} ", id);
		WardMasterDTO wardMasterDTO = new WardMasterDTO();
		String message = "";
		try {
			Optional<WardMasterEntity> wardMasterEntity = wardMasterDao.findById(id);	
			if (wardMasterEntity.isPresent()) {
				wardMasterDTO = objectMapper.convertValue(wardMasterEntity, WardMasterDTO.class);
				response.setResponseContent(wardMasterDTO);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details  - {} ", wardMasterDTO);
			}else {
				//log.error("ward does not Exist - {} ",id);
				 message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.getById() - {} ", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("<=== Ended WardMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started WardMasterServiceImpl.getAllActive()===>");
		try {
			response = wardMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended WardMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started WardMasterServiceImpl.getLazyList()===>");
		try {
			response = wardMasterDao.getLazyList(pageData);
		} catch (Exception e) {
			log.error(" Exception WardMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended WardMasterServiceImpl.getLazyList() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started WardMasterServiceImpl.softDelete()===>");
		try {
			WardMasterEntity wardMasterEntity = wardMasterRepository.getOne(id);
			if (wardMasterEntity != null) {
				if (wardMasterEntity.getStatus() == true) {
					wardMasterEntity.setStatus(false);
					wardMasterEntity = wardMasterRepository.save(wardMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Ward Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Ward Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- WardMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Ward Master Details");
		}
		//log.info("<=== Ended WardMasterServiceImpl.softDelete() ===>");
		return response;
	}
	
	private WardMasterEntity updatedValues(WardMasterEntity wardMaster,WardMasterDTO wardMasterDTO) {
		WardMasterEntity wardMasterEntity = wardMaster;
		wardMasterEntity.setWardName(wardMasterDTO.getWardName());
		wardMasterEntity.setStatus(wardMasterDTO.getStatus());
		wardMasterEntity.setMunicipalitynameId(wardMasterDTO.getMunicipalitynameId());
		return wardMasterEntity;
	}
}
