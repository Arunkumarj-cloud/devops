package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.DistrictMasterDAO;
import com.oasys.uppcl_user__master_management.dto.DistrictMasterDTO;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.DistrictMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DistrictMasterServiceImpl implements DistrictMasterService {
	
	
	@Autowired
	DistrictMasterDAO districtMasterDAO;
	
	@Autowired
	DistrictMasterRepository districtMasterRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(DistrictMasterDTO districtMasterDTO) {
		//log.info(" <----- DistrictMasterServiceImpl.create() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.create(districtMasterDTO);
		} catch(DataIntegrityViolationException e) {
			
			log.error("<---- DistrictMasterServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.create() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Add DistrictMaster");
		}
		//log.info(" <----- DistrictMasterServiceImpl.create() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getByName(String name) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started RoleMasterServiceImpl.getByName()===>");
		try {
			response = districtMasterDAO.getByName(name);
		} catch (Exception e) {
			log.error(" Exception RoleMasterServiceImpl.getByName() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended RoleMasterServiceImpl.getByName() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getAll() {
		
		BaseDTO baseDTO = new BaseDTO();
		List<DistrictMasterEntity> districtMasterEntity = null;
		try {
			districtMasterEntity  = districtMasterDAO.getAll();
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
			baseDTO.setResponseContents(districtMasterEntity);
			baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getAll() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- District getAll Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getById(UUID id) {
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.getById(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getByIds(Set<UUID> id) {
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.getByIds(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <-----DistrictMasterServiceImpl.delete() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.delete(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.delete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----DistrictMasterServiceImpl.delete() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO update(UUID id,DistrictMasterDTO districtMasterDTO) {
		//log.info(" <-----DistrictMasterServiceImpl.update() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.update(id,districtMasterDTO);
		}catch(DataIntegrityViolationException e) {
			log.error("<---- DistrictMasterServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.update() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <-----DistrictMasterServiceImpl.update() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- DistrictMasterServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getLazyList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getLazyList() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- DistrictMasterServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.getAllActive();
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getAllActive() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getAllActive() Service END -----> ");
		return baseDTO;
	}
	
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- DistrictMasterServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.softDelete(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			baseDTO.setMessage("Unable to Delete DistrictName");
		}
		//log.info(" <----- DistrictMasterServiceImpl.softDelete() Service END -----> ");
		return baseDTO;
	}
	
	@Override
	public BaseDTO getByStateId(UUID id) {
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = districtMasterDAO.getByStateId(id);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getById() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to Get District Master");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getById() Service END -----> ");
		return baseDTO;
	}

	@Override
	public BaseDTO getBydistricName(String districName,UUID stateId) {
		//log.info(" <----- DistrictMasterServiceImpl.getBydistricName() Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			
			baseDTO = districtMasterDAO.getBydistricName(districName,stateId);
			
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getBydistricName() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to Get District Master");
		String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
		baseDTO.setMessage(msg);
		}
		//log.info(" <----- DistrictMasterServiceImpl.getBydistricName() Service END -----> ");
		return baseDTO;
	}
	

}
