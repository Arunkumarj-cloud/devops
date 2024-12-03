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
import com.oasys.uppcl_user__master_management.dao.BankPassbookMasterDao;
import com.oasys.uppcl_user__master_management.dto.BankPassbookMasterDTO;
import com.oasys.uppcl_user__master_management.entity.BankPassbookMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankPassbookMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BankPassbookMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankPassbookMasterServiceImpl implements BankPassbookMasterService  {

	@Autowired
	BankPassbookMasterDao bankPassbookMasterDao;
	
	@Autowired
	BankPassbookMasterRepository bankPassbookMasterRepository;

	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankPassbookMasterServiceImpl.getAll()===>");
		try {
			response = bankPassbookMasterDao.getAll();
		} catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended BankPassbookMasterServiceImpl.getAll()===>");
		return response;
	}

	@Override
	public BaseDTO create(BankPassbookMasterDTO bankPassbookMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankPassbookMasterServiceImpl.create() ===>");
		try {
			response = bankPassbookMasterDao.create(bankPassbookMasterDTO);
			
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankPassbookMasterServiceImpl.create():" + e);
			
		}catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankPassbookMasterServiceImpl.create():" + e);
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, BankPassbookMasterDTO bankPassbookMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankPassbookMasterServiceImpl.update() ===>");
		try {
			response = bankPassbookMasterDao.update(id, bankPassbookMasterDTO);
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception BankPassbookMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankPassbookMasterServiceImpl.getById()===>");
		try {
			response = bankPassbookMasterDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankPassbookMasterServiceImpl.delete() ===>");
		try {
			response = bankPassbookMasterDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankPassbookMasterServiceImpl.getAllActive() ===>");
		try {
			response = bankPassbookMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllLazy(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankPassbookMasterServiceImpl.getAllLazy()===>");
		try {
			response = bankPassbookMasterDao.getAllLazy(pageData);
		} catch (Exception e) {
			log.error(" Exception BankPassbookMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.getAllLazy() ===>");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		///log.info("<=== Started BankPassbookMasterServiceImpl.softDelete()===>");
		try {
			BankPassbookMasterEntity bankPassbookMasterEntity = bankPassbookMasterRepository.getOne(id);
			if (bankPassbookMasterEntity != null) {
				if (bankPassbookMasterEntity.getStatus() == true) {
					bankPassbookMasterEntity.setStatus(false);
					bankPassbookMasterEntity = bankPassbookMasterRepository.save(bankPassbookMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Bank PassBook Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Bank PassBook Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankPassbookMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank PassBook Master Details");
		}
		//log.info("<=== Ended BankPassbookMasterServiceImpl.softDelete() ===>");
		return response;
	}

}
