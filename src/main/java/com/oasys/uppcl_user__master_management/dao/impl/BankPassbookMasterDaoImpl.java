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
import com.oasys.uppcl_user__master_management.dao.BankPassbookMasterDao;
import com.oasys.uppcl_user__master_management.dto.BankPassbookMasterDTO;
import com.oasys.uppcl_user__master_management.entity.BankPassbookMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankPassbookMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BankPassbookMasterDaoImpl implements BankPassbookMasterDao {

	@Autowired
	BankPassbookMasterRepository bankPassbookMasterRepository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MessageSource messageSource;

	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		//log.info("<==== BankPassbookMasterDaoImpl.getAll() Started ====>");
		List<BankPassbookMasterEntity> banktypeList = null;
		try {

			banktypeList = bankPassbookMasterRepository.findAll();
			if (banktypeList.size() != 0) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContents(banktypeList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Bank PassBook Get All Details Fetched Successfully");
			} else {
				response.setMessage("Bank passbook List is Empty");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<== Exception BankPassbookMasterDaoImpl.getAll() ==>", e);
		}
		//log.info("<==== BankPassbookMasterDaoImpl.getAll() Ended ====>");
		return response;
	}

	@Override
	public BaseDTO create(BankPassbookMasterDTO bankPassbookMasterDTO) {
		//log.info("<== Started BankPassbookMasterDaoImpl.create() ==>");
		BaseDTO response = new BaseDTO();
		BankPassbookMasterEntity bankPassbookMasterEntity = new BankPassbookMasterEntity();
		try {
				if (bankPassbookMasterDTO.getName() != "" && bankPassbookMasterDTO.getName()!=null) {
					//log.info(" Name {} - Is Not Null - {} -Is Not Empty", bankPassbookMasterDTO.getName());
					if (bankPassbookMasterDTO.getDescription() != "" && bankPassbookMasterDTO.getDescription()!=null) {
						//log.info("Description {} - Is Not Null - {} -Is Not Empty", bankPassbookMasterDTO.getDescription());
						if (bankPassbookMasterDTO.getStatus() != null) {
							//log.info(" Status {} - Is Not Null", bankPassbookMasterDTO.getStatus());
							Optional<BankPassbookMasterEntity> optional = bankPassbookMasterRepository.findByPassBookName(bankPassbookMasterDTO.getName());
							if(optional.isPresent()) {
								String msg = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null,Locale.US);
								response.setMessage(bankPassbookMasterDTO.getName() + " " +msg);
								response.setResponseContent(optional.get());
								response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
								//log.info(" Bank Pass Book Name -{} Already Existst",bankPassbookMasterDTO.getName() );
							
							}else {
							bankPassbookMasterEntity.setName(bankPassbookMasterDTO.getName());
							bankPassbookMasterEntity.setDescription(bankPassbookMasterDTO.getDescription());
							bankPassbookMasterEntity.setStatus(bankPassbookMasterDTO.getStatus());
							bankPassbookMasterRepository.save(bankPassbookMasterEntity);
							String msg = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
							response.setMessage( bankPassbookMasterDTO.getName() + " " + msg);
							response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
							//log.info("Successfully Added",bankPassbookMasterDTO.getName() ,bankPassbookMasterDTO.getDescription());
							}
						} else {
							response.setMessage("Status is Required");
							response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
							//log.warn("Status is Required");
						}
					} else {
						response.setMessage("Bank Passbook Description Required");
						response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
						//log.warn("Bank Passbook Description Required");
					}
				} else {
					response.setMessage("Bank Passbook Name is Required");
					response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
					//log.warn("Bank Passbook Name is Required");
				}
		}  catch(DataIntegrityViolationException e) {
			log.error("<-------- BankPassbookMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<-------- BankPassbookMasterDaoImpl.create() exception-------->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<== Ended BankPassbookMasterDaoImpl.create() ==>");
		return response;
	}



	public BaseDTO getById(UUID id) {
		//log.info("<==== Started BankPassbookMasterDaoImpl.getById() ===> ");
		BaseDTO response = new BaseDTO();
		BankPassbookMasterEntity master = new BankPassbookMasterEntity();
		try {
			master = bankPassbookMasterRepository.getOne(id);
			if (master.getId() != null) {
				response.setResponseContent(master);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info(" Get Bank Pass Book Details Fetched");
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.error("bank passbook id does not Exist");
			}
		} catch (Exception e) {
			log.error("<------BankPassbookMasterDaoImpl.getById()------> Exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<==== Ended BankPassbookMasterDaoImpl.getById() ===> ");
		return response;
	}

	public BaseDTO delete(UUID id) {
		//log.info("<==== Started  BankPassbookMasterDaoImpl.delete() ===> ");
		BaseDTO response = new BaseDTO();
		BankPassbookMasterEntity master = new BankPassbookMasterEntity();
		try {
			master = bankPassbookMasterRepository.getOne(id);
			if (master.getId() != null) {
				///log.info("Bank Pass Book Id",id);
				bankPassbookMasterRepository.delete(master);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Delete Bank PassBook Details");
			} else {
				//log.error("Bank passbook ID does not Exist");
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			}
		} catch (Exception e) {
			log.error("<---- BankPassbookMasterDaoImpl.delete()  exception----->", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<====Ended BankPassbookMasterDaoImpl.delete() ===> ");
		return response;
	}

	public BaseDTO getAllLazy(PaginationRequestDTO pageData) {
		//log.info("<== BankPassbookMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<BankPassbookMasterEntity> passbookMasterList = null;
		List<BankPassbookMasterEntity> contentList = new ArrayList<BankPassbookMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- BankPassbookMasterDaoImpl.getAllLazy() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					passbookMasterList = bankPassbookMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					passbookMasterList = bankPassbookMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					passbookMasterList = bankPassbookMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					passbookMasterList = bankPassbookMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (passbookMasterList != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(passbookMasterList.getNumberOfElements());
				response.setTotalRecords(passbookMasterList.getTotalElements());
				response.setTotalPages(passbookMasterList.getTotalPages());
				for (BankPassbookMasterEntity passbookTypMaster : passbookMasterList) {
					contentList.add(passbookTypMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- BankPassbookMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== BankPassbookMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------BankPassbookMasterDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<BankPassbookMasterEntity> passbookTypeMaster = null;
		try {
			passbookTypeMaster = bankPassbookMasterRepository.getAllactive();
			if (passbookTypeMaster != null) {
				response.setResponseContents(passbookTypeMaster);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Bank PassBook Get All Active Details Fetched Ssuccessfully");
			} else {
				//log.error("No Bank Passbook Details are Active");
				response.setMessage("No Bank Passbook Details are Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------BankPassbookMasterDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------BankPassbookMasterDaoImpl.getAllActive()------> Ended");
		return response;
	}
	

	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankPassbookMasterDaoImpl softDelete  STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankPassbookMasterEntity master = bankPassbookMasterRepository.getOne(id);
			if (master != null) {
				master.setStatus(false);
				bankPassbookMasterRepository.save(master);
				response.setMessage("Successfully Deleted");
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Deleted");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankPassbookMasterDaoImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank PassBook");
		}
		//log.info(" <----- BankPassbookMasterDaoImpl softDelete  END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, BankPassbookMasterDTO bankPassbookMasterDTO) {
		//log.info(" <----- BankPassbookMasterDaoImpl.update() STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			boolean check = false;
			Optional<BankPassbookMasterEntity> optional = bankPassbookMasterRepository.findById(id);
			if (optional.isPresent()) {
				//log.info("Bank pass Book Name id -{}",id);
				BankPassbookMasterEntity passbook = new BankPassbookMasterEntity();
				passbook = optional.get();
				List<BankPassbookMasterEntity> checkCode = bankPassbookMasterRepository.findByName(bankPassbookMasterDTO.getName());
				for (BankPassbookMasterEntity entity : checkCode) {
					if (entity.getName().equals(bankPassbookMasterDTO.getName())) {
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
					response.setMessage(bankPassbookMasterDTO.getName() + " " + msg  );
					response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
					//log.info("Bank Pass Book Name -{} Already Exists",bankPassbookMasterDTO.getName());
				} else {
					passbook.setName(bankPassbookMasterDTO.getName());
					passbook.setDescription(bankPassbookMasterDTO.getDescription());
					passbook.setStatus(bankPassbookMasterDTO.getStatus());
					BankPassbookMasterEntity afterUpdate = bankPassbookMasterRepository.save(passbook);
					response.setResponseContent(afterUpdate);
					String msg = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null,
							Locale.US);
					response.setMessage(bankPassbookMasterDTO.getName()+ " " + msg);
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					/*log.info("Municipality - {} Updated Successfully", bankPassbookMasterDTO.getName()
							,bankPassbookMasterDTO.getDescription());*/
				}
			} else {
				String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BankPassbookMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

		} catch (Exception e) {
			log.error("<---- BankPassbookMasterDaoImpl.update() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info(" <----- BankPassbookMasterDaoImpl.update() END -----> ");
		return response;
	}
	
}
