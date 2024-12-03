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
import com.oasys.uppcl_user__master_management.dao.NatureOfBusinessDao;
import com.oasys.uppcl_user__master_management.dao.StoreCategoryDao;
import com.oasys.uppcl_user__master_management.dto.StoreCategoryDTO;
import com.oasys.uppcl_user__master_management.entity.StoreCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.StoreCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.StoreCategoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StoreCategoryServiceImpl implements StoreCategoryService{

	@Autowired
	StoreCategoryDao storeCategoryDao;
	
	@Autowired
	StoreCategoryRepository storeCategoryRepository;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	NatureOfBusinessDao NatureOfBusinessDao;
	
	@Override
	public BaseDTO create(StoreCategoryDTO storeCategoryDTO) {
		//log.info(" <----- StoreCategory create Service STARTED -----> {} ", storeCategoryDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			StoreCategoryEntity storeCategoryEntity = storeCategoryDao
					.findByName(storeCategoryDTO.getStoreCategoryName());
			if (storeCategoryEntity != null) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage(storeCategoryDTO.getStoreCategoryName() + " " + message);
				//log.error(storeCategoryDTO.getStoreCategoryName() + " " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			}
			if (NatureOfBusinessDao.findById(storeCategoryDTO.getNatureOfBusinessId().getId()).isPresent()) {
				storeCategoryEntity = objectMapper.convertValue(storeCategoryDTO, StoreCategoryEntity.class);
				storeCategoryEntity = storeCategoryDao.save(storeCategoryEntity);
				if (storeCategoryEntity == null) {
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(storeCategoryDTO.getStoreCategoryName() + " " + message);
					//log.error(message + " - {} ", storeCategoryEntity);
				} else {
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(storeCategoryDTO.getStoreCategoryName() + " " + message);
					//log.info(message + " - {} ", storeCategoryEntity);
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage("NatureOfBusinessId " + message);
				//log.error(storeCategoryDTO.getNatureOfBusinessId().getId() + " " + message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				return response;
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- StoreCategoryServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.create() ----> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- StoreCategory create Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- StoreCategory getById Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = storeCategoryDao.getById(id);
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to Get StoreCategory");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- StoreCategory getById Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- StoreCategory getAll Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = storeCategoryDao.getAll();
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.getAll() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to GetAll StoreCategory");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- StoreCategory getAll Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- StoreCategory getAllActive Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = storeCategoryDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to GetAllActive StoreCategory");
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- StoreCategory getAllActive Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- StoreCategory getLazyList Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = storeCategoryDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.getLazyList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to getLazyList ServiceType");
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- StoreCategory getLazyList Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO update(UUID id, StoreCategoryDTO storeCategoryDTO) {
		//log.info(" <----- StoreCategory update Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		boolean validateStoreCategoryName = false;
		try {
			Optional<StoreCategoryEntity> natureOfBusiness = storeCategoryDao.findById(id);
			if (!natureOfBusiness.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				//log.error(message + " - {} ", id);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				return response;
			}
			if (!NatureOfBusinessDao.findById(storeCategoryDTO.getNatureOfBusinessId().getId()).isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage("NatureOfBusinessId " + message);
				//log.error(storeCategoryDTO.getNatureOfBusinessId() + " " + message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				return response;
			}
			//log.info("updateNatureOfBusiness - {} and - {} ", id, storeCategoryDTO);
			StoreCategoryEntity natureOfBusinessMaster = natureOfBusiness.get();
			natureOfBusinessMaster.setStoreCategoryName(storeCategoryDTO.getStoreCategoryName());
			natureOfBusinessMaster.setDescription(storeCategoryDTO.getDescription());
			natureOfBusinessMaster.setNatureOfBusinessId(storeCategoryDTO.getNatureOfBusinessId());
			natureOfBusinessMaster.setStatus(storeCategoryDTO.getStatus());
			List<StoreCategoryEntity> natureOfBusinessList = storeCategoryDao.findByIdNotIn(id);
			for (StoreCategoryEntity natureOfBusinessMasterEntity : natureOfBusinessList) {
				if (natureOfBusinessMasterEntity.getId() != natureOfBusinessMaster.getId()) {
					if (natureOfBusinessMasterEntity.getStoreCategoryName().equalsIgnoreCase(natureOfBusinessMaster.getStoreCategoryName())) {
						validateStoreCategoryName = true;
						break;
					} else
						validateStoreCategoryName = false;
				} else
					validateStoreCategoryName = false;
			}
			if (!validateStoreCategoryName) {
				natureOfBusinessMaster = storeCategoryDao.save(natureOfBusinessMaster);
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(storeCategoryDTO.getStoreCategoryName() + " " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(storeCategoryDTO.getStoreCategoryName() + " " + message);
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage(storeCategoryDTO.getStoreCategoryName() + " " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn(storeCategoryDTO.getStoreCategoryName() + " " + message);
			}	
		} catch(DataIntegrityViolationException e) {
			log.error("<-------- StoreCategoryServiceImpl.update() exception--------> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		}
		catch (Exception e) {
			log.error("<-------- StoreCategoryServiceImpl.update() exception--------> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- StoreCategory update Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- StoreCategory delete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = storeCategoryDao.delete(id);
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to Delete StoreCategory");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage( msg);
		}
		//log.info(" <----- StoreCategory delete Service END -----> ");
		return baseDTO;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- StoreCategory delete Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			StoreCategoryEntity storeCategoryEntity = storeCategoryRepository.getOne(id);
			if (storeCategoryEntity != null) {
				if (storeCategoryEntity.getStatus() == true) {
					storeCategoryEntity.setStatus(false);
					storeCategoryEntity = storeCategoryRepository.save(storeCategoryEntity);
					baseDTO.setMessage("Successfully Deleted");
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					baseDTO.setMessage("Unable to Delete Store Category Master Details..");
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Store Category Master Details..");
				}
			} else {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- StoreCategoryServiceImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete Store Category Master Details");
		}
		//log.info(" <----- StoreCategory delete Service ENDED -----> ");
		return baseDTO;
	}

}
