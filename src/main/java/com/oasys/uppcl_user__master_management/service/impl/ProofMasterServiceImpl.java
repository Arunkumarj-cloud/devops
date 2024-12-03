package com.oasys.uppcl_user__master_management.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.ProofMasterDao;
import com.oasys.uppcl_user__master_management.dao.ProofTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.ProofMasterDTO;
import com.oasys.uppcl_user__master_management.dto.ProofMasterResponseDTO;
import com.oasys.uppcl_user__master_management.dto.ProofTypeProofMappingDTO;
import com.oasys.uppcl_user__master_management.entity.ProofMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ProofTypeProofMappingEntity;
import com.oasys.uppcl_user__master_management.repository.ProofMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.ProofMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProofMasterServiceImpl implements ProofMasterService {

	@Autowired
	ProofMasterDao proofDao;
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProofMasterRepository proofMasterRepository;
	@Autowired
	ProofTypeMasterDao proofTypeMasterDao;
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO create(ProofMasterDTO proofMasterDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ProofMasterServiceImpl.create() {} ",proofMasterDTO);
		ProofTypeProofMappingDTO mappingDTO = new ProofTypeProofMappingDTO();
		ProofTypeProofMappingEntity mappingEntity = new ProofTypeProofMappingEntity();
		boolean validateProofType = false;
		String message = "";
		try {
			//log.info("size {} ",proofMasterDTO.getProofType().size());
			if(proofMasterDTO.getProofType().isEmpty() || proofMasterDTO.getProofType().size() < 0) {
				 message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null,Locale.US);
				response.setMessage("ProofType " +  message);
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Proof Type {}",message);
				return response;
			}
			for(ProofTypeMasterEntity proofTypeMaster : proofMasterDTO.getProofType()) {
				if(proofTypeMaster.getId() != null) {
				if(!proofTypeMasterDao.getById(proofTypeMaster.getId()).isPresent()) {
					validateProofType = false;
					break;
				}else
					validateProofType = true;
			}else {
				 message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null,Locale.US);
				response.setMessage("ProofType " +  message);
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Proof Type {} ",message);
				return response;
			}
			}if(!validateProofType) {
				 message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage("ProofType " +  message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("Proof Type {}"+ message);
				return response;
			}
			if (validateProofName(proofMasterDTO.getProofName())) {
				//log.info("Proof Name - Is Validate {}..",proofMasterDTO.getProofName());
				ProofMasterEntity proofList = objectMapper.convertValue(proofMasterDTO, ProofMasterEntity.class);
				proofList = proofDao.save(proofList);

				for (ProofTypeMasterEntity proofType : proofMasterDTO.getProofType()) {
					mappingDTO.setProofId(proofList);
					mappingDTO.setStatus(true);
					mappingDTO.setDescription(proofMasterDTO.getDescription());
					proofMasterDTO.setProofTypeId(proofType);
					mappingDTO.setProofTypeId(proofMasterDTO.getProofTypeId());
					mappingDTO.setRemarks(proofMasterDTO.getRemarks());
					mappingDTO.setCreatedDate(new Date());
					boolean validateMapping = proofDao.validate(mappingDTO);
					if(!validateMapping) {
						mappingEntity = objectMapper.convertValue(mappingDTO, ProofTypeProofMappingEntity.class);
						mappingEntity = proofDao.saveProofTypeProofMapping(mappingEntity);
					}					
				}
				if (proofList.getId() == null && mappingEntity.getId() == null) {
					 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.error("Failure to Add");
				} else {
					 message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(message);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Added");
				}
			} else {
				 message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
				response.setMessage("This Proof Name " +  message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Proof Name is Already Exists..");
			}
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(message);
			log.error("Excetion in ProofMasterServiceImpl.create(): {} ",  e);
		}catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			 message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(message);
			log.error("Excetion in ProofMasterServiceImpl.create(): {} " , e);
		}
		//log.info("<=== Ended ProofMasterServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, ProofMasterDTO proofMasterDTO) {
		//log.info("<=== Started ProofMasterServiceImpl.update() {} and {} ", id, proofMasterDTO);
		BaseDTO response = new BaseDTO();
		ProofTypeProofMappingDTO mappingDTO = new ProofTypeProofMappingDTO();
		ProofTypeProofMappingEntity mapping = new ProofTypeProofMappingEntity();
		boolean validateProofType = false;
		String message = "";
		try {
			boolean validateProofName = false;
			ProofMasterEntity proof = proofDao.getById(id);
			//log.info("Proof master Id {}", id);
			if (proof.getId() == null) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				log.warn("No Record Found..");
				return response;
			}
			//log.info("size {} ", proofMasterDTO.getProofType().size());
			if (proofMasterDTO.getProofType().isEmpty() || proofMasterDTO.getProofType().size() < 0) {
				message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null, Locale.US);
				response.setMessage("ProofType " + message);
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				log.warn("Proof Type {}", message);
				return response;
			}
			for (ProofTypeMasterEntity proofTypeMaster : proofMasterDTO.getProofType()) {
				if (proofTypeMaster.getId() != null) {
					if (!proofTypeMasterDao.getById(proofTypeMaster.getId()).isPresent()) {
						validateProofType = false;
						break;
					} else
						validateProofType = true;
				} else {
					message = messageSource.getMessage(ResponseConstant.EMPTY_DATA, null, Locale.US);
					response.setMessage("ProofType " + message);
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("Proof Type {} ", message);
					return response;
				}
			}
			if (!validateProofType) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage("ProofType " + message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("Proof Type {}" + message);
				return response;
			}
			ProofMasterEntity beforeUpdate = updatedValues(proof, proofMasterDTO);
			List<ProofMasterEntity> except = proofDao.exceptId(id);
			for (ProofMasterEntity name : except) {
				if (name.getId() != beforeUpdate.getId()) {
					if (name.getProofName().equalsIgnoreCase(beforeUpdate.getProofName())) {
						validateProofName = true;
						break;
					} else {
						validateProofName = false;
					}
				} else {
					validateProofName = false;
				}
			}
			if (!validateProofName) {
				ProofMasterEntity afterUpdate = proofDao.save(beforeUpdate);
				List<ProofTypeProofMappingEntity> deleteMapping = new ArrayList<ProofTypeProofMappingEntity>();
				deleteMapping = proofDao.getByProofId(proof);
				log.warn(deleteMapping);
				if (!deleteMapping.isEmpty()) {
					for (ProofTypeProofMappingEntity mappent : deleteMapping) {
						proofDao.deleteProofMapping(mappent);
					}
				}
				for (ProofTypeMasterEntity ent : proofMasterDTO.getProofType()) {
					mappingDTO.setProofId(afterUpdate);
					mappingDTO.setStatus(true);
					proofMasterDTO.setProofTypeId(ent);
					mappingDTO.setProofTypeId(proofMasterDTO.getProofTypeId());
					mappingDTO.setRemarks(proofMasterDTO.getRemarks());
					mappingDTO.setModifiedDate(new Date());

					boolean validateMapping = proofDao.validate(mappingDTO);
					if (!validateMapping ) {
						mapping = objectMapper.convertValue(mappingDTO, ProofTypeProofMappingEntity.class);
						mapping = proofDao.saveProofTypeProofMapping(mapping);
					}
				}
				message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Updated");
			} else {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Proof Name " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.warn("Proof Name is Already Exists..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error(" Exception ProofMasterServiceImpl.update() {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.update() {} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info("<=== Ended ProofMasterServiceImpl.update() ===>");
		return response;
	}
	

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<=== Started ProofMasterServiceImpl.getById()===>");
		BaseDTO response = new BaseDTO();
		try {
			ProofMasterEntity proofEntity = proofDao.getById(id);
			//log.info("Proof master Id {}",id);
			ProofMasterDTO proof = objectMapper.convertValue(proofEntity, ProofMasterDTO.class);
			List<ProofTypeProofMappingEntity> mapping = proofDao.getByProofId(proofEntity);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for (ProofTypeProofMappingEntity entity : mapping) {
				Map<String, Object> proofTypeName = new HashMap<String, Object>();
				ProofTypeMasterEntity proofType = entity.getProofTypeId();
				proofTypeName.put("name", proofType.getProofTypeName());
				proofTypeName.put("id",  proofType.getId());
				proofTypeName.put("remarks",  proofType.getRemarks());
				proofTypeName.put("createdDate",  proofType.getCreatedDate());
				proofTypeName.put("modifiedDate",  proofType.getModifiedDate());
				list.add(proofTypeName);
			}
			ProofMasterResponseDTO ressponseDTO = objectMapper.convertValue(proof, ProofMasterResponseDTO.class);
			ressponseDTO.setProofTypeName(list);
			if (ressponseDTO.getId() == null) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");

			} else {
				response.setResponseContent(ressponseDTO);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Proof Details..",id);
			}

		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofMasterServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ProofMasterServiceImpl.getAll()===>");
		List<ProofMasterResponseDTO> proofMasterResponseDTO = new ArrayList<ProofMasterResponseDTO>();
		try {
			List<ProofMasterEntity> proof = proofDao.getAll();
			for (ProofMasterEntity proofList : proof) {
				List<ProofTypeProofMappingEntity> mapping = proofDao.getByProofId(proofList);
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for (ProofTypeProofMappingEntity entity : mapping) {
					Map<String, Object> proofTypeName = new HashMap<String, Object>();
					ProofTypeMasterEntity proofType = entity.getProofTypeId();
					proofTypeName.put("name", proofType.getProofTypeName());
					proofTypeName.put("id",  proofType.getId());
					proofTypeName.put("remarks",  proofType.getRemarks());
					proofTypeName.put("createdDate",  proofType.getCreatedDate());
					proofTypeName.put("modifiedDate",  proofType.getModifiedDate());
					list.add(proofTypeName);
				}
				ProofMasterResponseDTO proofResponse = objectMapper.convertValue(proofList,
						ProofMasterResponseDTO.class);
				proofResponse.setProofTypeName(list);
				proofMasterResponseDTO.add(proofResponse);
			}
			if (proofMasterResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(proofMasterResponseDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
				//log.info("Sucessfully Get All Proof Details");
			}
		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended ProofMasterServiceImpl.getAll() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ProofMasterServiceImpl.getAllActive()===>");
		List<ProofMasterResponseDTO> proofMasterResponseDTO = new ArrayList<ProofMasterResponseDTO>();
		try {
			List<ProofMasterEntity> proof = proofDao.getAllActive();

			for (ProofMasterEntity proofList : proof) {
				List<ProofTypeProofMappingEntity> mapping = proofDao.getByProofId(proofList);
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for (ProofTypeProofMappingEntity entity : mapping) {
					Map<String, Object> proofTypeName = new HashMap<String, Object>();
					ProofTypeMasterEntity proofType = entity.getProofTypeId();
					proofTypeName.put("name", proofType.getProofTypeName());
					proofTypeName.put("id",  proofType.getId());
					proofTypeName.put("remarks",  proofType.getRemarks());
					proofTypeName.put("createdDate",  proofType.getCreatedDate());
					proofTypeName.put("modifiedDate",  proofType.getModifiedDate());
					list.add(proofTypeName);
				}
				ProofMasterResponseDTO proofResponse = objectMapper.convertValue(proofList,
						ProofMasterResponseDTO.class);
				proofResponse.setProofTypeName(list);
				proofMasterResponseDTO.add(proofResponse);
			}
			if (proofMasterResponseDTO.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage( msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContents(proofMasterResponseDTO);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage( msg);
				//log.info("Successfully Get AllActive  Proof Details..");
			}
		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.getAllActive() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofMasterServiceImpl.getAllActive() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started ProofMasterServiceImpl.delete() ===>");
		try {
			ProofMasterEntity proofEntity = proofDao.getById(id);
			//log.info("Proof Id {}",id);
			if (proofEntity != null) {
				List<ProofTypeProofMappingEntity> mappingEntity = proofDao.getByProofId(proofEntity);
				if (mappingEntity.isEmpty()) {
					proofDao.delete(id);
				} else {
					for (ProofTypeProofMappingEntity deleteMaping : mappingEntity) {
						proofDao.deleteProofMapping(deleteMaping);
					}
				}
				List<ProofTypeProofMappingEntity> proofMapping = proofDao.getByProofId(proofEntity);
				if (proofMapping.isEmpty()) {
					proofDao.delete(id);
				}
				ProofMasterEntity proofMasterEntity = proofDao.getById(id);
				if (proofMasterEntity.getId() == null) {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage( msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted",id);
				} else {
					String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage(msg);
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					log.error("Unable to Delete Proof..");
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			}
		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofMasterServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info("<=== Started ProofMasterServiceImpl.getAllLazy() ===>");
		BaseDTO response = new BaseDTO();
		List<Map<String, Object>> totalList = null;
		List<Map<String, Object>> map = new ArrayList<>();
		long totalRecords = 0;
		int pageNo = 0;
		int totalPages = 0;
		//log.info("<=== Started ProofMasterServiceImpl.getAllLazy()===>");
		try {
			totalList = proofDao.getLazyList(requestData);

			if (totalList != null) {
				for (Map<String, Object> entity : totalList) {
					for (Map.Entry<String, Object> entry : entity.entrySet()) {
						if (entry.getKey().equals("proofName")) {
							map.add(entity);
						}
					}
				}
				for (Map<String, Object> entity : totalList) {

					if (entity.containsKey("totalRecords")) {
						totalRecords = Integer.parseInt((entity.get("totalRecords").toString()));
					}
					if (entity.containsKey("pageNo")) {
						pageNo = Integer.parseInt((entity.get("pageNo").toString()));
					}
					if (entity.containsKey("totalPages")) {
						totalPages = Integer.parseInt((entity.get("totalPages").toString()));
					}
				}
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

			if (totalList != null && totalList.size() > 0) {
				response.setResponseContents(map);
				response.setTotalPages(totalPages);
				response.setPageNo(pageNo);
				response.setTotalRecords(totalRecords);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Lazy List Details For Proof");
			} else {
				//log.warn("No Records Found");
				response.setMessage("No Records Found");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofMasterServiceImpl.getAllLazy() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getByProofType(UUID id) {
		//log.info("<=== Started ProofMasterServiceImpl.getByProofType()===>");
		BaseDTO response = new BaseDTO();
		try {			
			List<ProofTypeProofMappingEntity> mapping = proofDao.getByProofType(id);
			//log.info("proof type Id",id);
			List<Map<String,Object>> proofType = new ArrayList<Map<String,Object>>();
			
			for (ProofTypeProofMappingEntity entity : mapping) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", entity.getId());
				map.put("status", entity.getStatus());
				map.put("proofTypeId", entity.getProofTypeId().getId());
				map.put("proofTypeName", entity.getProofTypeId().getProofTypeName());
				map.put("proofId", entity.getProofId().getId());
				map.put("proofName", entity.getProofId().getProofName());
				
				//ProofTypeProofMappingDTO ressponseDTO = objectMapper.convertValue(entity, ProofTypeProofMappingDTO.class);				
				proofType.add(map);
			}			
			if (proofType.isEmpty()) {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.error("No Record Found..");
			} else {
				response.setResponseContent(proofType);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Proof Details..",id);
			}

		} catch (Exception e) {
			log.error(" Exception ProofMasterServiceImpl.getByProofType() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended ProofMasterServiceImpl.getByProofType() ===>");
		return response;
	}

	private ProofMasterEntity updatedValues(ProofMasterEntity proofMaster, ProofMasterDTO proofDTO) {
		ProofMasterEntity proof = proofMaster;
		proof.setProofName(proofDTO.getProofName());
		proof.setDescription(proofDTO.getDescription());
		proof.setStatus(proofDTO.getStatus());
		proof.setRemarks(proofDTO.getRemarks());
		proof.setModifiedDate(new Date());
		return proof;
	}
	private boolean validateProofName(String name) {

		ProofMasterEntity entity = proofDao.validateProofName(name);
		if (entity == null) {
			log.warn("Valid ------>");
			return true;
		} else {
			//log.error("Invalid ------>");
			return false;
		}
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started ProofMasterServiceImpl.softDelete()===>");
		try {
			ProofMasterEntity proofMasterEntity = proofMasterRepository.getOne(id);
			if (proofMasterEntity != null) {
				if (proofMasterEntity.getStatus() == true) {
					proofMasterEntity.setStatus(false);
					proofMasterEntity = proofMasterRepository.save(proofMasterEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Proof Master Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Proof Master Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- ProofMasterServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Proof Master Details");
		}
		//log.info("<=== Ended ProofMasterServiceImpl.softDelete() ===>");
		return response;
	}

	@Override
	public BaseDTO getProofNameById(UUID id) {
		//log.info("Get ProofName By Id Start - {}",id);
		BaseDTO response = new BaseDTO();
		try {
			String str = proofMasterRepository.getProofNameById(id);
			response.setMessage("Data Retrieved Sucessfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getErrorCode());
			response.setResponseContent(str);
		}catch (Exception e) {
			log.error("<---- Unable To Get Detail ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Get Proof Master Details");
		}
		//log.info("Get ProofName By Id End - {}",id);
		return response;
	}
	
}