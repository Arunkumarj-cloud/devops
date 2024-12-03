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

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.StateMasterDao;
import com.oasys.uppcl_user__master_management.dto.StateMasterDTO;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class StateMasterDaoImpl implements StateMasterDao{
	
	@Autowired
	private StateMasterRepository repository;
	
	@Autowired
	MessageSource messageSource;

	private static final Boolean ACTIVE_STATUS = true;
	private static final Boolean INACTIVE_STATUS = false;
	private static final Boolean DELETE_FLAG = true;

	public StateMasterEntity save(StateMasterEntity stateMaster) {	
		//log.info("Entered in StateMasterDaoImpl.save(StateMasterDTO dto) - {} ",stateMaster);
		StateMasterEntity stateMasterEntity=repository.save(stateMaster);
		//log.info("Finished Execution of StateMasterDaoImpl.save(StateMasterDTO dto)");
		return stateMasterEntity;
	}
	
	public StateMasterEntity findByStateCode(String stateCode) {	
		//log.info("Entered in StateMasterDaoImpl.findByStateCode - {} ",stateCode);
		StateMasterEntity stateMasterEntity=repository.findByStateCode(stateCode);
		//log.info("Finished Execution of StateMasterDaoImpl.findByStateCode");
		return stateMasterEntity;
	}
	
	public List<StateMasterEntity> findByIdNotIn(UUID id) {	
		//log.info("Entered in StateMasterDaoImpl.findByIdNotIn - {} ",id);
		List<StateMasterEntity> stateMasterEntity=repository.findByIdNotIn(id);
		//log.info("Finished Execution of StateMasterDaoImpl.findByIdNotIn");
		return stateMasterEntity;
	}

	public Optional<StateMasterEntity> getState(UUID id) {		
		//log.info("Entered in StateMasterDaoImpl.getState(UUID id) - {} " ,id);
			Optional<StateMasterEntity> responseEntity = repository.findById(id);			
		//log.info("Finished Execution of StateMasterDaoImpl.getState(UUID id)");
		return responseEntity;
	}

	public BaseDTO getAllState(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("Entered in StateMasterDaoImpl.getAllState(StateMasterDTO dto)");
		Page<StateMasterEntity> stateMasterEntity = null;
		List<StateMasterEntity> contentsList = new ArrayList<StateMasterEntity>();
		Pageable pageRequest;
		try {
			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					stateMasterEntity = repository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					stateMasterEntity = repository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					stateMasterEntity = repository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					stateMasterEntity = repository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (stateMasterEntity.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");

			} else {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				for (StateMasterEntity stateMasterEntityList : stateMasterEntity) {
					contentsList.add(stateMasterEntityList);
					//log.info("successfully Get Details");
				}
				response.setResponseContents(contentsList);
				response.setNumberOfElements(stateMasterEntity.getNumberOfElements());
				response.setTotalRecords(stateMasterEntity.getTotalElements());
				response.setTotalPages(stateMasterEntity.getTotalPages());
				//log.info("State Master Data List Retrived");
			}
		} catch (Exception e) {
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}
		//log.info("Finished Execution of StateMasterDaoImpl.getAllState(StateMasterDTO dto)");
		return response;
	}

	public BaseDTO deleteState(StateMasterDTO dto) {
		BaseDTO response = new BaseDTO();
		//log.info("Entered in StateMasterDaoImpl.deleteState(StateMasterDTO dto)");
		try {
			StateMasterEntity response_entity = repository.getOne(dto.getId());
			if (null != response_entity) {

				if (dto.getDeleteFlag().equals(DELETE_FLAG)) {
					response_entity.setStatus(INACTIVE_STATUS);
					StateMasterEntity updated_entity = repository.save(response_entity);
					if (null != updated_entity) {
						String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage(msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("State Master Data Deleted");
					} else {
						response.setMessage("State Master Data Not Deleted");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.debug("State Master Data Not Deleted");
					}
				}

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.debug("No Record Found to Delete State Master Data");
			}
		} catch (Exception e) {
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}
		//log.info("Finished Execution of StateMasterDaoImpl.deleteState(StateMasterDTO dto)");
		return response;
	}

	public BaseDTO getActiveStateList() {
		BaseDTO response = new BaseDTO();
		//log.info("Entered in StateMasterDaoImpl.getActiveStateList()");
		try {
			List<StateMasterEntity> list = repository.findAllActiveStates();
            
			if (list.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContents(list);
				//log.info("State Master Data List Retrived");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.debug("No Data Found State Master Data List");
			}
		} catch (Exception e) {
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}
		//log.info("Finished Execution of StateMasterDaoImpl.getActiveStateList()");
		return response;
	}
	
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();

		try {
			List<StateMasterEntity> list = repository.findAll();

			if (list.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
						Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setResponseContents(list);

			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());

			}
		} catch (Exception e) {
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception Occured :" + e.getLocalizedMessage());
		}

		return response;
	}


}
