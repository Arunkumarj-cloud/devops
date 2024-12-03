package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.MerchantTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.MerchantTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.MerchantTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.MerchantTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class MerchantTypeMasterDaoImpl implements MerchantTypeMasterDao {

	@Autowired
	MerchantTypeMasterRepository merchantTypeRepository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MessageSource messageSource;

	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== MerchantTypeMasterDaoImpl.getAll() Started ====>");
		List<MerchantTypeMasterEntity> merchantTypeList = null;
		try {
			merchantTypeList = merchantTypeRepository.findAll();
			if (merchantTypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(merchantTypeList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get all Merchant Type details Fetched Successfully");
			} else {
				//log.warn("Merchant Type List Empty");
				response.setMessage("Merchant Type List Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception MerchantTypeMasterDaoImpl.getAll() ==>", e);
		}
		//log.info("<==== MerchantTypeMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public BaseDTO create(MerchantTypeMasterDTO merchantTypeDTO) {
		//log.info("<== Started MerchantTypeMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		MerchantTypeMasterEntity merchantTypeMasterEntity = null;
		try {
			if (merchantTypeDTO.getAgentType() != "" && merchantTypeDTO.getAgentType()!=null) {
				//log.info(" Agent Type {} - Is Not Null - {} -Is Not Empty", merchantTypeDTO.getAgentType());
					//log.info(" Description {} - Is Not Null - {} -Is Not Empty", merchantTypeDTO.getDescription());
					if (merchantTypeDTO.getStatus() != null) {
						//log.info(" status {} - Is Not Null ", merchantTypeDTO.getStatus());
						//Optional<MerchantTypeMasterEntity> optional = merchantTypeRepository.findByAgentType(merchantTypeDTO.getAgentType().toUpperCase());
						merchantTypeMasterEntity = merchantTypeRepository.findByAgentTypeIgnoreCase(merchantTypeDTO.getAgentType());
						
						if(merchantTypeMasterEntity != null) {
							String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
							response.setMessage("This User Role Type " +msg);
							//response.setResponseContent(optional.get());
							response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
							//log.info("Merchant Type  Already Exist",merchantTypeDTO.getAgentType());
						}
						
						else {
						merchantTypeMasterEntity = new MerchantTypeMasterEntity();
						merchantTypeMasterEntity.setAgentType(merchantTypeDTO.getAgentType());
						merchantTypeMasterEntity.setStatus(merchantTypeDTO.getStatus());
						merchantTypeMasterEntity.setDescription(merchantTypeDTO.getDescription());
						merchantTypeMasterEntity.setRemarks(merchantTypeDTO.getRemarks());
						merchantTypeRepository.save(merchantTypeMasterEntity);
						String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
						response.setMessage("User Role Type " +msg);
						response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
						//log.info("Successfully Added",merchantTypeDTO.getAgentType());
						}
					} else {
						response.setMessage("Status Required");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("Status Required");
					}
			
			} else {
				response.setMessage("Agent Type is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Agent Type is Required");
			}
		}catch(DataIntegrityViolationException e) {
			log.error("<-------- MerchantTypeMasterDaoImpl.create() exception- {}", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<-------- MerchantTypeMasterDaoImpl.create() exception-{} ", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<== Ended MerchantTypeMasterDaoImpl.create() ==>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, MerchantTypeMasterDTO merchantTypeMasterDTO) {
		//log.info(" <----- MerchantTypeMasterService.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<MerchantTypeMasterEntity> optional = merchantTypeRepository.findById(id);
			if (optional.isPresent()) {
				MerchantTypeMasterEntity agentType = new MerchantTypeMasterEntity();
				agentType = optional.get();
				List<MerchantTypeMasterEntity> checkCode = merchantTypeRepository.check(merchantTypeMasterDTO.getAgentType().toUpperCase());
				for (MerchantTypeMasterEntity entity : checkCode) {
					if (entity.getAgentType().equalsIgnoreCase(merchantTypeMasterDTO.getAgentType())) {
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
					response.setMessage("This User Role Type " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Agent Type - {} already Exists",merchantTypeMasterDTO.getAgentType());
				} else {
					agentType.setAgentType(merchantTypeMasterDTO.getAgentType());
					agentType.setDescription(merchantTypeMasterDTO.getDescription());
					agentType.setStatus(merchantTypeMasterDTO.getStatus());
					agentType.setRemarks(merchantTypeMasterDTO.getRemarks());
					MerchantTypeMasterEntity afterUpdate = merchantTypeRepository.save(agentType);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(merchantTypeMasterDTO.getAgentType() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("  Agent type - {} Updated Successfully",merchantTypeMasterDTO.getAgentType()
							,merchantTypeMasterDTO.getDescription());*/
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- MerchantTypeMasterService.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- MerchantTypeMasterService.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- MerchantTypeMasterService.update() END -----> ");
		return response;
	}
	
	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  MerchantTypeMasterDaoImpl.delete() ===> ");
		BaseDTO response = new BaseDTO();
		MerchantTypeMasterEntity master = new MerchantTypeMasterEntity();
		try {
			master = merchantTypeRepository.getOne(id);
			if (master.getId() != null) {
				merchantTypeRepository.delete(master);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Merchant Type Details Deleted Successfully");
			} else {
				//log.error("merchant ID does not exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- MerchantTypeMasterDaoImpl.delete()  exception----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<====Ended MerchantTypeMasterDaoImpl.delete() ===> ");
		return response;
	}

	public BaseDTO getById(UUID id) {
		//log.info("<==== Started MerchantTypeMasterDaoImpl.getById() ===> ");
		BaseDTO response = new BaseDTO();
		MerchantTypeMasterEntity merchantTypeMaster = null;
		try {
			merchantTypeMaster = merchantTypeRepository.getOne(id);
			if (merchantTypeMaster.getId() != null) {
				response.setResponseContent(merchantTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("get Details successfully");
			} else {
				//log.error("merchantType id does not Exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------MerchantTypeMasterDaoImpl.getById()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended MerchantTypeMasterDaoImpl.getById() ===> ");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------MerchantTypeMasterService.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<MerchantTypeMasterEntity> bankTypeMaster = null;
		try {
			bankTypeMaster = merchantTypeRepository.getAllactive();
			if (bankTypeMaster != null) {
				response.setResponseContents(bankTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Merchant type Get all active Details Fetched successfully");
			} else {
				//log.error("No merchant Type is Active");
				response.setMessage("No merchant Type is Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------MerchantTypeMasterService.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------MerchantTypeMasterService.getAllActive()------> Ended");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		log.info("<== MerchantTypeMasterService.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<MerchantTypeMasterEntity> merhcantTypMasterList = null;
		List<MerchantTypeMasterEntity> contentList = new ArrayList<MerchantTypeMasterEntity>();
		Pageable pageRequest;
		try {
			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					merhcantTypMasterList = merchantTypeRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					merhcantTypMasterList = merchantTypeRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					merhcantTypMasterList = merchantTypeRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					merhcantTypMasterList = merchantTypeRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (merhcantTypMasterList.isEmpty()) {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found..");
			} else {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(merhcantTypMasterList.getNumberOfElements());
				response.setTotalRecords(merhcantTypMasterList.getTotalElements());
				response.setTotalPages(merhcantTypMasterList.getTotalPages());
				for (MerchantTypeMasterEntity merchantTypMaster : merhcantTypMasterList) {
					contentList.add(merchantTypMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully get Lazy list details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- MerchantTypeMasterService.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== MerchantTypeMasterService.getAllLazy() Ended ==>");
		return response;
	}
	
	private MerchantTypeMasterEntity updatedValues(MerchantTypeMasterEntity merchantTypeMasterEntity, MerchantTypeMasterDTO merchantTypeMasterDTO) {
		MerchantTypeMasterEntity agentType = merchantTypeMasterEntity;
		merchantTypeMasterEntity.setAgentType(merchantTypeMasterDTO.getAgentType());
		merchantTypeMasterEntity.setDescription(merchantTypeMasterDTO.getDescription());
		merchantTypeMasterEntity.setStatus(merchantTypeMasterDTO.getStatus());
		return merchantTypeMasterEntity;
	} 

}
