package com.oasys.uppcl_user__master_management.service.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.MuncipalityDao;
import com.oasys.uppcl_user__master_management.dto.MuncipalityDTO;
import com.oasys.uppcl_user__master_management.entity.Muncipality;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.MuncipalityService;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class MuncipalityServiceImpl implements MuncipalityService{
	
	@Autowired(required = false)
	MuncipalityDao muncipalityDao;
	
	@Autowired(required = false)
	MessageSource messageSource;
	
	@Override
	public BaseDTO createMuncipality(MuncipalityDTO muncipalityDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.createMuncipality ===>");
		try {
			response = muncipalityDao.createMuncipality(muncipalityDTO);
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception in MuncipalityServiceImpl.createMuncipality: " + e);
			
		}catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception in MuncipalityServiceImpl.createMuncipality: " + e);	
			}
		//log.info("<===Ended MuncipalityServiceImpl.createMuncipality ===>");
		return response;
	}
	
	public BaseDTO getMuncipality() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.getMuncipality ===>");
		try {
			response = muncipalityDao.getMuncipality();
			
		}catch(Exception e) {
			log.error(" Exception MuncipalityServiceImpl.getMuncipality " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended MuncipalityServiceImpl.getMuncipality ===>");
		return response;
		
	}
	public BaseDTO getMuncipalityById(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.getMuncipalityById ===>");
		try {
			Optional<Muncipality> muncipality = muncipalityDao.getMuncipalityById(id);
			if (muncipality.isPresent()) {
				//log.info("Municipality Id {}", muncipality.get().getId());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContent(muncipality);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Details Successfully");
			} else {
				//log.error("No data found");
				response.setMessage("No data found");
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			}
			
		}catch(Exception e) {
			log.error(" Exception MuncipalityServiceImpl.getMuncipalityById " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended MuncipalityServiceImpl.getMuncipalityById ===>");
		return response;
		
	}
	
	public BaseDTO updateMuncipality(UUID id,MuncipalityDTO muncipalityDTO) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.updateMuncipality ===>");
		try {
			response = muncipalityDao.updateMuncipality(id,muncipalityDTO);
			
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception MuncipalityServiceImpl.updateMuncipality " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch(Exception e) {
			log.error(" Exception MuncipalityServiceImpl.updateMuncipality " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended MuncipalityServiceImpl.updateMuncipality ===>");
		return response;
		
	}

	public BaseDTO softDelete(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.softDelete ===>");
		try {
			response = muncipalityDao.softDelete(id);
			
		}catch(Exception e) {
			log.error(" Exception MuncipalityServiceImpl.softDelete " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===ended MuncipalityServiceImpl.softDelete ===>");
		return response;
		
	}

	public BaseDTO getLazyList(PaginationRequestDTO dto) {
		//log.info(" <----- Municipality getLazyList Service STARTED -----> ");
		BaseDTO baseDTO = new BaseDTO();
		try {
			baseDTO = muncipalityDao.getLazyList(dto);
		} catch (Exception e) {
			log.error("<---- DistrictMasterServiceImpl.getLazyList() ----> EXCEPTION", e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//baseDTO.setMessage("Unable to getLazyList DistrictName");
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			baseDTO.setMessage(msg);
		}
		//log.info(" <----- Municipality getLazyList Service END -----> ");
		return baseDTO;
	}
	
	public BaseDTO getDistrictById(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started MuncipalityServiceImpl.getDistrictById ===>");
		try {
			response = muncipalityDao.getDistrictById(id);
			
		}catch(Exception e) {
			log.error(" Exception MuncipalityServiceImpl.getDistrictById " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("data doesn't exist");
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- MuncipalityServiceImpl getDistrictById Service END -----> ");
		return response;
		
	}

	@Override
	public BaseDTO deleteMunicipality(UUID id) {
		//log.info(" <----- MuncipalityServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = muncipalityDao.deleteMunicipality(id);
		} catch (Exception e) {
			log.error("<---- MuncipalityServiceImpl.delete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Delete Municipality");
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- MuncipalityServiceImpl.delete() Service END -----> ");
		return response;
	}
	

}
