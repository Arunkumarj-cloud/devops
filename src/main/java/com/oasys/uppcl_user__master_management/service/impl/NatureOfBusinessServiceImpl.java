package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.NatureOfBusinessDao;
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessMasterDto;
import com.oasys.uppcl_user__master_management.dto.NatureOfBusinessUpdateDTO;
import com.oasys.uppcl_user__master_management.entity.NatureOfBusinessMasterEntity;
import com.oasys.uppcl_user__master_management.repository.NatureOfBusinessRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.NatureOfBusinessService;

import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class NatureOfBusinessServiceImpl  implements NatureOfBusinessService  { 


	
	@Autowired
	NatureOfBusinessDao nobdao;
	
	@Autowired
	 NatureOfBusinessRepository  nobmr;
	
	@Autowired
    MessageSource messageSource;
	
	@Autowired
	ObjectMapper mapper;
	
	public BaseDTO createNatureOfBusiness(NatureOfBusinessMasterDto nobdto) {
		//log.info("<===Started NatureOfBusinessServiceImpl.getNatureOfBusiness {} ", nobdto);
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			Optional<NatureOfBusinessMasterEntity> optional = nobmr
					.findByMccCode(nobdto.getMccCode().trim().toUpperCase());
			if (optional.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage(
						"This Nature of Business " + message);
				// log.error(natureOfBusinessDTO.getName() + " " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			} else {
				NatureOfBusinessMasterEntity businessMasterEntity = new NatureOfBusinessMasterEntity();
				//businessMasterEntity.setCreatedBy(oauth2UserDetails.getId());
				businessMasterEntity.setCode(nobdto.getMccCode());
				businessMasterEntity.setName(nobdto.getName());
				if(StringUtils.isNotEmpty(nobdto.getRemarks())){
					businessMasterEntity.setRemarks(nobdto.getRemarks());
				}else {
				businessMasterEntity.setRemarks("Created First Time");
				}
				businessMasterEntity.setStatus(nobdto.getStatus());
				nobmr.save(businessMasterEntity);

				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setResponseContent(businessMasterEntity);
				// log.info(message + " - {} ", natureOfBusiness);
			}
		} catch (DataIntegrityViolationException e) {
			log.error("Exception NatureOfBusinessServiceImpl.createNatureOfBusiness : {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("Exception NatureOfBusinessServiceImpl.createNatureOfBusiness : {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("<===Ended NatureOfBusinessServiceImpl.getNatureOfBusiness ===>");
		return response;
	}
	
	public BaseDTO getNatureOfBusiness() {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started NatureOfBusinessServiceImpl.getNatureOfBusiness ===>");
		try {
			response =  nobdao.getNatureOfBusiness();
			
		}catch(Exception e) {
			log.error(" Exception NatureOfBusinessServiceImpl.getNatureOfBusiness " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended NatureOfBusinessServiceImpl.getNatureOfBusiness ===>");
		return response;
		
	}
	public BaseDTO getNatureOfBusinessById(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started NatureOfBusinessServiceImpl.getNatureOfBusinessById - {} ",id);
		String message = "";
		try {
			Optional<NatureOfBusinessMasterEntity > natureOfBusiness =  nobdao.findById(id);
			if(natureOfBusiness.isPresent()) {
				NatureOfBusinessMasterEntity  natureOfBusinessMaster = natureOfBusiness.get();
				NatureOfBusinessMasterDto nobdto=new NatureOfBusinessMasterDto();
				nobdto.setId(natureOfBusinessMaster.getId());
				nobdto.setName(natureOfBusinessMaster.getName());
				nobdto.setStatus(natureOfBusinessMaster.getStatus());
				nobdto.setRemarks(natureOfBusinessMaster.getRemarks());
				nobdto.setMccCode(natureOfBusinessMaster.getCode());
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(message);
				response.setResponseContent(nobdto);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get Nature of business Details");
			}else {
				//log.error("Data doesn't exist");
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}			
		}catch(Exception e) {
			log.error(" Exception NatureOfBusinessServiceImpl.getNatureOfBusinessById {} " , e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(message);
		}
		//log.info("<===Ended NatureOfBusinessServiceImpl.getNatureOfBusinessById ===>");
		return response;
		
	}
	
	
	
	public BaseDTO updateNatureOfBusiness(UUID id,  NatureOfBusinessUpdateDTO nobdto) {
		//log.info(" <----- NatureOfBusinessServiceImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		NatureOfBusinessMasterEntity nature = new NatureOfBusinessMasterEntity();
		try {
			boolean check = false;
			Optional<NatureOfBusinessMasterEntity> optional = nobmr.findById(id);
			if (optional.isPresent()) {
				//log.info("id -{}",id);
				nature = optional.get();
				List<NatureOfBusinessMasterEntity> checkName = nobmr.check(nobdto.getName().toUpperCase());
				for (NatureOfBusinessMasterEntity entity : checkName) {
					if (entity.getName().equalsIgnoreCase(nobdto.getName())) {
						if(id.equals(entity.getId())) {
							check = false;
						}else	
						check = true;

					} else {
						check = false;
					}
				}

				if (check) {
					String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
					response.setMessage("This Nature of Business " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Nature of Business - {} Already Exist");
				} else {
					nature.setName(nobdto.getName());
					
//					nature.setCode(nobdto.getCode());
					nature.setStatus(nobdto.getStatus());
					nature.setRemarks(nobdto.getRemarks());
					NatureOfBusinessMasterEntity afterUpdate = nobmr.save(nature);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(nobdto.getName() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- NatureOfBusinessServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- NatureOfBusinessServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- NatureOfBusinessServiceImpl.update() END -----> ");
		return response;
	}

	public BaseDTO deleteNature(UUID id) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started  NatureOfBusinessServiceImpl.deleteNature ===>");
		try {
			response =  nobdao.deleteNature(id);
			
		}catch(Exception e) {
			log.error(" Exception deleteNature.deleteNature " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<===Started NatureOfBusinessServiceImpl.deleteNature ===>");
		return response;
		
	}
	
	public BaseDTO getAllNatureOfBusinesslazy(PaginationRequestDTO pageData) {
		BaseDTO response=new BaseDTO();
		//log.info("<===Started NatureOfBusinessServiceImpl. getAllNatureOfBusinesslazy ===>");
		try {
			response =  nobdao.getAllNatureOfBusinesslazy(pageData);
			
		}catch(Exception e) {
			log.error(" Exception getAllNatureOfBusinesslazy.deleteNature " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended  NatureOfBusinessServiceImpl.getAllNatureOfBusinesslazy ===>");
		return response;	
	}

	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started NatureOfBusinessServiceImpl.getAllActive()===>");
		try {
			response =  nobdao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception NatureOfBusinessServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended NatureOfBusinessServiceImpl.getAllActive() ===>");
		return response;
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started NatureOfBusinessServiceImpl.softDelete()===>");
		try {
			NatureOfBusinessMasterEntity Nobmentity = nobmr.getOne(id);
			if (Nobmentity != null) {
				if (Nobmentity.getStatus() == true) {
					Nobmentity.setStatus(false);
					Nobmentity = nobmr.save(Nobmentity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Nature Of Business Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Nature Of Business Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- NatureOfBusinessServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Nature Of Business Master Details");
		}
		//log.info("<=== Ended NatureOfBusinessServiceImpl.softDelete() ===>");
		return response;
	}
	
}


