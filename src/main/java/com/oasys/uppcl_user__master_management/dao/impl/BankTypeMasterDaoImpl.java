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
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BankTypeMasterDao;
import com.oasys.uppcl_user__master_management.dto.BankTypeMasterDTO;
import com.oasys.uppcl_user__master_management.entity.BankTypeMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankTypeMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BankTypeMasterDaoImpl implements BankTypeMasterDao  {
	
	@Autowired
	BankTypeMasterRepository bankTypeMasterRepository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== BankTypeMasterDaoImpl.getAll() Started ====>");
		List<BankTypeMasterEntity> banktypeList = null;
		try {

			banktypeList = bankTypeMasterRepository.findAll(Sort.by(Direction.ASC, "type"));
			if (banktypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setResponseContents(banktypeList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Get All Bank Type Details Fetched Successfully");
			} else {
				response.setMessage("Bank Type List is Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Bank Type List is Empty");
			}
		} catch (Exception e) {
			log.error("<== Exception BankTypeMasterDaoImpl.getAll() ==>", e);
		}
		//log.info("<==== BankTypeMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public BaseDTO create(BankTypeMasterDTO bankTypeDto) {
		//log.info("<== Started BankTypeMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		BankTypeMasterEntity bankTypeMasterEntity = new BankTypeMasterEntity();
		try {
			if (bankTypeDto.getType() != "" && bankTypeDto.getType() != null) {
				//log.info(" Bank type {} - Is Not Null - {} -Is Not Empty", bankTypeDto.getType());
				if (bankTypeDto.getStatus() != null) {
					//log.info(" status {} - Is Not Null", bankTypeDto.getType());
					//Optional<BankTypeMasterEntity> optional = bankTypeMasterRepository.findByBankTypeIgnoreCase(bankTypeDto.getType());
					bankTypeMasterEntity = bankTypeMasterRepository.findByTypeIgnoreCase(bankTypeDto.getType());
					if(bankTypeMasterEntity != null) {
						String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
						response.setMessage("This Bank Type " +msg);
						//response.setResponseContent(optional.get());
						response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
						//log.info("Bank Type Already Exist",bankTypeDto.getType());
					}
					else {	
						BankTypeMasterEntity bankTypeMasterEntity1 = new BankTypeMasterEntity();

						bankTypeMasterEntity1.setType(bankTypeDto.getType());
						bankTypeMasterEntity1.setStatus(bankTypeDto.getStatus());
					bankTypeMasterRepository.save(bankTypeMasterEntity1);
					String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
					response.setMessage( bankTypeDto.getType() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Added - {}", bankTypeDto.getType(),bankTypeDto.getStatus());
					}
				} else {
					response.setMessage("Status is Required");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("Status is Required");
				}
			} else {
				response.setMessage("Bank Type is Required");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("Bank Type is Required");
			}
		}catch(DataIntegrityViolationException e) {
			log.error("<-------- BankTypeMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<-------- BankTypeMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info("<== Ended BankTypeMasterDaoImpl.create() ==>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  BankTypeMasterDaoImpl.delete() ===> ");
		BaseDTO response = new BaseDTO();
		BankTypeMasterEntity master = new BankTypeMasterEntity();
		try {
			master = bankTypeMasterRepository.getOne(id);
			if (master.getId() != null) {
				//log.info("Bank type Id - {} ",id);
				bankTypeMasterRepository.delete(master);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Delete Bank Type Details ");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("No Record Found");
			}
		} catch (Exception e) {
			log.info("<---- BankTypeMasterDaoImpl.delete()  exception----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info("<====Ended BankTypeMasterDaoImpl.delete() ===> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<==== Started BankTypeMasterDaoImpl.getById() ===> ");
		BaseDTO response = new BaseDTO();
		BankTypeMasterEntity merchantTypeMaster = null;
		try {
			merchantTypeMaster = bankTypeMasterRepository.getOne(id);
			if (merchantTypeMaster.getId() != null) {
				response.setResponseContent(merchantTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get bank type Details ");
			} else {
				//log.error("No Record Found");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<------BankTypeMasterDaoImpl.getById()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage( msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended BankTypeMasterDaoImpl.getById() ===> ");
		return response;
	}

	public BaseDTO getAllLazy(PaginationRequestDTO pageData) {
		//log.info("<== BankTypeMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<BankTypeMasterEntity> bankMasterList = null;
		List<BankTypeMasterEntity> contentList = new ArrayList<BankTypeMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- BankTypeMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					bankMasterList = bankTypeMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					bankMasterList = bankTypeMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					bankMasterList = bankTypeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					bankMasterList = bankTypeMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}

			if (bankMasterList != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(bankMasterList.getNumberOfElements());
				response.setTotalRecords(bankMasterList.getTotalElements());
				response.setTotalPages(bankMasterList.getTotalPages());
				for (BankTypeMasterEntity bankTypMaster : bankMasterList) {
					contentList.add(bankTypMaster);
				}
				response.setResponseContents(contentList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully Get Lazy List Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- BankTypeMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== BankTypeMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------BankTypeMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<BankTypeMasterEntity> bankTypeMaster = null;
		try {
			bankTypeMaster = bankTypeMasterRepository.getAllactive(Sort.by(Direction.ASC, "type"));
			if (bankTypeMaster != null) {
				response.setResponseContents(bankTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			} else {
				//log.error("Bank Type is not Active");
				response.setMessage("Bank Type is not Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------BankTypeMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------BankTypeMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}
	
	public BaseDTO update(UUID id, BankTypeMasterDTO bankTypeMasterDTO) {
		//log.info(" <----- BankTypeMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<BankTypeMasterEntity> optional = bankTypeMasterRepository.findById(id);
			if (optional.isPresent()) {
				log.info("Bank Type  id -{}",id);	
				BankTypeMasterEntity bankType = new BankTypeMasterEntity();
				bankType = optional.get();
				List<BankTypeMasterEntity> checkCode = bankTypeMasterRepository.check(bankTypeMasterDTO.getType());
				for (BankTypeMasterEntity entity : checkCode) {
					if (entity.getType().equalsIgnoreCase(bankTypeMasterDTO.getType())) {
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
					response.setMessage("This Bank Type " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Bank type  - {} Already exists",bankTypeMasterDTO.getType());
				} else {
					bankType.setType(bankTypeMasterDTO.getType());
					bankType.setStatus(bankTypeMasterDTO.getStatus());
					BankTypeMasterEntity afterUpdate = bankTypeMasterRepository.save(bankType);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(bankTypeMasterDTO.getType() + " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("Bank type - {} Updated Successfully",  bankTypeMasterDTO.getType()
							,bankTypeMasterDTO.getStatus());*/
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BankTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- BankTypeMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- BankTypeMasterDaoImpl.update() END -----> ");
		return response;
	}
	


}
