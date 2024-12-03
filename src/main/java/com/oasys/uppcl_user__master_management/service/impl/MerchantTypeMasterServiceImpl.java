package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.MerchantTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.MerchantTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.MerchantTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.MerchantTypeMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MerchantTypeMasterServiceImpl implements MerchantTypeMasterService {

	@Autowired
	MerchantTypeMasterDao merchantTypeMasterDao;
	
	@Autowired
	MerchantTypeMasterRepository merchantTypeMasterRepository;

	@Autowired
	MessageSource messageSource;
	
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MerchantTypeMasterServiceImpl.getAll()===>");
		try {
			response = merchantTypeMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended MerchantTypeMasterServiceImpl.getAll()===>");
		return response;
	}

	@Override
	public BaseDTO create(MerchantTypeMasterDTO merchantTypeDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MerchantTypeMasterServiceImpl.create() ===>");
		try {
			response = merchantTypeMasterDao.create(merchantTypeDTO);
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in MerchantTypeMasterServiceImpl.create():" + e);
		}catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in MerchantTypeMasterServiceImpl.create():" + e);
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MerchantTypeMasterServiceImpl.delete() ===>");
		try {
			response = merchantTypeMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, MerchantTypeMasterDTO merchantTypeDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MerchantTypeMasterServiceImpl.update() ===>");
		try {
			response = merchantTypeMasterDao.update(id, merchantTypeDTO);
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			
		}catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started MerchantTypeMasterServiceImpl.getById()===>");
		try {
			response = merchantTypeMasterDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started MerchantTypeMasterServiceImpl.getAllActive()===>");
		try {
			response = merchantTypeMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started MerchantTypeMasterServiceImpl.getAllLazy()===>");
		try {
			response = merchantTypeMasterDao.getLazyList(pageData);
		} catch (Exception e) {
			log.error(" Exception MerchantTypeMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.getAllLazy() ===>");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started MerchantTypeMasterServiceImpl.softDelete()===>");
		try {
			MerchantTypeMasterEntity merchantTypeMasterEntity = merchantTypeMasterRepository.getOne(id);
			if (merchantTypeMasterEntity != null) {
				if (merchantTypeMasterEntity.getStatus() == true) {
					merchantTypeMasterEntity.setStatus(false);
					merchantTypeMasterEntity = merchantTypeMasterRepository.save(merchantTypeMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Merchant Type Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Merchant Type Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- MerchantTypeMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Merchant type Master Details");
		}
		//log.info("<=== Ended MerchantTypeMasterServiceImpl.softDelete() ===>");
		return response;
	}

	@Override
	public BaseDTO getByAgentType(String agentType) {
		BaseDTO response = new BaseDTO();

		try {
			Optional<MerchantTypeMasterEntity> entity = merchantTypeMasterRepository.getByAgentType(agentType);
			if (entity.isPresent()) {
				response.setResponseContent(entity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- MerchantTypeMasterServiceImpl.getByAgentType() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to get details");
		}
		return response;
	}
	
	

}
