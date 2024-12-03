package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.LanguageDao;
import com.oasys.uppcl_user__master_management.dto.LanguageDTO;
import com.oasys.uppcl_user__master_management.entity.LanguageMasterEntity;
import com.oasys.uppcl_user__master_management.repository.LanguageRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class LanguageDaoImpl implements LanguageDao {

	@Autowired
	LanguageRepository languageRepository;
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(LanguageDTO languageDTO) {
		//log.info(" <----- LanguageDaoImpl.create() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			LanguageMasterEntity Language = objectMapper.convertValue(languageDTO, LanguageMasterEntity.class);
			languageRepository.save(Language);
			if (null != languageDTO) {
				languageRepository.save(Language);
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added - {} ,- {}",languageDTO.getLanguageName(),languageDTO.getCode());
			}  else {
				String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage("This Language Already Exist.");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("Language Code - {} Already Exist..",languageDTO.getCode());
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- LanguageDaoImpl.create() ----> EXCEPTION", e);
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- LanguageDaoImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- LanguageDaoImpl.create() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, LanguageDTO languageDTO) {
		//log.info(" <----- LanguageDaoImpl.update() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {

			boolean check = false;
			Optional<LanguageMasterEntity> optional = languageRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Langyuage Id {}", id);
				LanguageMasterEntity language = new LanguageMasterEntity();
				language = optional.get();
				List<LanguageMasterEntity> checkCode = languageRepository.checkCode(languageDTO.getCode());
				for (LanguageMasterEntity entity : checkCode) {
					if (entity.getCode().equals(languageDTO.getCode())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else			
						check = true;
					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage("This Language Code " + msg);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					log.warn("Language Code Already Exists");
				}else {
				
					language.setCode(languageDTO.getCode());
					language.setLanguageName(languageDTO.getLanguageName());
					language.setStatus(languageDTO.getStatus());
					LanguageMasterEntity afterUpdate = languageRepository.save(language);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(languageDTO.getCode() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Language - {} Updated Successfully",  languageDTO.getCode(),languageDTO.getLanguageName());
				}
				
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- LanguageDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}catch (Exception e) {
			log.error("<---- LanguageDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- LanguageDaoImpl.update() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- LanguageDaoImpl.getById() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<LanguageMasterEntity> language = languageRepository.findById(id);
			if (language.isPresent()) {
				//log.info("Language Id {}", id);
				response.setResponseContent(language);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Language Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- LanguageDaoImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- LanguageDaoImpl.getById() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- LanguageDaoImpl.getAll() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<LanguageMasterEntity> language = languageRepository.findAll();

			if (language.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				log.error("No Record Found..");
			} else {
				response.setResponseContents(language);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get all Language Details");
			}
		} catch (Exception e) {
			log.error("<----- LanguageDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- LanguageDaoImpl.getAll() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- LanguageDaoImpl.getAllActive() Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<LanguageMasterEntity> language = languageRepository.getAllActive();

			if (language.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			 log.error("No Record Found..");
			} else {
				response.setResponseContents(language);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get AllActive  Language Details..");
			}
		} catch (Exception e) {
			log.error("<----- LanguageDaoImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- LanguageDaoImpl.getAllActive() Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Language getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<LanguageMasterEntity> contentList = new ArrayList<LanguageMasterEntity>();
		Page<LanguageMasterEntity> languageList = null;
		PageRequest pageRequest;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					languageList = languageRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					languageList = languageRepository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					languageList = languageRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					languageList = languageRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (languageList.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				response.setNumberOfElements(languageList.getNumberOfElements());
				response.setTotalRecords(languageList.getTotalElements());
				response.setTotalPages(languageList.getTotalPages());
				for (LanguageMasterEntity language : languageList) {
					contentList.add(language);
				}
				response.setResponseContents(contentList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info(" Successfully Get LazyList for Language Details.. ");
			}
		} catch (Exception e) {
			log.error("<----- LanguageDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Language delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<LanguageMasterEntity> language = languageRepository.findById(id);
			if (language != null) {
				languageRepository.deleteById(id);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- LanguageDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- Language delete Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- Language softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<LanguageMasterEntity> language = languageRepository.findById(id);
			if (language.isPresent()) {
				LanguageMasterEntity beforeUpdate = language.get();
				beforeUpdate.setStatus(false);
				languageRepository.save(beforeUpdate);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- LanguageServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Language");
		}
		//log.info(" <----- Language softDelete Service END -----> ");
		return response;
	}

	private LanguageMasterEntity updatedValues(LanguageMasterEntity languageMaster, LanguageDTO languageDTO) {
		LanguageMasterEntity language = languageMaster;
		language.setCode(languageDTO.getCode());
		language.setLanguageName(languageDTO.getLanguageName());
		language.setStatus(languageDTO.getStatus());
		return language;
	}

}
