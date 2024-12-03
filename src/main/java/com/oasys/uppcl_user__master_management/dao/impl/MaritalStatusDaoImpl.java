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
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.MaritalStatusDao;
import com.oasys.uppcl_user__master_management.entity.MaritalStatusMasterEntity;
import com.oasys.uppcl_user__master_management.repository.MaritalStatusRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MaritalStatusDaoImpl implements MaritalStatusDao{
	
	@Autowired
	MaritalStatusRepository maritalStatusRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public MaritalStatusMasterEntity save(MaritalStatusMasterEntity maritalStatusMaster) {
		//log.info("<---- Started MaritalStatusDaoImpl.save ----> {} ",maritalStatusMaster);
		MaritalStatusMasterEntity maritalStatus = maritalStatusRepository.save(maritalStatusMaster);
		//log.info("<---- Ended MaritalStatusDaoImpl.save ---->");
		return maritalStatus;
	}
	@Override
	public MaritalStatusMasterEntity findByName(String maritalStatus) {
		//log.info("<---- Started MaritalStatusDaoImpl.findByName ----> {} ",maritalStatus);
		MaritalStatusMasterEntity maritalStatusMaster = maritalStatusRepository.findByName(maritalStatus);
		//log.info("<---- Ended MaritalStatusDaoImpl.findByName ---->");
		return maritalStatusMaster;
	}
	@Override
	public List<MaritalStatusMasterEntity> findByIdNotIn(UUID id) {
		//log.info("<---- Started MaritalStatusDaoImpl.findByIdNotIn ----> {} ",id);
		List<MaritalStatusMasterEntity> maritalStatus = maritalStatusRepository.findByIdNotIn(id);
		//log.info("<---- Ended MaritalStatusDaoImpl.findByIdNotIn ---->");
		return maritalStatus;
	}
	
	public BaseDTO getMaritalStatus() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusDaoImpl.getMaritalStatus===>");
		List<MaritalStatusMasterEntity> marital=new ArrayList<>();
		try {
			marital = maritalStatusRepository.findAll();
			if(!marital.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContent(marital);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get All Maritalstatus Details");
			}else {
				response.setMessage("Data doesn't exist");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception MaritalStatusDaoImpl.getMaritalStatus : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started MaritalStatusDaoImpl.getMaritalStatus===>");
		return response;
		
	}
	
	@Override
	public Optional<MaritalStatusMasterEntity> getMaritalStatusById(UUID id) {
		//log.info("<---- Started MaritalStatusDaoImpl.getMaritalStatusById ----> {} ",id);
		Optional<MaritalStatusMasterEntity> maritalStatus = maritalStatusRepository.findById(id);
		//log.info("<---- Started MaritalStatusDaoImpl.getMaritalStatusById ---->");
		return maritalStatus;
	}
		
	public BaseDTO softDelete(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MaritalStatusDaoImpl.softDelete===>");
		MaritalStatusMasterEntity marital=new MaritalStatusMasterEntity();
		try {
			
			marital = maritalStatusRepository.getOne(id);
			if(marital.getId()!=null) {
				marital.setStatus(false);
				maritalStatusRepository.save(marital);
			response.setMessage("Data Deleted Sucessfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			}else {
				//log.error("no data found");
				response.setMessage("no data found");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception MaritalStatusDaoImpl.softDelete : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		//log.info("<===Ended MaritalStatusDaoImpl.softDelete===>");
		return response;
		
	}
	
	public BaseDTO getAllMaritalStatuslazy(PaginationRequestDTO requestData) {
		//log.info("<== Started MaritalStatusDaoImpl.getAllMaritalStatuslazy() ==>");
		BaseDTO response = new BaseDTO();
		Page<MaritalStatusMasterEntity> maritalStatus = null;
		List<MaritalStatusMasterEntity> contentList = new ArrayList<MaritalStatusMasterEntity>();
		Pageable pageRequest;
		try {

		//log.info("<<<< ------- MaritalStatusDaoImpl.getAllMaritalStatuslazy() ---------- >>>>>>>");
		
		if (requestData.getSearch() != null) {
			if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
				pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
						Sort.by(Direction.ASC, requestData.getSortField()));
				maritalStatus = maritalStatusRepository.search(pageRequest, requestData.getSearch());
			} else {
				pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
						Sort.by(Direction.DESC, requestData.getSortField()));
				maritalStatus = maritalStatusRepository.search(pageRequest, requestData.getSearch());
			}
		} else {

			if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
				maritalStatus = maritalStatusRepository.findAll(PageRequest.of(requestData.getPageNo(),
						requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
			} else {
				maritalStatus = maritalStatusRepository.findAll(PageRequest.of(requestData.getPageNo(),
						requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
			}
		}
		if (maritalStatus.isEmpty()) {
			String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			//log.error("No Record Found..");
		
		}else {
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		response.setNumberOfElements(maritalStatus.getNumberOfElements());
		response.setTotalRecords(maritalStatus.getTotalElements());
		response.setTotalPages(maritalStatus.getTotalPages());
		for (MaritalStatusMasterEntity i : maritalStatus) {
		contentList.add(i);
		//log.info("Get Lazy list successfully");
		}
		response.setResponseContents(contentList);
		
		}
	
		} catch (Exception e) {
		log.error("<<<< ------- MaritalStatusDaoImpl.getAllMaritalStatuslazy() ---------- Exception>>>>", e);
		String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
		response.setMessage(msg);
		response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== MaritalStatusDaoImpl.getAllMaritalStatuslazy() Ended ==>");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------MaritalStatusDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<MaritalStatusMasterEntity> martialTypeMaster = null;
		try {
			martialTypeMaster = maritalStatusRepository.getAllactive();
			if (martialTypeMaster != null) {
				response.setResponseContents(martialTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("MaritalStatus get all active Details Fecthed Successfully");
			} else {
				//log.error("No martial status is Actives");
				response.setMessage("No martial status is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------MaritalStatusDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------MaritalStatusDaoImpl.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO deleteMarital(UUID id) {
		//log.info(" <----- MaritalStatusDaoImpl delete  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			Optional<MaritalStatusMasterEntity> muncipality = maritalStatusRepository.findById(id);
			if (muncipality != null) {
				maritalStatusRepository.deleteById(id);
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
			log.error("<---- MaritalStatusDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- MaritalStatusDaoImpl delete  END -----> ");
		return response;
	}
	@Override
	public MaritalStatusMasterEntity findByNameIgnoreCase(String name) {
		//log.info("<---- Started MaritalStatusDaoImpl.findByName ----> {} ",maritalStatus);
				MaritalStatusMasterEntity maritalStatusMaster = maritalStatusRepository.findByNameIgnoreCase(name);
				//log.info("<---- Ended MaritalStatusDaoImpl.findByName ---->");
				return maritalStatusMaster; 
	}

}
