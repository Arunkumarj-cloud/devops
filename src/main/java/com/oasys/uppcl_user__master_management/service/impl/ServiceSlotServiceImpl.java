package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.cexception.NoRecoerdFoundException;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.ServiceSlotDao;
import com.oasys.uppcl_user__master_management.dto.ServiceSlotDTO;
import com.oasys.uppcl_user__master_management.dto.SlotsRespPojo;
import com.oasys.uppcl_user__master_management.entity.ServiceSlotEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceSlotRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.ServiceSlotService;

import lombok.extern.log4j.Log4j2;

		@Service
		@Log4j2
		public class ServiceSlotServiceImpl implements ServiceSlotService {
			
			@Autowired
			ServiceSlotDao serviceSlotDao;
			
			@Autowired
			ServiceSlotRepository serviceSlotRepository;
			
			@Autowired
			MessageSource messageSource;
			
			public BaseDTO create(ServiceSlotDTO dto) {
				//log.info(" <----- ServiceSlotServiceImpl.create() Service STARTED -----> ");
				BaseDTO baseDTO = new BaseDTO();
				try {
					baseDTO = serviceSlotDao.create(dto);
				} catch(DataIntegrityViolationException e) {
					
					log.error("<---- ServiceSlotServiceImpl.create() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}catch (Exception e) {
					log.error("<---- ServiceSlotServiceImpl.create() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					baseDTO.setMessage("Unable to Add ");
				}
				//log.info(" <----- ServiceSlotServiceImpl.create() Service END -----> ");
				return baseDTO;
			}
	
			@Override
			public BaseDTO getById(UUID id) {
				//log.info(" <----- ServiceSlotServiceImpl.getById() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = serviceSlotDao.getById(id);
				} catch (Exception e) {
					log.error("<---- SubCategoryServiceImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
				}
				//log.info(" <----- ServiceSlotServiceImpl.getById() Service END -----> ");
				return response;
			}
			
			
			@Override
			public BaseDTO delete(UUID id) {
				//log.info(" <-----ServiceSlotServiceImpl.delete() Service STARTED -----> ");
				BaseDTO baseDTO = new BaseDTO();
				try {
					baseDTO = serviceSlotDao.delete(id);
				} catch (Exception e) {
					log.error("<---- ServiceSlotServiceImpl.delete() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}
				//log.info(" <-----ServiceSlotServiceImpl.delete() Service END -----> ");
				return baseDTO;
			}
			
			@Override
			public BaseDTO update(UUID id,ServiceSlotDTO dto) {
				//log.info(" <-----ServiceSlotServiceImpl.update() Service STARTED -----> ");
				BaseDTO baseDTO = new BaseDTO();
				try {
					baseDTO = serviceSlotDao.update(id,dto);
				}catch(DataIntegrityViolationException e) {
					log.error("<---- ServiceSlotServiceImpl.update() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}catch (Exception e) {
					log.error("<---- ServiceSlotServiceImpl.update() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}
				//log.info(" <-----ServiceSlotServiceImpl.update() Service END -----> ");
				return baseDTO;
			}
			
			
			@Override
			public BaseDTO getAllActive() {
				//log.info(" <----- ServiceSlotServiceImpl.getAllActive() Service STARTED -----> ");
				BaseDTO baseDTO = new BaseDTO();
				try {
					baseDTO = serviceSlotDao.getAllActive();
				} catch (Exception e) {
					log.error("<---- ServiceSlotServiceImpl.getAllActive() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}
				//log.info(" <----- ServiceSlotServiceImpl.getAllActive() Service END -----> ");
				return baseDTO;
			}
			
			@Override
			public BaseDTO getByServiceCategoryId(UUID id) {
				//log.info(" <----- ServiceSlotServiceImpl.getByServiceCategoryId() Service STARTED -----> ");
				BaseDTO baseDTO = new BaseDTO();
				try {
					baseDTO = serviceSlotDao.getByServiceCategoryId(id);
				} catch (Exception e) {
					log.error("<---- ServiceSlotServiceImpl.getByServiceCategoryId() ----> EXCEPTION", e);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					baseDTO.setMessage(msg);
				}
				//log.info(" <----- ServiceSlotServiceImpl.getByServiceCategoryId() Service END -----> ");
				return baseDTO;
			}

			@Override
			public BaseDTO get(UUID id, Double amount) {
				
				BaseDTO baseDTO =new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
				Optional<ServiceSlotEntity>  optional=serviceSlotRepository.getSlot(Math.round(Math.ceil(amount)), id);
				if(optional.isPresent())
				{
					baseDTO.setResponseContent(optional.get());
					return baseDTO;
				}
				throw new NoRecoerdFoundException(String.format("No Service slot found for subcategoryId:{},amount:{}",id,amount)) ;
				
				
			}
			
			@Override
			public BaseDTO getSlotids(Double amount) {
				
				BaseDTO baseDTO =new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
				List<ServiceSlotEntity>  serviceSlotEntities=serviceSlotRepository.getSlot(Math.round(Math.ceil(amount)));
				if(serviceSlotEntities.isEmpty())
				{
					throw new NoRecoerdFoundException(String.format("No Service slot found for amount:{}",amount)) ;
				}

				baseDTO.setResponseContent(new SlotsRespPojo(serviceSlotEntities.stream().map(ServiceSlotEntity::getId).collect(Collectors.toList())));
				return baseDTO;
				
				
			}


	}


