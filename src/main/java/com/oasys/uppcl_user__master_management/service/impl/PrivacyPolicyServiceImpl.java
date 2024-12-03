package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.PrivacyPolicyDao;
import com.oasys.uppcl_user__master_management.dto.PrivacyPolicyDTO;
import com.oasys.uppcl_user__master_management.entity.PrivacyPolicyEntity;
import com.oasys.uppcl_user__master_management.repository.PrivacyPolicyRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.PrivacyPolicyService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2

public class PrivacyPolicyServiceImpl implements PrivacyPolicyService {

	@Autowired
	PrivacyPolicyDao privacyPolicyDao;
	@Autowired
	PrivacyPolicyRepository privacyPolicyRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(PrivacyPolicyDTO privacyPolicyDTO) {
		//log.info(" <----- Privacy Policy create Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		PrivacyPolicyEntity entity = null;
		try {
			
			
			entity = privacyPolicyDao.getOne();
		     if(entity != null)
		     {
		    	 
		    	    baseDTO.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					baseDTO.setMessage("Privacy Policy currently exists in Active mode, please InActive it before creating a new Privacy Policy.") ;
					 
		     }else
		     {
			PrivacyPolicyEntity privacyPolicyEntity = objectMapper.convertValue(privacyPolicyDTO,
					PrivacyPolicyEntity.class);
			privacyPolicyEntity = privacyPolicyDao.save(privacyPolicyEntity);
			if (privacyPolicyEntity.getId() != null) {
				String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				//log.error("Failure to Add");
			}
		     }
		} catch (DataIntegrityViolationException e) {
			log.error("<---- PrivacyPolicyServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy create Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Privacy Policy delete Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			PrivacyPolicyEntity privacyPolicyEntity = privacyPolicyDao.getById(id);
			//log.info("Privacy policy id {}", id);
			if (privacyPolicyEntity != null) {
				privacyPolicyDao.delete(id);
				PrivacyPolicyEntity privacy = privacyPolicyDao.getById(id);
				if (privacy == null) {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					baseDTO.setMessage(msg);
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,
							Locale.US);
					baseDTO.setMessage(msg);
					baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.error("Unable to Delete Privacy Policy..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy delete Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO update(UUID id, PrivacyPolicyDTO privacyPolicyDTO) {
		//log.info(" <----- update Privacy Policy Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			boolean check = false;
			PrivacyPolicyEntity privacyPolicyEntity = privacyPolicyDao.getById(id);
			//log.info("Privacy policy id {}", id);
			if (privacyPolicyEntity.getId() != null) {
				PrivacyPolicyEntity beforeUpdate = updatedValues(privacyPolicyEntity, privacyPolicyDTO);
				if (check == false) {
					beforeUpdate.setRemarks(privacyPolicyDTO.getRemarks());
				
					privacyPolicyDao.save(beforeUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					baseDTO.setMessage(msg);
					baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- PrivacyPolicyServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- Privacy Policy update Privacy Policy Dao END -----> ");
		return baseDTO;
	}

	private PrivacyPolicyEntity updatedValues(PrivacyPolicyEntity privacyPolicyEntity,
			PrivacyPolicyDTO privacyPolicyDTO) {
		PrivacyPolicyEntity privacy = privacyPolicyEntity;
		privacy.setDescription(privacyPolicyDTO.getDescription());
		privacy.setStatus(privacyPolicyDTO.getStatus());
		return privacy;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- Privacy Policy getById Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			PrivacyPolicyEntity privacyPolicyEntity = privacyPolicyDao.getById(id);
			//log.info("Privacy policy id {}", id);
			PrivacyPolicyDTO privacy = objectMapper.convertValue(privacyPolicyEntity, PrivacyPolicyDTO.class);
			privacy.setCreatedDate(privacyPolicyEntity.getCreatedDate());
			if (privacy.getId() == null) {
				baseDTO.setMessage("No Record Found..");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");

			} else {
				baseDTO.setResponseContent(privacy);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				///log.info("Successfully Get Privacy Policy..");
			}
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy getById Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Privacy Policy getAll Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		List<PrivacyPolicyDTO> privacyResponseDTO = new ArrayList<PrivacyPolicyDTO>();
		try {
			List<PrivacyPolicyEntity> privacy = privacyPolicyDao.getAll();
			for (PrivacyPolicyEntity privacyList : privacy) {
				PrivacyPolicyDTO privacyResponse = objectMapper.convertValue(privacyList, PrivacyPolicyDTO.class);
				privacyResponse.setCreatedDate(privacyList.getCreatedDate());
				privacyResponseDTO.add(privacyResponse);
			}
			if (privacyResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setResponseContents(privacyResponseDTO);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully Get All Details");
			}
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyServiceImpl.getAll() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy getAll Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Privacy Policy getLazyList Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		List<PrivacyPolicyDTO> privacyResponseDTO = new ArrayList<PrivacyPolicyDTO>();
		try {
			Page<PrivacyPolicyEntity> privacyPolicyEntity = privacyPolicyDao.getLazyList(requestData);
			if (privacyPolicyEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setNumberOfElements(privacyPolicyEntity.getNumberOfElements());
				baseDTO.setTotalRecords(privacyPolicyEntity.getTotalElements());
				baseDTO.setTotalPages(privacyPolicyEntity.getTotalPages());
				for (PrivacyPolicyEntity privacy : privacyPolicyEntity) {
					PrivacyPolicyDTO privacyResponse = objectMapper.convertValue(privacy, PrivacyPolicyDTO.class);
					privacyResponse.setCreatedDate(privacy.getCreatedDate());
					privacyResponseDTO.add(privacyResponse);
				}
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setResponseContents(privacyResponseDTO);
				//log.info("Get Details Successfully");
			}
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyServiceImpl.getLazyList() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy getLazyList Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Privacy Policy getAllActive Dao STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		List<PrivacyPolicyDTO> privacyResponseDTO = new ArrayList<PrivacyPolicyDTO>();
		try {
			List<PrivacyPolicyEntity> privacy = privacyPolicyDao.getAllActive();
			for (PrivacyPolicyEntity privacyList : privacy) {
				PrivacyPolicyDTO privacyResponse = objectMapper.convertValue(privacyList, PrivacyPolicyDTO.class);
				privacyResponse.setCreatedDate(privacyList.getCreatedDate());
				privacyResponseDTO.add(privacyResponse);
			}
			if (privacyResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				baseDTO.setResponseContents(privacyResponseDTO);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				baseDTO.setMessage(msg);
				//log.info("Successfully Get AllActive Privacy Policy..");
			}
		} catch (Exception e) {
			log.error("<----- PrivacyPolicyServiceImpl.getAllActive() ----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Privacy Policy getAllActive Dao END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started PrivacyPolicyServiceImpl.softDelete()===>");
		try {
			PrivacyPolicyEntity privacyPolicyEntity = privacyPolicyRepository.getOne(id);
			if (privacyPolicyEntity != null) {
				if (privacyPolicyEntity.getStatus() == true) {
					privacyPolicyEntity.setStatus(false);
					privacyPolicyEntity = privacyPolicyRepository.save(privacyPolicyEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Privacy Policy Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Privacy Policy Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- PrivacyPolicyServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete  Privacy Policy Master Details");
		}
		//log.info("<=== Ended PrivacyPolicyServiceImpl.softDelete() ===>");
		return response;
	}

}
