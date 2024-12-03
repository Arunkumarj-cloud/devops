package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.oasys.uppcl_user__master_management.dao.TermsAndConditionsDao;
import com.oasys.uppcl_user__master_management.dto.TermsAndConditionsDTO;
import com.oasys.uppcl_user__master_management.entity.TermsAndConditionsEntity;
import com.oasys.uppcl_user__master_management.repository.TermsAndConditionsRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.TermsAndConditionsService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TermsAndConditionsServiceImpl implements TermsAndConditionsService {

	@Autowired
	TermsAndConditionsDao termsAndConditionsDao;
	
	@Autowired
	TermsAndConditionsRepository termsAndConditionsRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(TermsAndConditionsDTO termsAndConditionsDTO) {
		//log.info(" <----- Terms and conditions create Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
	    TermsAndConditionsEntity entity = null;
		try {
//			if (validateTermsAndConditions(termsAndConditionsDTO.getDescription())) {
			
			     entity = termsAndConditionsDao.getOne();
			     if(entity != null)
			     {
			    	 
			    	    response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
						response.setMessage("Term & Condition currently exists in Active mode, please InActive it before creating a new Term & Condition.") ;
						 
			     }else
			     {
			    			
				    	 TermsAndConditionsEntity termsAndConditionsEntity = objectMapper.convertValue(termsAndConditionsDTO,
									TermsAndConditionsEntity.class);
							termsAndConditionsEntity = termsAndConditionsDao.save(termsAndConditionsEntity);
							if (termsAndConditionsEntity.getId() != null) {
								String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
								response.setMessage(msg) ;
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//log.info("Successfully Added");
							} else {
								String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
								response.setMessage(msg) ;
								response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
								//log.error("Failure to Add");
							}   
			     }
				
//			}
		}catch(DataIntegrityViolationException e) {
			log.error("<---- TermsAndConditionsServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}catch (Exception e) {
			log.error("<---- TermsAndConditionsServiceImpl.create() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- Terms and conditions create Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- Terms and conditions delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TermsAndConditionsEntity termsAndConditionsEntity = termsAndConditionsDao.getById(id);
			if (termsAndConditionsEntity != null) {
				termsAndConditionsDao.delete(id);
				TermsAndConditionsEntity terms = termsAndConditionsDao.getById(id);
				if (terms == null) {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg) ;
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg) ;
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.error("Unable to Delete Terms and conditions..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- Terms and conditions delete Dao END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id, TermsAndConditionsDTO termsAndConditionsDTO) {
		//log.info(" <----- TermsAndConditions update TermsAndConditions Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			TermsAndConditionsEntity termsAndConditionsEntity = termsAndConditionsDao.getById(id);
			TermsAndConditionsEntity beforeUpdate = updatedValues(termsAndConditionsEntity, termsAndConditionsDTO);
			if (termsAndConditionsEntity.getId() != null) {
				//log.info("Terms and Conditions Id {}",id);
				
				if(termsAndConditionsDTO.getStatus() == true)
				{
					if(termsAndConditionsDTO.getStatus().equals(termsAndConditionsEntity.getStatus()))
					{
						check = true;
					} /*
						 * else { termsAndConditionsEntity = termsAndConditionsDao.getOne();
						 * if(termsAndConditionsEntity == null) { check = true; }else { check = false; }
						 * }
						 */
				}else
				{
				      check = true;
				}
				
					if (check == true) {
						termsAndConditionsDao.save(beforeUpdate);
						String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg) ;
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Updated");
					}else
				{
					 response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					 String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					 response.setMessage("Term & Condition currently exists in Active mode, please InActive it before active another one") ;
				
				}
				
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		}catch(DataIntegrityViolationException e) {
			log.error("<---- TermsAndConditionsServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}catch (Exception e) {
		
			log.error("<---- TermsAndConditionsServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- TermsAndConditions update TermsAndConditions Dao END -----> ");
		return response;
	}

	
	private TermsAndConditionsEntity updatedValues(TermsAndConditionsEntity termsAndConditionsEntity,
			TermsAndConditionsDTO termsAndConditionsDTO) {
		TermsAndConditionsEntity terms = termsAndConditionsEntity;
		terms.setDescription(termsAndConditionsDTO.getDescription());
		terms.setRemarks(termsAndConditionsDTO.getRemarks());
		terms.setStatus(termsAndConditionsDTO.getStatus());
		terms.setModifiedDate(new Date());
		return terms;	
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- Terms and Conditions getById Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TermsAndConditionsEntity termsAndConditionsEntity = termsAndConditionsDao.getById(id);
			//log.info("Terms and Conditions Id {}",id);
			TermsAndConditionsDTO terms = objectMapper.convertValue(termsAndConditionsEntity, TermsAndConditionsDTO.class);
			terms.setCreatedDate(termsAndConditionsEntity.getCreatedDate());
			if (terms.getId() == null) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");

			} else {
				response.setResponseContent(terms);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Terms and Conditions..");
			}
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- Terms and Conditions getById Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- Terms and Conditions getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<TermsAndConditionsDTO> termsResponseDTO = new ArrayList<TermsAndConditionsDTO>();
		try {
			List<TermsAndConditionsEntity> terms = termsAndConditionsDao.getAll();
			for (TermsAndConditionsEntity termsList : terms) {
				TermsAndConditionsDTO termsResponse = objectMapper.convertValue(termsList, TermsAndConditionsDTO.class);
				termsResponse.setCreatedDate(termsList.getCreatedDate());
				termsResponseDTO.add(termsResponse);
			}
			if (termsResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.info("No Record Found..");
			} else {
				response.setResponseContents(termsResponseDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg) ;
				//log.info("Terms And Conditions get all Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsServiceImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- Terms and Conditions getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- Terms and Conditions getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<TermsAndConditionsDTO> termsResponseDTO = new ArrayList<TermsAndConditionsDTO>();
		try {
			Page<TermsAndConditionsEntity> termsAndConditionsEntity = termsAndConditionsDao.getLazyList(requestData);
			if (termsAndConditionsEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.info("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(termsAndConditionsEntity.getNumberOfElements());
				response.setTotalRecords(termsAndConditionsEntity.getTotalElements());
				response.setTotalPages(termsAndConditionsEntity.getTotalPages());
				for (TermsAndConditionsEntity terms : termsAndConditionsEntity) {
					TermsAndConditionsDTO termsResponse = objectMapper.convertValue(terms, TermsAndConditionsDTO.class);
					termsResponse.setCreatedDate(terms.getCreatedDate());
					termsResponseDTO.add(termsResponse);
					//log.info(" Successfully Get Terms and Conditions  Details {}");
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(termsResponseDTO);
			}
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsServiceImpl.getLazyList() ----->", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- Terms and Conditions getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- Terms and Conditions getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<TermsAndConditionsDTO> termsResponseDTO = new ArrayList<TermsAndConditionsDTO>();
		try {
			List<TermsAndConditionsEntity> terms = termsAndConditionsDao.getAllActive();
			for (TermsAndConditionsEntity termsList : terms) {
				TermsAndConditionsDTO termsResponse = objectMapper.convertValue(termsList, TermsAndConditionsDTO.class);
				termsResponse.setCreatedDate(termsList.getCreatedDate());
				termsResponseDTO.add(termsResponse);
			}
			if (termsResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(termsResponseDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg) ;
				//log.info("Successfully Get AllActive  Terms and Conditions..");
			}
		} catch (Exception e) {
			log.error("<----- TermsAndConditionsServiceImpl.getAllActive() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- Terms and Conditions getAllActive Dao END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<----- Terms and Conditions softDelete serviceImpl END ----->");
		try {
			TermsAndConditionsEntity termsAndConditionsEntity = termsAndConditionsRepository.getOne(id);
			if (termsAndConditionsEntity != null) {
				if (termsAndConditionsEntity.getStatus() == true) {
					termsAndConditionsEntity.setStatus(false);
					termsAndConditionsEntity = termsAndConditionsRepository.save(termsAndConditionsEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Terms and Conditions Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Terms and Conditions Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TermsAndConditionsServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Terms and Conditions Master Details");
		}
		//log.info("<----- Terms and Conditions softDelete serviceImpl ENDED ----->");
		return response;
	}
}
