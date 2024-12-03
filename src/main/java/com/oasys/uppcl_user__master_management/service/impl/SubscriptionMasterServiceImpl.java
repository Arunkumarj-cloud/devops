package com.oasys.uppcl_user__master_management.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.SubscriptionMasterDao;
import com.oasys.uppcl_user__master_management.dto.SubscriptionMasterDTO;
import com.oasys.uppcl_user__master_management.entity.SubscriptionMasterEntity;
import com.oasys.uppcl_user__master_management.repository.SubscriptionMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.SubscriptionMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SubscriptionMasterServiceImpl implements SubscriptionMasterService {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	SubscriptionMasterDao subscriptionMasterDao;
	
	
	@Autowired
	SubscriptionMasterRepository repository;

	@Autowired
	MessageSource messageSource;
	
	public BaseDTO create(SubscriptionMasterDTO subscriptionDTO) {
		//log.info(" <----- SubscriptionMaster create Service STARTED -----> {} ",subscriptionDTO);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			if (validateSubscriptionName(subscriptionDTO.getSubscriptionName())) {
				//log.info(" Subscription name  - Is Validate {}..",subscriptionDTO.getSubscriptionName());
				if(subscriptionDTO.getIsCustom()!=null || subscriptionDTO.getIsDefault()!=null) {
				if (validateIsDefault() || subscriptionDTO.getIsDefault() ==false ) {
				
					SubscriptionMasterEntity subscription = objectMapper.convertValue(subscriptionDTO,
							SubscriptionMasterEntity.class);
					subscription = subscriptionMasterDao.save(subscription);
					if (subscription.getId() != null) {
						 message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(subscriptionDTO.getSubscriptionName() + " " + message);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Added");
					} else {
						 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage( message);
						response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
						//log.error("Failure to Add");
					}
					
				} else {
					 message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage("Default Subscription is  " + message) ;
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error("Default Subscription is already exists..");
				}
				}else {
					response.setMessage("please select Custom are Default");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				}
			} else {
				 message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage(subscriptionDTO.getSubscriptionName() + " " + message) ;
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.error("SubscriptionMaster Name is already exists..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- SubscriptionMasterServiceImpl.create() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message) ;
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.create() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message) ;
		}
		//log.info(" <----- SubscriptionMaster create Service END -----> ");
		return response;
	}

	public BaseDTO getById(UUID id) {
		//log.info(" <----- SubscriptionMaster getById Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubscriptionMasterEntity subscriptionMasterEntity = subscriptionMasterDao.getById(id);
			SubscriptionMasterDTO subscriptionMaster = objectMapper.convertValue(subscriptionMasterEntity,
					SubscriptionMasterDTO.class);
			if (subscriptionMaster.getId() == null) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");

			} else {
				response.setResponseContent(subscriptionMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg) ;
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get SubscriptionMaster Details..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.getById() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg) ;
		}
		//log.info(" <----- SubscriptionMaster getById Service END -----> ");
		return response;
	}

	public BaseDTO getAll() {
		//log.info(" <----- SubscriptionMaster getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<SubscriptionMasterDTO> subscriptionMasterDTO = new ArrayList<SubscriptionMasterDTO>();
		try {
			List<SubscriptionMasterEntity> subscriptionMasterEntity = subscriptionMasterDao.getAll();
			for (SubscriptionMasterEntity subscription : subscriptionMasterEntity) {
				SubscriptionMasterDTO subscriptionMaster = objectMapper.convertValue(subscription,
						SubscriptionMasterDTO.class);
				subscriptionMasterDTO.add(subscriptionMaster);
			}
			if (subscriptionMasterDTO.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContent(subscriptionMasterDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get All  SubscriptionMaster Details..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg) ;
		}
		//log.info(" <----- SubscriptionMaster getAll Service END -----> ");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info(" <----- SubscriptionMaster getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<SubscriptionMasterDTO> subscriptionMasterDTO = new ArrayList<SubscriptionMasterDTO>();
		try {
			List<SubscriptionMasterEntity> subscriptionMasterEntity = subscriptionMasterDao.getAllActive();
			for (SubscriptionMasterEntity subscription : subscriptionMasterEntity) {
				SubscriptionMasterDTO subscriptionMaster = objectMapper.convertValue(subscription,
						SubscriptionMasterDTO.class);
				subscriptionMasterDTO.add(subscriptionMaster);
			}
			if (subscriptionMasterDTO.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContent(subscriptionMasterDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get All Active SubscriptionMaster Details..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- SubscriptionMaster getAllActive Service END -----> ");
		return response;

	}

	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- SubscriptionMaster getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<SubscriptionMasterDTO> subscriptionMasterDTO = new ArrayList<SubscriptionMasterDTO>();
		try {
			Page<SubscriptionMasterEntity> subscriptionMasterEntity = subscriptionMasterDao.getLazyList(requestData);
			if (subscriptionMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage( msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(subscriptionMasterEntity.getNumberOfElements());
				response.setTotalRecords(subscriptionMasterEntity.getTotalElements());
				response.setTotalPages(subscriptionMasterEntity.getTotalPages());
				for (SubscriptionMasterEntity subscriptionMaster : subscriptionMasterEntity) {
					SubscriptionMasterDTO SubscriptionMasterResponse = objectMapper.convertValue(subscriptionMaster,
							SubscriptionMasterDTO.class);
					subscriptionMasterDTO.add(SubscriptionMasterResponse);
					//log.info("Successfully Get lazy list details");
				}
				response.setResponseContents(subscriptionMasterDTO);
			}
		} catch (Exception e) {
			log.error("<----- SubscriptionMasterServiceImpl.getLazyList() ----->", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- SubscriptionMaster getLazyList Service END -----> ");
		return response;
	}

	public BaseDTO update(UUID id, SubscriptionMasterDTO subscriptionMasterDTO) {
		//log.info(" <----- SubscriptionMaster update Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			SubscriptionMasterEntity subscriptionMasterEntity = subscriptionMasterDao.getById(id);
			if (subscriptionMasterEntity != null) {
				
				List<SubscriptionMasterEntity> except = subscriptionMasterDao.check(subscriptionMasterDTO.getSubscriptionName());
				
				if(except.isEmpty()) {
					check = false;
				}
				else {
					if(id.toString().equalsIgnoreCase(except.get(0).getId().toString())) {
						check = false;
					}
					else {
						check = true;
					}
				}
				
				
				if(check == false) {
					
					if(subscriptionMasterDTO.getIsDefault() == true) {
						List<SubscriptionMasterEntity> list = subscriptionMasterDao.getDefault();
						if(!list.isEmpty()) {
							if(id.toString().equalsIgnoreCase(list.get(0).getId().toString())){
								
								SubscriptionMasterEntity beforeUpdate = updatedValues(subscriptionMasterEntity, subscriptionMasterDTO);
								subscriptionMasterEntity = subscriptionMasterDao.save(beforeUpdate);
								String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
								response.setMessage(subscriptionMasterDTO.getSubscriptionName() + " " + msg) ;
								response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
								//log.info("Successfully Updated");
							}
							else {
								response.setMessage("Default Subscription is already exists..");
								response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								//log.error("Default Subscription is already exists..");
							}
						}else {
							SubscriptionMasterEntity beforeUpdate = updatedValues(subscriptionMasterEntity, subscriptionMasterDTO);
							subscriptionMasterEntity = subscriptionMasterDao.save(beforeUpdate);
							String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
							response.setMessage(subscriptionMasterDTO.getSubscriptionName() + " " + msg) ;
							response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							//log.info("Successfully Updated");
						}
						
						
					}
					else {
						
						SubscriptionMasterEntity beforeUpdate = updatedValues(subscriptionMasterEntity, subscriptionMasterDTO);
						subscriptionMasterEntity = subscriptionMasterDao.save(beforeUpdate);
						String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(subscriptionMasterDTO.getSubscriptionName() + " " + msg) ;
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Updated");
						
					}
					
					
				}else {
					response.setMessage("Name is already exists..");
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error("Name is already exists..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}

		}catch(DataIntegrityViolationException e) {
			log.error("<---- SubscriptionMasterServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}	catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- SubscriptionMaster update Service END -----> ");
		return response;
	}

	public BaseDTO delete(UUID id) {
		//log.info(" <----- SubscriptionMaster delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubscriptionMasterEntity subscription = subscriptionMasterDao.getById(id);
			if (subscription != null) {
				subscription = subscriptionMasterDao.delete(id);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg) ;
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg) ;
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg) ;
		}
		//log.info(" <----- SubscriptionMaster delete Service END -----> ");
		return response;
	}

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- SubscriptionMaster softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubscriptionMasterEntity subscription = subscriptionMasterDao.getById(id);
			if (subscription != null) {
				subscription.setIsActive(false);
				subscription = subscriptionMasterDao.save(subscription);

				if (subscription.getIsActive() == false) {
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete SubscriptionMaster..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.error("Unable to Delete SubscriptionMaster..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete SubscriptionMaster");
		}
		//log.info(" <----- SubscriptionMaster softDelete Service END -----> ");
		return response;
	}
	
	public BaseDTO getDefault() {
		//log.info(" <----- SubscriptionMaster getDefault Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			SubscriptionMasterEntity subscriptionMasterEntity = subscriptionMasterDao.validateIsDefault();
			SubscriptionMasterDTO subscriptionMaster = objectMapper.convertValue(subscriptionMasterEntity,
					SubscriptionMasterDTO.class);
			if (subscriptionMaster.getId() == null) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.info("No Record Found..");

			} else {
				response.setResponseContent(subscriptionMaster);
				response.setMessage("Successfully Get SubscriptionMaster Details..");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get SubscriptionMaster Details..");
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.getDefault() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get SubscriptionMaster");
		}
		//log.info(" <----- SubscriptionMaster getDefault Service END -----> ");
		return response;
	}

	private SubscriptionMasterEntity updatedValues(SubscriptionMasterEntity subscriptionMasterEntity,
			SubscriptionMasterDTO subscriptionDTO) {
		SubscriptionMasterEntity subscriptionMaster = subscriptionMasterEntity;
		subscriptionMaster.setSubscriptionName(subscriptionDTO.getSubscriptionName());
		subscriptionMaster.setDescription(subscriptionDTO.getDescription());
		subscriptionMaster.setIsActive(subscriptionDTO.getIsActive());
		subscriptionMaster.setNumOfDays(subscriptionDTO.getNumOfDays());
		subscriptionMaster.setIsCustom(subscriptionDTO.getIsCustom());
		subscriptionMaster.setIsDefault(subscriptionDTO.getIsDefault());
		return subscriptionMaster;
	}

	private boolean validateSubscriptionName(String subscriptionName) {
		//log.info(" <----- SubscriptionMaster validateSubscriptionName Service STARTED -----> ");
		boolean flag = false;
		try {
			SubscriptionMasterEntity subscription = subscriptionMasterDao.validateSubscriptionName(subscriptionName);
			if (subscription == null) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.validateSubscriptionName() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster validateSubscriptionName Service END -----> ");
		return flag;
	}

	private boolean validateIsDefault() {
		//log.info(" <----- SubscriptionMaster validateIsDefault Service STARTED -----> ");
		boolean flag = false;
		try {
			SubscriptionMasterEntity subscription = subscriptionMasterDao.validateIsDefault();
			if (subscription == null) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			log.error("<---- SubscriptionMasterServiceImpl.validateIsDefault() ----> EXCEPTION", e);
		}
		//log.info(" <----- SubscriptionMaster validateIsDefault Dao END -----> ");
		return flag;
	}
	
	
	@Override
	public BaseDTO findbysubUUID(String name) {
		BaseDTO response = new BaseDTO();
		try {
			SubscriptionMasterEntity sub = repository.findBySubNameIgnoreCase(name);
			response.setMessage("Subscription Name Retrieved");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(sub);
		} catch (Exception e) {
			log.error("EXCEPTION "+e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("No Default subscription exist");
		}
		return response;
	}
}
