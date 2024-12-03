package com.oasys.uppcl_user__master_management.service.impl;




import java.util.Date;
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
import com.oasys.uppcl_user__master_management.dao.TypeOfAccountDao;
import com.oasys.uppcl_user__master_management.dto.TypeOfAccountDTO;
import com.oasys.uppcl_user__master_management.entity.TypeOfAccountMaster;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.TypeOfAccountService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TypeOfAccountServiceImpl implements TypeOfAccountService{
	
	@Autowired
	TypeOfAccountDao typeOfAccountDao;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public BaseDTO create(TypeOfAccountDTO typeOfAccountDTO) {
		//log.info(" <----- TypeOfAccount create Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			TypeOfAccountMaster typeOfAccount = typeOfAccountDao.findByAccountType(typeOfAccountDTO.getAccountType());
			if(typeOfAccount != null) {
				//log.error("Account type is already Exist");
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Type of Account " + message);
				
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			}
			 typeOfAccount = objectMapper.convertValue(typeOfAccountDTO, TypeOfAccountMaster.class);
			 typeOfAccount = typeOfAccountDao.save(typeOfAccount);
			if (typeOfAccount == null) {
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(typeOfAccountDTO.getAccountType() + " " + message);
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				//log.error("Unable to create Account Type");			
			} else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(typeOfAccountDTO.getAccountType() + " " + message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Added");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- TypeOfAccountServiceImpl.create() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.create() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- TypeOfAccount create Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO update(UUID id, TypeOfAccountDTO typeOfAccountDTO) {
		//log.info(" <----- TypeOfAccount updateTypeOfAccount Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean validateType = false;
			Optional<TypeOfAccountMaster> typeOfAccountMaster = typeOfAccountDao.getById(id);
			if (typeOfAccountMaster.isPresent()) {
				//log.info("type of account Id {}", id);
				TypeOfAccountMaster updateTypeOfAccount = updatedValues(typeOfAccountMaster.get(), typeOfAccountDTO);
				List<TypeOfAccountMaster> exceptTypeOfAccount = typeOfAccountDao.findByIdNotIn(id);
				for (TypeOfAccountMaster typeOfAccount : exceptTypeOfAccount) {
					if (typeOfAccount.getId() != updateTypeOfAccount.getId()) {
						if (typeOfAccount.getAccountType().equalsIgnoreCase(updateTypeOfAccount.getAccountType())) {
							validateType = true;
							break;
						} else {
							validateType = false;
						}
					} else {
						validateType = false;
					}
				}
				if (!validateType) {
					updateTypeOfAccount = typeOfAccountDao.save(updateTypeOfAccount);
					message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(typeOfAccountDTO.getAccountType() + " " + message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Updated");
				} else {
					message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
					response.setMessage("This Type of Account " + message);
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.error("Account Type is already exists..");
				}
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found.. - {} ", id);
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- TypeOfAccountDaoImpl.update() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- TypeOfAccountDaoImpl.update() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- TypeOfAccount updateTypeOfAccount Dao END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- TypeOfAccount getById Service STARTED -----> {} ", id);
		BaseDTO response = new BaseDTO();
		TypeOfAccountDTO typeOfAccountDTO = new TypeOfAccountDTO();
		try {
			Optional<TypeOfAccountMaster> typeOfAccount = typeOfAccountDao.getById(id);
			if (typeOfAccount.isPresent()) {
				typeOfAccountDTO = objectMapper.convertValue(typeOfAccount.get(), TypeOfAccountDTO.class);
				response.setResponseContent(typeOfAccountDTO);
				response.setMessage("Successfully Get TypeOfAccount");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get TypeOfAccount - {} ",typeOfAccountDTO);
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found.. {} ", id);
			}
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.getById() ----> EXCEPTION - {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TypeOfAccount getById Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		//log.info(" <----- TypeOfAccount getAll Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = typeOfAccountDao.getAll();
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.getAll() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAll TypeOfAccount");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- TypeOfAccount getAll Service END -----> ");
		return response;
	}
	

	public BaseDTO getAllActive() {
		//log.info(" <----- TypeOfAccount getAllActive Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = typeOfAccountDao.getAllActive();
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.getAllActive() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAllActive TypeOfAccount");
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- TypeOfAccount getAllActive Service END -----> ");
		return response;
	}
	
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- TypeOfAccount getLazyList Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = typeOfAccountDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.getLazyList() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to GetAllActive TypeOfAccount");
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- TypeOfAccount getLazyList Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- TypeOfAccount delete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = typeOfAccountDao.delete(id);
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Delete TypeOfAccount");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg) ;
		}
		//log.info(" <----- TypeOfAccount delete Service END -----> ");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- TypeOfAccount softDelete Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = typeOfAccountDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- TypeOfAccountServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete TypeOfAccount");
		}
		//log.info(" <----- TypeOfAccount softDelete Service END -----> ");
		return response;
	}
	
	private TypeOfAccountMaster updatedValues(TypeOfAccountMaster typeOfAccountMaster,
			TypeOfAccountDTO typeOfAccountDTO) {
		TypeOfAccountMaster typeOfAccount = typeOfAccountMaster;
		typeOfAccount.setStatus(typeOfAccountDTO.getStatus());
		typeOfAccount.setAccountType(typeOfAccountDTO.getAccountType());
		typeOfAccount.setRemarks(typeOfAccountDTO.getRemarks());
		typeOfAccount.setModifiedDate(new Date());
		return typeOfAccount;
	}

}
