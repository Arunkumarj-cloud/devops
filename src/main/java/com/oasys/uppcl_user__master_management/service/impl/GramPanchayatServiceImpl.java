package com.oasys.uppcl_user__master_management.service.impl;

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
import com.oasys.uppcl_user__master_management.dao.GramPanchayatDao;
import com.oasys.uppcl_user__master_management.dto.GramPanchayatDTO;
import com.oasys.uppcl_user__master_management.entity.GramPanchayat;
import com.oasys.uppcl_user__master_management.repository.GramPanchayatRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.GramPanchayatService;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class GramPanchayatServiceImpl implements GramPanchayatService{
	
	@Autowired
	GramPanchayatDao gramPanchayatDao;
	
	@Autowired
	GramPanchayatRepository gramPanchayatRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public BaseDTO create(GramPanchayatDTO gramPanchayatDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.create ===>");
		try {
			response = gramPanchayatDao.createGramPanchayat(gramPanchayatDTO);
		} catch (DataIntegrityViolationException e) {

			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception in GramPanchayatServiceImpl.create: " + e);
		} catch (Exception e) {

			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Exception in GramPanchayatServiceImpl.create: " + e);
		}
		//log.info("<===Ended GramPanchayatServiceImpl.create ===>");
		return response;
	}
	
	public BaseDTO getGramPanchayat() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.getGramPanchayat ===>");
		try {
			response = gramPanchayatDao.getGrampanchayat();
			
		}catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.getGramPanchayat " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended GramPanchayatServiceImpl.getGramPanchayat ===>");
		return response;
		
	}
	public BaseDTO getGramPanchayatById(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.getGramPanchayatById {} ",id);
		try {
			Optional<GramPanchayat> gramPanchayat = gramPanchayatDao.getGramPanchayatById(id);
			if(gramPanchayat.isPresent()) {
				//log.info("Grampanchayat Id {}", id);
				GramPanchayatDTO gramPanchayatDTO = new GramPanchayatDTO();
				gramPanchayatDTO = objectMapper.convertValue(gramPanchayat.get(), GramPanchayatDTO.class);
				response.setResponseContent(gramPanchayatDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get Details successfully");
			}else {
				log.error("No data found {} ",id);
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		}catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.getGramPanchayatById {} " , e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended GramPanchayatServiceImpl.getGramPanchayatById ===>");
		return response;
		
	}
	
	public BaseDTO updateGramPanchayat(UUID id,GramPanchayatDTO gramPanchayatDTO) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.updateGramPanchayat ===>");
		try {
			response = gramPanchayatDao.updateGramPanchayat(id,gramPanchayatDTO);
			
		}catch(DataIntegrityViolationException e) {
			
			log.error(" Exception GramPanchayatServiceImpl.updateGramPanchayat " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.updateGramPanchayat " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended GramPanchayatServiceImpl.updateGramPanchayat ===>");
		return response;
		
	}

	public BaseDTO deleteGramPanchayat(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.deleteGramPanchayat ===>");
		try {
			response = gramPanchayatDao.deleteGramPanchayat(id);
			
		}catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.deleteGramPanchayat " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started GramPanchayatServiceImpl.deleteGramPanchayat ===>");
		return response;
		
	}
	
	public BaseDTO getAllPanchayatlazy(PaginationRequestDTO pageData ) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.getAllPanchayatlazy ===>");
		try {
			response = gramPanchayatDao.getAllPanchayatlazy(pageData);
			
		}catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.getAllPanchayatlazy " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Ended GramPanchayatServiceImpl.getAllPanchayatlazy ===>");
		return response;
		
	}

	@Override
	public BaseDTO getByBlockId(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started GramPanchayatServiceImpl.getByBlockId ===>");
		try {
			response = gramPanchayatDao.getByBlockId(id);
			
		}catch(Exception e) {
			log.error(" Exception GramPanchayatServiceImpl.getByBlockId " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended GramPanchayatServiceImpl.getByBlockId ===>");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started GramPanchayatServiceImpl.softDelete()===>");
		try {
			GramPanchayat gramPanchayat = gramPanchayatRepository.getOne(id);
			if (gramPanchayat != null) {
				if (gramPanchayat.getStatus() == true) {
					gramPanchayat.setStatus(false);
					gramPanchayat = gramPanchayatRepository.save(gramPanchayat);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Gram Panchayat Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Gram Panchayat Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- GramPanchayatServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Ward Master Details");
		}
		//log.info("<=== Ended GramPanchayatServiceImpl.softDelete() ===>");
		return response;
	}	
	

}
