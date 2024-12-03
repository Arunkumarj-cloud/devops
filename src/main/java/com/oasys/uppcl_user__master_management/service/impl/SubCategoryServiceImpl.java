package com.oasys.uppcl_user__master_management.service.impl;



import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dao.SubCategoryDao;
import com.oasys.uppcl_user__master_management.dto.SearchDTO;
import com.oasys.uppcl_user__master_management.dto.SubCategoryDTO;
import com.oasys.uppcl_user__master_management.repository.SubCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.SubCategoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	SubCategoryDao subCategoryDao;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	@Autowired
	MessageSource messageSource;

	public BaseDTO create(SubCategoryDTO dto) {
		// log.info(" <----- SubCategoryServiceImpl.create() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.create(dto);
		} catch (DataIntegrityViolationException e) {

			log.error("<---- SubCategoryServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Add ");
		}
		// log.info(" <----- SubCategoryServiceImpl.create() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		// log.info(" <-----SubCategoryServiceImpl.delete() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.delete(id);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----SubCategoryServiceImpl.delete() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO update(UUID id, SubCategoryDTO dto) {
		// log.info(" <-----SubCategoryServiceImpl.update() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.update(id, dto);
		} catch (DataIntegrityViolationException e) {
			log.error("<---- SubCategoryServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <-----SubCategoryServiceImpl.update() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByServiceCategoryId(UUID id) {
		// log.info(" <----- SubCategoryServiceImpl.getByServiceCategoryId() Service
		// STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.getByServiceCategoryId(id);
		} catch (Exception e) {
			log.error("<---- TalukMasterServiceImpl.getByServiceCategoryId() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryServiceImpl.getByServiceCategoryId() Service END
		// -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getById(UUID id) {
		// log.info(" <----- SubCategoryServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = subCategoryDao.getById(id);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- SubCategoryServiceImpl.getById() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		// log.info(" <----- SubCategoryServiceImpl.getAllActive() Service STARTED
		// -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryServiceImpl.getAllActive() Service END ----->
		// ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActiveWithSearch(SearchDTO searchDTO) {
		// log.info(" <----- SubCategoryServiceImpl.getAllActive() Service STARTED
		// -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = subCategoryDao.getAllActiveWithSearch(searchDTO);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		// log.info(" <----- SubCategoryServiceImpl.getAllActive() Service END ----->
		// ");
		return baseDTO;
	}

	@Override
	public BaseDTO getByServiceCategoryByName(String name) {
		// log.info(" <----- SubCategoryServiceImpl.getById() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = subCategoryDao.getByServiceCategoryByName(name);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- SubCategoryServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	
	@Override
	public BaseDTO getByIds(List<UUID> ids) {
		BaseDTO baseDTO=new BaseDTO(ResponseMessageConstant.SUCCESS_RESPONSE);
		baseDTO.setResponseContents(subCategoryRepository.findAllById(ids));
		return baseDTO;
	}

	@Override
	public BaseDTO getByName(String name) {
		// log.info(" <----- SubCategoryServiceImpl.getById() Service STARTED -----> ");
				BaseDTO response = new BaseDTO();
				try {
					response = subCategoryDao.getByName(name);
				} catch (Exception e) {
					log.error("<---- SubCategoryServiceImpl.getById() ----> EXCEPTION", e);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
					response.setMessage(msg);

				}
				// log.info(" <----- SubCategoryServiceImpl.getById() Service END -----> ");
				return response;
	}

	@Override
	public BaseDTO getByServiceAndSubcategory(String serviceName, String categoryName) {
		// TODO Auto-generated method stub
		BaseDTO response = new BaseDTO();
		try {
			response = subCategoryDao.getByServiceAndSubcategory(serviceName,categoryName);
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);

		}
		// log.info(" <----- SubCategoryServiceImpl.getById() Service END -----> ");
		return response;
	}
	
	public BaseDTO getAllOrderByModifiedDate() {	
		BaseDTO response = new BaseDTO();
		try {
			response = subCategoryDao.getAllOrderByModifiedDate();
		} catch (Exception e) {
			log.error("<---- SubCategoryServiceImpl.getAllOrderByModifiedDate() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);

		}
		
		return response;
	}

}
