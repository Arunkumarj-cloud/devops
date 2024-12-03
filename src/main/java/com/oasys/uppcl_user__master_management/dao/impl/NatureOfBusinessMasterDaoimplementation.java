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
import com.oasys.uppcl_user__master_management.dao.NatureOfBusinessDao;
import com.oasys.uppcl_user__master_management.entity.NatureOfBusinessMasterEntity;
import com.oasys.uppcl_user__master_management.repository.NatureOfBusinessRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class NatureOfBusinessMasterDaoimplementation  implements NatureOfBusinessDao{



	
	@Autowired
	NatureOfBusinessRepository nombre;
	
	@Autowired
	MessageSource  messageSource;
	
	@Override
	public NatureOfBusinessMasterEntity save(NatureOfBusinessMasterEntity nomb) {
		//log.info(" Started NatureOfBusinessDaoImpl.save - {} ",natureOfBusiness);
		NatureOfBusinessMasterEntity natureOfBusinessEntity=nombre.save(nomb);				
		//log.info(" Ended NatureOfBusinessDaoImpl.save ");
		return natureOfBusinessEntity;
	}
	//@Override
	/*public List<NatureOfBusinessMasterEntity > findByIdNotIn(UUID id) {
		//log.info(" Started NatureOfBusinessDaoImpl.save - {} ",id);
		List<NatureOfBusinessMasterEntity > natureOfBusinessEntity=nombre.findByIdNotIn(id);				
		//log.info(" Ended NatureOfBusinessDaoImpl.save ");
		return natureOfBusinessEntity;
	}
	@Override
	public NatureOfBusinessMasterEntity  findByNatureOfBussinessName(String natureOfBusiness) {
		//log.info(" Started NatureOfBusinessDaoImpl.save - {} ",natureOfBusiness);
		NatureOfBusinessMasterEntity  natureOfBusinessEntity=nombre.findByName(natureOfBusiness);				
		//log.info(" Ended NatureOfBusinessDaoImpl.save ");
		return natureOfBusinessEntity;
	}*/
	@Override
	public Optional<NatureOfBusinessMasterEntity > findById(UUID id) {
		//log.info(" Started NatureOfBusinessDaoImpl.findById - {} ",id);
		  Optional<NatureOfBusinessMasterEntity > natureOfBusinessEntity=nombre.findById(id);				
		//log.info(" Ended NatureOfBusinessDaoImpl.findById ");
		return natureOfBusinessEntity;
	}
	
	public BaseDTO getNatureOfBusiness() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started NatureOfBusinessDaoImpl.getNatureOfBusiness===>");
		List<NatureOfBusinessMasterEntity> natureOfBusiness=new ArrayList<>();
		try {
			natureOfBusiness = nombre.findAll();
			if(!natureOfBusiness.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContent(natureOfBusiness);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Details successfully");
			}else {
				//log.warn("Data doesn't exist");
				response.setMessage("Data doesn't exist");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			
		}catch(Exception e) {
			log.error("Exception NatureOfBusinessDaoImpl.getNatureOfBusiness : " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started NatureOfBusinessDaoImpl.getNatureOfBusiness===>");
		return response;
		
	}
	
	public BaseDTO deleteNature(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started NatureOfBusinessDaoImpl.deleteNature===>");
		NatureOfBusinessMasterEntity natureofBusiness1=new NatureOfBusinessMasterEntity();
		try {
			Optional<NatureOfBusinessMasterEntity> natureOfBusiness = nombre.findById(id);
			if (natureOfBusiness != null) {
				nombre.deleteById(id);
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
			log.error("<---- NatureOfBusinessDaoImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- NatureOfBusinessDaoImpl delete Dao END -----> ");
		return response;
	}

	
	public BaseDTO getAllNatureOfBusinesslazy(PaginationRequestDTO requestData) {
		//log.info("<== Started NatureOfBusinessDaoImpl.getAllNatureOfBusinesslazy() ==>");
		BaseDTO response = new BaseDTO();
		Page<NatureOfBusinessMasterEntity> natureOfBusiness = null;
		List<NatureOfBusinessMasterEntity> contentList = new ArrayList<NatureOfBusinessMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- NatureOfBusinessDaoImpl.getAllNatureOfBusinesslazy() ---------- >>>>>>>");
			if (requestData.getSearch() != null) {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.ASC, requestData.getSortField()));
					natureOfBusiness = nombre.search(pageRequest, requestData.getSearch());
				} else {
					pageRequest = PageRequest.of(requestData.getPageNo(), requestData.getPaginationSize(),
							Sort.by(Direction.DESC, requestData.getSortField()));
					natureOfBusiness = nombre.search(pageRequest, requestData.getSearch());
				}
			} else {
				if (requestData.getSortOrder().equalsIgnoreCase("ASC")) {
					natureOfBusiness = nombre.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.ASC, requestData.getSortField())));
				}	else {
					natureOfBusiness = nombre.findAll(PageRequest.of(requestData.getPageNo(),
							requestData.getPaginationSize(), Sort.by(Direction.DESC, requestData.getSortField())));
				}
			}
			if (natureOfBusiness.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(natureOfBusiness.getNumberOfElements());
				response.setTotalRecords(natureOfBusiness.getTotalElements());
				response.setTotalPages(natureOfBusiness.getTotalPages());
				for (NatureOfBusinessMasterEntity i : natureOfBusiness) {
					contentList.add(i);
					//log.info("Get Details Successfully");
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
			}
		} catch (Exception e) {
			log.error("<<<< ------- NatureOfBusinessDaoImpl.getAllNatureOfBusinesslazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== NatureOfBusinessDaoImpl.getAllNatureOfBusinesslazy() Ended ==>");
		return response;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info("<------NatureOfBusinessDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<NatureOfBusinessMasterEntity> businessTypeMaster = null;
		try {
			businessTypeMaster = nombre.getAllactive();
			if (!businessTypeMaster.isEmpty()) {
				response.setResponseContents(businessTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Nature of Business Get All Active Details Fetched Successfully");
			} else {
				//log.error("Nature of Business Type is not Active");
				response.setMessage("Nature of Business Type is not Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------NatureOfBusinessDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------NatureOfBusinessDaoImpl.getAllActive()------> Ended");
		return response;
	}
	@Override
	public NatureOfBusinessMasterEntity findByNameIgnoreCase(String name) {
		       //log.info(" Started NatureOfBusinessDaoImpl.save - {} ",id);
				NatureOfBusinessMasterEntity natureOfBusinessEntity=nombre.findByNameIgnoreCase(name);				
				//log.info(" Ended NatureOfBusinessDaoImpl.save ");
				return natureOfBusinessEntity;
	}
	
}


