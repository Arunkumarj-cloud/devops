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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.StoreCategoryDao;
import com.oasys.uppcl_user__master_management.entity.StoreCategoryEntity;
import com.oasys.uppcl_user__master_management.repository.NatureOfBusinessRepository;
import com.oasys.uppcl_user__master_management.repository.StoreCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class StoreCategoryDaoImpl implements StoreCategoryDao {

	@Autowired
	StoreCategoryRepository storeCategoryRepository;

	@Autowired
	NatureOfBusinessRepository natureOfBusinessRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JdbcTemplate jdbcTemplate;

	final String DELETE_OK = "Y";
	
	@Autowired
	MessageSource messageSource;

	@Override
	public StoreCategoryEntity save(StoreCategoryEntity storeCategory) {
		//log.info(" <----- StoreCategory save Dao STARTED -----> ");
		StoreCategoryEntity storeCategoryEntity=storeCategoryRepository.save(storeCategory);
		//log.info(" <----- StoreCategory save Dao END -----> ");
		return storeCategoryEntity;
	}
	@Override
	public Optional<StoreCategoryEntity> findById(UUID id){
		//log.info(" <----- StoreCategory findById Dao STARTED -----> ");
		Optional<StoreCategoryEntity> storeCategoryEntity=storeCategoryRepository.findById(id);
		//log.info(" <----- StoreCategory findById Dao END -----> ");
		return storeCategoryEntity;
	}
	
	@Override
	public StoreCategoryEntity findByName(String name) {
		//log.info(" <----- StoreCategory findByName Dao STARTED -----> ");
		StoreCategoryEntity storeCategoryEntity=storeCategoryRepository.findByStoreCategoryName(name);
		//log.info(" <----- StoreCategory findByName Dao END -----> ");
		return storeCategoryEntity;
	}
	
	@Override
	public List<StoreCategoryEntity> findByIdNotIn(UUID id){
		//log.info(" <----- StoreCategory findByIdNotIn Dao STARTED -----> ");
		List<StoreCategoryEntity> storeCategoryEntity=storeCategoryRepository.findByIdNotIn(id);
		//log.info(" <----- StoreCategory findByIdNotIn Dao END -----> ");
		return storeCategoryEntity;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<==== Started StoreCategoryDaoImpl.getById() ===> ");
		BaseDTO baseDTO = new BaseDTO();
		StoreCategoryEntity storeCategoryEntity = null;
		try {
			storeCategoryEntity = storeCategoryRepository.getOne(id);
			//log.info("  store Category  Id  - {}  ",id );
			if (storeCategoryEntity.getId() != null) {
				baseDTO.setResponseContent(storeCategoryEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Successfully Get category details");
			} else {
				//log.error("Store Category id does not Exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------StoreCategoryDaoImpl.getById()------> Exception", e);
			baseDTO.setMessage("Error: " + e.getCause());
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended StoreCategoryDaoImpl.getById() ===> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<==== StoreCategoryDaoImpl.getAll() Started ====>");
		List<StoreCategoryEntity> storeCategoryList = null;
		try {
			storeCategoryList = storeCategoryRepository.findAll();
			if (storeCategoryList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setResponseContents(storeCategoryList);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get all store Category Details " );
			} else {
				baseDTO.setMessage("StoreCategory details List is Empty");
				baseDTO.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception StoreCategoryDaoImpl.getAll() ==>", e);
		}
		//log.info("<==== StoreCategoryDaoImpl.getAll() Ended ====>");
		return baseDTO;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info("<------StoreCategoryDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<StoreCategoryEntity> storeCategoryEntity = null;
		try {
			storeCategoryEntity = storeCategoryRepository.getAllactive();
			if (storeCategoryEntity != null) {
				response.setResponseContents(storeCategoryEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get all active store Category Details " );
			} else {
				response.setMessage("No StoreCategory is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------StoreCategoryService.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<----StoreCategoryDaoImpl.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info("<== StoreCategoryDaoImpl.getAllLazy() Started ==>");
		BaseDTO baseDTO = new BaseDTO();
		Page<StoreCategoryEntity> storeCategoryList = null;
		List<StoreCategoryEntity> contentsList = new ArrayList<StoreCategoryEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- StoreCategoryDaoImpl.getAllLazy() ---------- >>>>>>>");
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					storeCategoryList = storeCategoryRepository.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					storeCategoryList = storeCategoryRepository.search(pageRequest, requestData.getSearch());
				}
			} else {

				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					storeCategoryList = storeCategoryRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				} else {
					storeCategoryList = storeCategoryRepository.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (storeCategoryList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				baseDTO.setNumberOfElements(storeCategoryList.getNumberOfElements());
				baseDTO.setTotalRecords(storeCategoryList.getTotalElements());
				baseDTO.setTotalPages(storeCategoryList.getTotalPages());
				for (StoreCategoryEntity storeCategory : storeCategoryList) {
					contentsList.add(storeCategory);
				}
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setResponseContents(contentsList);
				//log.info("successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- StoreCategoryDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== StoreCategoryDaoImpl.getAllLazy() Ended ==>");
		return baseDTO;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  StoreCategoryDaoImpl.delete() ===> ");
		BaseDTO baseDTO = new BaseDTO();
		StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
		try {
			storeCategoryEntity = storeCategoryRepository.getOne(id);
			if (storeCategoryEntity.getId() != null) {
				storeCategoryRepository.delete(storeCategoryEntity);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				//log.error("StoreCategory ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				baseDTO.setMessage(msg);
				baseDTO.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- StoreCategoryDaoImpl.delete()  exception----->", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info("<====Ended StoreCategoryDaoImpl.delete() ===> ");
		return baseDTO;
	}

}
