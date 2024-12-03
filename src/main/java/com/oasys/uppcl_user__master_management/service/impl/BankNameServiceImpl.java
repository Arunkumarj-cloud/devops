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
import com.oasys.uppcl_user__master_management.dao.BankNameDao;
import com.oasys.uppcl_user__master_management.dto.BankNameDTO;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BankNameService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankNameServiceImpl implements BankNameService {
	
	@Autowired
	BankNameDao bankNameDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(BankNameDTO bankNameDTO) {
		//log.info(" <----- BankNameServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.create(bankNameDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- BankNameServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}	catch (Exception e) {
			log.error("<---- BankNameServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankNameServiceImpl.create() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id,BankNameDTO bankNameDTO) {
		//log.info(" <-----BankNameServiceImpl.update() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.update(id,bankNameDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- BankNameServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}catch (Exception e) {
			log.error("<---- BankNameServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----BankNameServiceImpl.update() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- BankNameServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.getById(id);
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- BankNameServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <-----BankNameServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.getAll();
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <-----BankNameServiceImpl.getAll() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----BankNameServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <-----BankNameServiceImpl.getAllActive() Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BankNameServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- BankNameServiceImpl.getLazyList() Service END -----> ");
		return response;
	}
	/*
	 * @Override public BaseDTO userSearchFilter(BankNameDTO bankNameDTO) {
	 * log.info(" <----- BankNameServiceImpl.getLazyList() Service STARTED -----> "); BaseDTO
	 * response = new BaseDTO(); try { response =
	 * bankNameDao.userSearchFilter(bankNameDTO); } catch (Exception e) {
	 * log.error("<---- BankNameServiceImpl.getLazyList() ----> EXCEPTION", e);
	 * response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	 * response.setMessage("Unable to getLazyList BankName"); }
	 * log.info(" <----- BankNameServiceImpl.getLazyList() Service END -----> "); return
	 * response; }
	 */
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BankNameServiceImpl.delete()Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.delete(id);
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}
		//log.info(" <----- BankNameServiceImpl.delete()Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankNameServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete BankName");
		}
		//log.info(" <----- BankNameServiceImpl.softDelete() Service END -----> ");
		return response;
	}
	/*
	@Override
	public BaseDTO getByBanktypeId(UUID id) {
		log.info(" <-----BankNameServiceImpl.getByBanktypeId() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankNameDao.getByBanktypeId(id);
		} catch (Exception e) {
			log.error("<---- BankNameServiceImpl.getByBanktypeId() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		log.info(" <-----BankNameServiceImpl.getByBanktypeId() Service END -----> ");
		return response;
	}
*/

}
