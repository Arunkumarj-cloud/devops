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
import com.oasys.uppcl_user__master_management.dao.FaqCategoryDao;
import com.oasys.uppcl_user__master_management.dto.FaqCategoryDTO;
import com.oasys.uppcl_user__master_management.entity.FaqCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.FaqCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.FaqCategoryService;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2

public class FaqCategoryServiceImpl implements FaqCategoryService{
	
	@Autowired
	FaqCategoryDao faqCategoryDao;
	
	@Autowired
	FaqCategoryRepository faqCategoryRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(FaqCategoryDTO faqCategoryDTO) {
		//log.info(" <----- FaqCategoryServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.create(faqCategoryDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqCategoryServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Create Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Create Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FaqCategoryServiceImpl.create() Service END -----> ");
		return response;
	}
	@Override
	public BaseDTO update(UUID id, FaqCategoryDTO faqCategoryDTO) {
		//log.info(" <----- FaqCategoryServiceImpl.update()  Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.update(id, faqCategoryDTO);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- FaqCategoryServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Update Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Update Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FaqCategoryServiceImpl.update()  Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getByID(UUID id) {
		//log.info(" <----- FaqCategoryServiceImpl.getByID() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.getByID(id);
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.getByID() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get Faq Category By ID");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FaqCategoryServiceImpl.getByID() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <-----FaqCategoryServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.getAll();
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Get All Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----FaqCategoryServiceImpl.getAll() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- FaqCategory delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.delete(id);
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to delete Faq Category By ID");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- FaqCategory delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----FaqCategoryServiceImpl.getAll()Active Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to getAllActive Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----FaqCategoryServiceImpl.getAll()Active Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO lazylist(PaginationRequestDTO paginationRequestDTO) {
		//log.info(" <-----FaqCategoryServiceImpl.lazylist() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = faqCategoryDao.lazylist(paginationRequestDTO);
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.lazylist() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to lazylist Faq Category");
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <-----FaqCategoryServiceImpl.lazylist() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started FaqCategoryServiceImpl.softDelete()===>");
		try {
			FaqCategoryEntity faqCategoryEntity = faqCategoryRepository.getOne(id);
			if (faqCategoryEntity != null) {
				if (faqCategoryEntity.getStatus() == true) {
					faqCategoryEntity.setStatus(false);
					faqCategoryEntity = faqCategoryRepository.save(faqCategoryEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Faq Category Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Faq Category Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- FaqCategoryServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Faq Category Master Details");
		}
		//log.info("<=== Ended FaqCategoryServiceImpl.softDelete() ===>");
		return response;
	}
	
}


	