package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
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
import com.oasys.uppcl_user__master_management.dao.BankBranchDao;
import com.oasys.uppcl_user__master_management.dto.BankBranchDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchMasterEntity;
import com.oasys.uppcl_user__master_management.entity.BankNameMasterEntity;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BankBranchService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankBranchServiceImpl implements BankBranchService {
	@Autowired
	BankBranchDao bankBranchDao;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ObjectMapper mapper;

	@Override
	public BaseDTO create(BankBranchDTO bankBranchDTO) {
		//log.info(" <-----BankBranchServiceImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			Optional<BankBranchMasterEntity> optional = bankBranchDao.findByIfscCode(bankBranchDTO.getBranchIfscCode());
			if (optional.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Branch IFSC Code " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Branch IFSC Code - {}  Already Exist", bankBranchDTO.getBranchIfscCode());
				return response;
			}
			BankBranchMasterEntity bankBranch = mapper.convertValue(bankBranchDTO, BankBranchMasterEntity.class);
			bankBranch.setLandlineNumber(Long.parseLong(bankBranchDTO.getLandlineNumber()));
			bankBranch.setStdCode(Long.parseLong(bankBranchDTO.getStdCode()));
			bankBranch = bankBranchDao.save(bankBranch);
			message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Bank Branch " + message);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("Bank Branch - {} Created Successfully", bankBranchDTO.getBranchName());
		} catch (DataIntegrityViolationException e) {
			log.error("BankBranchServiceImpl.create() - {}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("BankBranchServiceImpl.create() -{} ", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <-----BankBranchServiceImpl.create() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, BankBranchDTO bankBranchDTO) {
		//log.info(" <-----BankBranchServiceImpl.update() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean checkIfscCode = false;
			Optional<BankBranchMasterEntity> optional = bankBranchDao.findById(id);
			if (!optional.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
				return response;
			}
			BankBranchMasterEntity bankBranchName = optional.get();
			List<BankBranchMasterEntity> checkCode = bankBranchDao.getByExcepted(id);
			for (BankBranchMasterEntity bankBranchEntity : checkCode) {
				if (bankBranchEntity.getId() != bankBranchName.getId()) {
					if (bankBranchEntity.getBranchIfscCode().equalsIgnoreCase(bankBranchName.getBranchIfscCode())) {
						checkIfscCode = true;
						break;
					} else {
						checkIfscCode = false;
					}
				} else {
					checkIfscCode = false;
				}
			}
			if (checkIfscCode) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Branch IFSC Code " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Bank Branch IFSC Code Already exist");
				return response;
			}
			bankBranchName.setBranchName(bankBranchDTO.getBranchName());
			bankBranchName.setBranchIfscCode(bankBranchDTO.getBranchIfscCode());
			bankBranchName.setStatus(bankBranchDTO.getStatus());
			bankBranchName.setBankNameId(bankBranchDTO.getBankNameId());
			bankBranchName.setDistrictId(bankBranchDTO.getDistrictId());
			bankBranchName.setAddress(bankBranchDTO.getAddress());
			bankBranchName.setBranchContactNumber(bankBranchDTO.getBranchContactNumber());
			bankBranchName.setStdCode(Long.parseLong(bankBranchDTO.getStdCode()));
			bankBranchName.setLandlineNumber(Long.parseLong(bankBranchDTO.getLandlineNumber()));
			bankBranchName = bankBranchDao.save(bankBranchName);

			message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Bank Branch " + message);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("Bank Branch - {} Updated Successfully", bankBranchDTO.getBranchName());
		} catch (DataIntegrityViolationException e) {
			log.error("<---- BankBranchServiceImpl.update() - {}", e.getCause());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.update() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <-----BankBranchServiceImpl.update() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("BankBranchServiceImpl.getById() Service STARTED ");
		BaseDTO response = new BaseDTO();
		BankBranchDTO dto = new BankBranchDTO();
		String message = "";
		try {
			Optional<BankBranchMasterEntity> bankBranch = bankBranchDao.findById(id);
			if (bankBranch.isPresent()) {
				dto = mapper.convertValue(bankBranch, BankBranchDTO.class);
				if(dto.getStdCode() !=null) {
					if (dto.getStdCode().charAt(0) != '0') {
						dto.setStdCode("0" + dto.getStdCode());
						//log.info(dto.getStdCode().charAt(0));
					}
				}					
				response.setResponseContent(dto);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get BankBranch Details..");
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("BankBranchDaoImpl.getById() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" BankBranchServiceImpl.getById() Service END ");
		return response;
	}

	@Override
	public BaseDTO getAll() {
		//log.info(" <----- BankBranchServiceImpl.getAll() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		List<BankBranchDTO> list = new ArrayList<BankBranchDTO>();
		try {
			List<BankBranchMasterEntity> bankBranch = bankBranchDao.getAll();
			if (bankBranch.isEmpty()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
				return response;
			}
			for (BankBranchMasterEntity entity : bankBranch) {
				BankBranchDTO dto = new BankBranchDTO();
				if (entity.getStdCode() != null) {
					dto = mapper.convertValue(entity, BankBranchDTO.class);
					if (dto.getStdCode().charAt(0) != '0') {
						dto.setStdCode("0" + dto.getStdCode());
					}
					list.add(dto);
				}
			}
			response.setResponseContents(list);
			//log.info("bank branch get All Active  sucessfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,
					Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.getAll() -{} ", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- BankBranchServiceImpl.getAll() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		//log.info(" <----- BankBranchServiceImpl.getAllActive() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		List<BankBranchDTO> list = new ArrayList<BankBranchDTO>();
		try {
			List<BankBranchMasterEntity> bankBranch = bankBranchDao.getAllActive();
			if (bankBranch.isEmpty()) {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
			for (BankBranchMasterEntity entity : bankBranch) {
				BankBranchDTO dto = new BankBranchDTO();
				if (entity.getStdCode() != null) {
					dto = mapper.convertValue(entity, BankBranchDTO.class);
					if (dto.getStdCode().charAt(0) != '0') {
						dto.setStdCode("0" + dto.getStdCode());
					}
					list.add(dto);
				}
			}
			response.setResponseContents(list);
			//log.info("Bank Branch get All Details Sucessfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.getAllActive() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- BankBranchServiceImpl.getAllActive() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO requestData) {
		//log.info(" <----- BankBranchServiceImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDao.getLazyList(requestData);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.getLazyList()-{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchServiceImpl.getLazyList() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BankBranchServiceImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDao.delete(id);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.delete() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchServiceImpl.delete() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankBranchServiceImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.softDelete() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank Branch");
		}
		//log.info(" <----- BankBranchServiceImpl.softDelete() Service END -----> ");
		return response;
	}

	public BaseDTO getByBankNameId(UUID id) {
		//log.info(" <----- BankBranchServiceImpl.getByBankNameId() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			BankNameMasterEntity bankName = new BankNameMasterEntity();
			bankName.setId(id);

			List<BankBranchMasterEntity> bankBranch = bankBranchDao.getByBankNameId(bankName);
			if (!bankBranch.isEmpty()) {
				response.setResponseContents(bankBranch);
				message = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Bank Branch Details..");
			} else {
				message = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
				response.setMessage(message);
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankBranchServiceImpl.getByBankNameId() - {}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" <----- BankBranchServiceImpl.getByBankNameId() Service END -----> ");
		return response;
	}


}
