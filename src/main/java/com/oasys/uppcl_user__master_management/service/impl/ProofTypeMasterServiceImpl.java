package com.oasys.uppcl_user__master_management.service.impl;

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
import com.oasys.uppcl_user__master_management.dao.ProofTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.ProofTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.ProofTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ProofTypeMasterService;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class ProofTypeMasterServiceImpl implements ProofTypeMasterService {

	@Autowired
	ProofTypeMasterDao proofTypeMasterDao;
	
	@Autowired
	ProofTypeMasterRepository proofTypeMasterRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ObjectMapper mapper;
	
	
	public BaseDTO getAll()
	{
		BaseDTO baseDTO=new BaseDTO();
		log.info("<===Started ProofMasterServiceImpl.getAll()===>");
		try
		{
		baseDTO=proofTypeMasterDao.getAll();
		}
		catch(Exception e)
		{
		baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
		baseDTO.setMessage( msg);
		}
		return baseDTO;
	}


	public BaseDTO create(ProofTypeMasterDTO proofTypeDTO) {
		BaseDTO response = new BaseDTO();
		log.info("<===Started ProofTypeMasterServiceImpl.create() ===>");
		String message = "";
		
		try {
			ProofTypeMasterEntity proofType = proofTypeMasterDao.findByProofTypeName(proofTypeDTO.getName());
			if (proofType != null) {
				log.error("Proof Type - {} Already Exist");
			//	message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Proof Type Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			}
			ProofTypeMasterEntity proofTypeMaster = new ProofTypeMasterEntity();
			proofTypeMaster.setProofTypeName(proofTypeDTO.getName());
			proofTypeMaster.setStatus(proofTypeDTO.getStatus());
			proofTypeMaster.setDescription(proofTypeDTO.getDescription());
			proofTypeMaster = proofTypeMasterDao.save(proofTypeMaster);
			if (proofTypeMaster == null) {
				response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
				message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				log.error(message + "  - {} ", proofTypeDTO);
			} else {
				message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(proofTypeMaster.getProofTypeName() + " "+ message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				log.info(proofTypeMaster.getProofTypeName() + " "+ message);
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<--- ProofTypeMasterServiceImpl.create() exception ---> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<--- ProofTypeMasterServiceImpl.create() exception ---> {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		log.info("<=== Ended ProofTypeMasterServiceImpl.create() ===>");
		return response;
	}

	public BaseDTO delete(UUID id) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<===Started ProofTypeMasterServiceImpl.delete() ===>");
	try
	{
			baseDTO = proofTypeMasterDao.delete(id);
	}
	catch(Exception e)
	{
		log.error(" Exception ProofTypeMasterServiceImpl.delete()" + e);
		baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	}
		//log.info("<=== Ended ProofTypeMasterServiceImpl.delete() ===>");
		return baseDTO;
	}
	
	public BaseDTO update(UUID id, ProofTypeMasterDTO proofTypeMasterDTO) {
		//log.info(" <----- ProofTypeMasterServiceImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		ProofTypeMasterEntity proofTypeMasterEntity = new ProofTypeMasterEntity();
		try {
			boolean check = false;
	
			Optional<ProofTypeMasterEntity> optional = proofTypeMasterRepository.findById(id);
			if (optional.isPresent()) {
				proofTypeMasterEntity = optional.get();
				List<ProofTypeMasterEntity> checkCode = proofTypeMasterRepository.findByName(proofTypeMasterDTO.getName());
				for (ProofTypeMasterEntity entity : checkCode) {
					if (entity.getProofTypeName().equalsIgnoreCase(proofTypeMasterDTO.getName())) {
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
					response.setMessage("This Proof Type " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Proof Type Name -{} Already Exists",proofTypeMasterDTO.getName());
				} else {
					proofTypeMasterEntity.setProofTypeName(proofTypeMasterDTO.getName());
					proofTypeMasterEntity.setDescription(proofTypeMasterDTO.getDescription());
					proofTypeMasterEntity.setStatus(proofTypeMasterDTO.getStatus());
				ProofTypeMasterEntity afterUpdate = proofTypeMasterRepository.save(proofTypeMasterEntity);
					
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(proofTypeMasterDTO.getName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Proof Type - {} Updated Successfully");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- ProofTypeMasterServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- ProofTypeMasterServiceImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- ProofTypeMasterServiceImpl.update() END -----> ");
		return response;
	}

	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ProofTypeMasterServiceImpl.getById()===>");
		String message = "";
		try {
			Optional<ProofTypeMasterEntity> proofTypeMaster = proofTypeMasterDao.getById(id);
			if (proofTypeMaster.isPresent()) {
				ProofTypeMasterDTO proofTypeDTO = new ProofTypeMasterDTO();
				proofTypeDTO.setDescription(proofTypeMaster.get().getDescription());
				proofTypeDTO.setId(proofTypeMaster.get().getId());
				proofTypeDTO.setName(proofTypeMaster.get().getProofTypeName());
				proofTypeDTO.setStatus(proofTypeMaster.get().getStatus());
				response.setResponseContent(proofTypeDTO);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully get Details");
			} else {
				log.error(message);
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------ProofTypeMasterServiceImpl.getById()------> Exception", e);
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofTypeMasterServiceImpl.getById() ===>");
		return response;
	}

	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<=== Started ProofTypeMasterServiceImpl.getAllLazy()===>");
		try {
			baseDTO = proofTypeMasterDao.getAllLazy(requestData);
		} catch (Exception e) {
			log.error(" Exception ProofTypeMasterServiceImpl.getAllLazy() " + e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofTypeMasterServiceImpl.getAllLazy() ===>");
		return baseDTO;
	}
	
	public BaseDTO getAllActive() {
		BaseDTO baseDTO = new BaseDTO();
		//log.info("<=== Started ProofTypeMasterServiceImpl.getAllActive()===>");
		try {
			baseDTO = proofTypeMasterDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception ProofTypeMasterServiceImpl.getAllActive() " + e);
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofTypeMasterServiceImpl.getAllActive() ===>");
		return baseDTO;
	}
	
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ProofTypeMasterServiceImpl.softDelete()===>");
		try {
			ProofTypeMasterEntity proofTypeMasterEntity = proofTypeMasterRepository.getOne(id);
			if (proofTypeMasterEntity != null) {
				if (proofTypeMasterEntity.getStatus() == true) {
					proofTypeMasterEntity.setStatus(false);
					proofTypeMasterEntity = proofTypeMasterRepository.save(proofTypeMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Proof Type Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Proof Type Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ProofTypeMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Proof Type Master Details");
		}
		//log.info("<=== Ended ProofTypeMasterServiceImpl.softDelete() ===>");
		return response;
	}

	public BaseDTO findByProofTypeNames(List<String> proofTypeNames) {
		BaseDTO baseDTO = new BaseDTO();
		log.info(" findByProofTypeNames :: {} ", proofTypeNames.toString());
		List<ProofTypeMasterEntity> proofTypes = null;
		try{
			proofTypes =  proofTypeMasterRepository.findByProofTypeNames(proofTypeNames);
		}
		catch(Exception e) {
			log.error("<------ProofTypeMasterDaoImpl.findByProofTypeNames()------> exception {}", e);
			baseDTO.setMessage("Failed to get proof types.");
			baseDTO.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		baseDTO.setMessage("Record retrived successfully!");
		baseDTO.setResponseContents(proofTypes);
		baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return baseDTO;
	}
	
	
	
}
