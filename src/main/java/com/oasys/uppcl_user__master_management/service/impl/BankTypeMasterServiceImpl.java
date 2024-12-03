package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BankTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.BankTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.BankTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BankTypeMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankTypeMasterServiceImpl implements BankTypeMasterService {
	@Autowired
	BankTypeMasterDao bankTypeMasterDao;
	
	@Autowired
	BankTypeMasterRepository bankTypeMasterRepository;

	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankTypeMasterServiceImpl.getAll()===>");
		try {
			response = bankTypeMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);	
		}
		//log.info("<===Ended BankTypeMasterServiceImpl.getAll()===>");
		return response;
	}

	@Override
	public BaseDTO create(BankTypeMasterDTO bankTypeDto) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankTypeMasterServiceImpl.create() ===>");
		try {
			response = bankTypeMasterDao.create(bankTypeDto);
		} catch (DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankTypeMasterServiceImpl.create():" + e);
		} catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankTypeMasterServiceImpl.create():" + e);
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankTypeMasterServiceImpl.delete() ===>");
		try {
			response = bankTypeMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, BankTypeMasterDTO bankTypeDto) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankTypeMasterServiceImpl.update() ===>");
		try {
			response = bankTypeMasterDao.update(id, bankTypeDto);
		} catch (DataIntegrityViolationException e) {
			log.error(" Exception BankTypeMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);

		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankTypeMasterServiceImpl.getById()===>");
		try {
			response = bankTypeMasterDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankTypeMasterServiceImpl.getAllActive()===>");
		try {
			response = bankTypeMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllLazy(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankTypeMasterServiceImpl.getAllLazy()===>");
		try {
			response = bankTypeMasterDao.getAllLazy(pageData);
		} catch (Exception e) {
			log.error(" Exception BankTypeMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.getAllLazy() ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankTypeMasterServiceImpl.softDelete()===>");
		try {
			BankTypeMasterEntity bankTypeMasterEntity = bankTypeMasterRepository.getOne(id);
			if (bankTypeMasterEntity != null) {
				if (bankTypeMasterEntity.getStatus() == true) {
					bankTypeMasterEntity.setStatus(false);
					bankTypeMasterEntity = bankTypeMasterRepository.save(bankTypeMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Bank Type Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Bank Type Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankTypeMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank Type Master Details");
		}
		//log.info("<=== Ended BankTypeMasterServiceImpl.softDelete() ===>");
		return response;
	}

}
