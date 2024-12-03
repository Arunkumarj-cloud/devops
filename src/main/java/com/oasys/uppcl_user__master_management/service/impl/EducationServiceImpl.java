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
import com.oasys.uppcl_user__master_management.dao.EducationDao;
import com.oasys.uppcl_user__master_management.dto.EducationDTO;
import com.oasys.uppcl_user__master_management.entity.EducationEntity;
import com.oasys.uppcl_user__master_management.repository.EducationRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.EducationService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EducationServiceImpl implements EducationService{
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	EducationDao educationDao;
	
	@Autowired
	EducationRepository educationRepository;
	
	public BaseDTO create(EducationDTO educationDTO) {
		//log.info(" <----- EducationServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = educationDao.create(educationDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- EducationServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		
		}	catch (Exception e) {
			log.error("<---- EducationServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- EducationServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <-----EducationServiceImpl.getAllActive() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = educationDao.getAllActive();
				} catch (Exception e) {
					log.error("<---- EducationServiceImpl.getAllActive() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
				//log.info(" <-----EducationServiceImpl.getAllActive() Service END -----> ");
				return response;
			}
			

	@Override
	public BaseDTO update(UUID id, EducationDTO educationDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started EducationServiceImpl.update() ===>");
		try {
			response = educationDao.update(id, educationDTO);
			
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception EducationServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error(" Exception EducationServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended EducationServiceImpl.update() ===>");
		return response;
	}
	@Override
		public BaseDTO getLazyList(PaginationRequestDTO requestData) {
			//log.info(" <----- EducationServiceImpl.getLazyList() Service STARTED -----> ");
			BaseDTO response = new BaseDTO();
			try {
				response = educationDao.getLazyList(requestData);
			} catch (Exception e) {
				log.error("<---- EducationServiceImpl.getLazyList()-{}", e.getMessage());
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
			}
			//log.info(" <----- EducationServiceImpl.getLazyList() Service END -----> ");
			return response;
		}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- EducationServiceImpl.getById() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = educationDao.getById(id);
				} catch (Exception e) {
					log.error("<---- EducationServiceImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
				//log.info(" <----- EducationServiceImpl.getById() Service END -----> ");
				return response;
			}
	@Override
	public BaseDTO getAll() {
		//log.info(" <-----EducationServiceImpl.getAll() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = educationDao.getAll();
				} catch (Exception e) {
					log.error("<---- EducationServiceImpl.getAll() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				
				}
				//log.info(" <-----EducationServiceImpl.getAll() Service END -----> ");
				return response;
			}
			

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started EducationServiceImpl.softDelete()===>");
		try {
			EducationEntity educationEntity = educationRepository.getOne(id);
			if (educationEntity != null) {
				if (educationEntity.getStatus() == true) {
					educationEntity.setStatus(false);
					educationEntity = educationRepository.save(educationEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					///log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete ");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- EducationServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete");
		}
		//log.info("<=== Ended EducationServiceImpl.softDelete() ===>");
		return response;
	}
	

}
