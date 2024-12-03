package com.oasys.uppcl_user__master_management.dao.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.TypeOfAccountDao;
import com.oasys.uppcl_user__master_management.entity.TypeOfAccountMaster;
import com.oasys.uppcl_user__master_management.repository.TypeOfAccountRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class TypeOfAccountDaoImpl implements TypeOfAccountDao {

	@Autowired
	TypeOfAccountRepository repository;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public TypeOfAccountMaster save(TypeOfAccountMaster typeOfAccountMaster) {
		//log.info(" <----- TypeOfAccount create Dao STARTED -----> ");
		TypeOfAccountMaster type = repository.save(typeOfAccountMaster);
		//log.info(" <----- TypeOfAccount create Dao END -----> ");
		return type;
	}

	@Override
	public Optional<TypeOfAccountMaster> getById(UUID id) {
		//log.info(" <----- TypeOfAccount getById Dao STARTED -----> ");
		Optional<TypeOfAccountMaster> typeOfAccount = repository.findById(id);
		//log.info(" <----- TypeOfAccount getById Dao END -----> ");
		return typeOfAccount;
	}

	@Override
	public TypeOfAccountMaster findByAccountType(String type) {
		//log.info(" <----- TypeOfAccount findByAccountType Dao STARTED -----> ");
		TypeOfAccountMaster typeOfAccount = repository.findByAccountTypeIgnoreCase(type);
		//log.info(" <----- TypeOfAccount findByAccountType Dao END -----> ");
		return typeOfAccount;
	}

	@Override
	public List<TypeOfAccountMaster> findByIdNotIn(UUID id) {
		//log.info(" <----- TypeOfAccount findByIdNotIn Dao STARTED -----> ");
		List<TypeOfAccountMaster> typeOfAccount = repository.findByIdNotIn(id);
		//log.info(" <----- TypeOfAccount findByIdNotIn Dao END -----> ");
		return typeOfAccount;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- TypeOfAccount getAll Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<TypeOfAccountMaster> typeOfAccount = repository.findAll();
			if (typeOfAccount.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(typeOfAccount);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Type Of account get all Details Fetched Successfully");
			}
		} catch (Exception e) {
			log.error("<----- TypeOfAccountDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TypeOfAccount getAll Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- TypeOfAccount getAllActive Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			List<TypeOfAccountMaster> typeOfAccount = repository.getAllActive();
			if (typeOfAccount.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setResponseContents(typeOfAccount);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get All Type of Account Details");
			}
		} catch (Exception e) {
			log.error("<----- TypeOfAccountDaoImpl.getAll() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			//log.info("Type Of account get all Active  Details Fetched Successfully");
		}
		//log.info(" <----- TypeOfAccount getAllActive Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- TypeOfAccount getLazyList Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		List<TypeOfAccountMaster> contentList = new ArrayList<TypeOfAccountMaster>();
		Page<TypeOfAccountMaster> typeOfAccountList = null;
		Pageable pageRequest;
		try {
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					typeOfAccountList = repository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					typeOfAccountList = repository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					typeOfAccountList = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					typeOfAccountList = repository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (typeOfAccountList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setNumberOfElements(typeOfAccountList.getNumberOfElements());
				response.setTotalRecords(typeOfAccountList.getTotalElements());
				response.setTotalPages(typeOfAccountList.getTotalPages());
				for (TypeOfAccountMaster TypeOfAccount : typeOfAccountList) {
					contentList.add(TypeOfAccount);
				}
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(contentList);
				//log.info("Successfully get Details");
			}

		} catch (Exception e) {
			log.error("<----- TypeOfAccountDaoImpl.getLazyList() ----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TypeOfAccount getLazyList Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- TypeOfAccount delete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TypeOfAccountMaster typeOfAccount = repository.getOne(id);
			//log.info("Type of account Id  {} ",id);
			if (typeOfAccount != null) {
				repository.delete(typeOfAccount);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted",id);
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TypeOfAccountDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- TypeOfAccount delete Dao END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- TypeOfAccount softDelete Dao STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			TypeOfAccountMaster typeOfAccount = repository.getOne(id);
			if (typeOfAccount != null) {
				typeOfAccount.setStatus(false);
				repository.save(typeOfAccount);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- TypeOfAccountDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete TypeOfAccount");
		}
		//log.info(" <----- TypeOfAccount softDelete Dao END -----> ");
		return response;
	}

	

}
