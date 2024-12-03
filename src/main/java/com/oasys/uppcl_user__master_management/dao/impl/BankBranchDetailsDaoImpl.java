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
import com.oasys.uppcl_user__master_management.dao.BankBranchDetailsDao;
import com.oasys.uppcl_user__master_management.dto.BankBranchDetailsDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;
import com.oasys.uppcl_user__master_management.entity.DistrictMasterEntity;
import com.oasys.uppcl_user__master_management.entity.StateMasterEntity;
import com.oasys.uppcl_user__master_management.repository.BankBranchDetailsRepository;
import com.oasys.uppcl_user__master_management.repository.BankNameRepository;
import com.oasys.uppcl_user__master_management.repository.DistrictMasterRepository;
import com.oasys.uppcl_user__master_management.repository.StateMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankBranchDetailsDaoImpl implements BankBranchDetailsDao{

	@Autowired
	BankBranchDetailsDao bankBranchDetailsDao;
	
	@Autowired
	BankBranchDetailsRepository bankBranchDetailsRepository;
	
	@Autowired
	BankNameRepository bankNameRepository;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
    StateMasterRepository stateMasterRepository;
	
	@Autowired
	DistrictMasterRepository districtMasterRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO create(BankBranchDetailsDTO bankBranchDetailsDTO) {
	//	log.info(" <-----BankBranchDetailsDaoImpl.create() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		BankBranchDetailsEntity bankBranchName = new BankBranchDetailsEntity();
		try {
			Optional<BankBranchDetailsEntity> optional = bankBranchDetailsRepository.findByIfsc(bankBranchDetailsDTO.getIfscCode());
			if (optional.isPresent()) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Branch IFSC Code Already Exist");
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				//log.info("Branch IFSC Code - {}  Already Exist", bankBranchDetailsDTO.getIfscCode());
				return response;
			}
			bankBranchName.setBankId(bankBranchDetailsDTO.getBankId());
			bankBranchName.setIfscCode(bankBranchDetailsDTO.getIfscCode());
			bankBranchName.setStatus(bankBranchDetailsDTO.getStatus());
			bankBranchName.setStateCode(bankBranchDetailsDTO.getStateCode());
			bankBranchName.setBranchName(bankBranchDetailsDTO.getBranchName());
			bankBranchName.setStateName(bankBranchDetailsDTO.getStateName());
			bankBranchName.setDistrictName(bankBranchDetailsDTO.getDistrictName());
			bankBranchName.setBankName(bankBranchDetailsDTO.getBankName());
			bankBranchName.setBankNameId(bankBranchDetailsDTO.getBankNameId());
			//bankBranchName.setStdCode(Long.parseLong(bankBranchDetailsDTO.getStdCode()));
			//bankBranchName.setLandlineNumber(Long.parseLong(bankBranchDetailsDTO.getLandlineNumber()));
			bankBranchName.setAddress(bankBranchDetailsDTO.getAddress());
			bankBranchName.setBranchContactNumber(bankBranchDetailsDTO.getBranchContactNumber());
			bankBranchName.setRemarks(bankBranchDetailsDTO.getRemarks());
			//bankBranchName.setBranchContactNumber(Long.parseLong(bankBranchDetailsDTO.getBranchContactNumber()));
			/*if(bankBranchDetailsDTO.getBranchContactNumber()!=null && 
					!bankBranchDetailsDTO.getBranchContactNumber().toString().trim().equalsIgnoreCase("")){
				//bankBranchName.setBranchContactNumber(Long.parseLong(bankBranchDetailsDTO.getBranchContactNumber()));
			} else {
				bankBranchName.setBranchContactNumber(null);
			}
			*/
		    bankBranchDetailsRepository.save(bankBranchName);
		    message = messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Bank Branch  Created Successfully");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("Bank Branch - {} Created Successfully");
		} catch (DataIntegrityViolationException e) {
			log.error("BankBranchDetailsDaoImpl.create() - {}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Faild To Add");
		} catch (Exception e) {
			log.warn("BankBranchDetailsDaoImpl.create() -{} ", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Failed To Add");
			log.error("Exception "+e);
		}
		//log.info(" <-----BankBranchDetailsDaoImpl.create() Service END -----> ");
		return response;
	}
	

	@Override
	public BaseDTO update(UUID id, BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info(" <-----BankBranchDetailsDaoImpl.update() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		String message = "";
		try {
			boolean checkIfscCode = false;
			Optional<BankBranchDetailsEntity> optional = bankBranchDetailsRepository.findById(id);
			//log.info("id");
			if (optional.isPresent()) {
				
			BankBranchDetailsEntity bankBranchName = optional.get();
			List<BankBranchDetailsEntity> checkCode = bankBranchDetailsRepository.findByIfscCode(bankBranchDetailsDTO.getIfscCode());
			for (BankBranchDetailsEntity bankBranchEntity : checkCode) {
					if (bankBranchEntity.getIfscCode().equalsIgnoreCase(bankBranchName.getIfscCode())) {
						if(id.equals(bankBranchEntity.getId())) {

						checkIfscCode = false;
						//break;
					} else {
						checkIfscCode = true;
					}
				} else {
					checkIfscCode = false;
				}
			}
			if (checkIfscCode) {
				message = messageSource.getMessage(ResponseConstant.ALREADY_EXISTS, null, Locale.US);
				response.setMessage("This Branch IFSC Code " + message);
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				log.info("This Bank Branch IFSC Code Already exist");
				return response;
			}
			bankBranchName.setBankId(bankBranchDetailsDTO.getBankId());
			bankBranchName.setIfscCode(bankBranchDetailsDTO.getIfscCode());
			bankBranchName.setStatus(bankBranchDetailsDTO.getStatus());
			bankBranchName.setStateCode(bankBranchDetailsDTO.getStateCode());
			bankBranchName.setBranchName(bankBranchDetailsDTO.getBranchName());
			bankBranchName.setStateName(bankBranchDetailsDTO.getStateName());
			bankBranchName.setDistrictName(bankBranchDetailsDTO.getDistrictName());
			bankBranchName.setBankNameId(bankBranchDetailsDTO.getBankNameId());
			bankBranchName.setBankName(bankBranchDetailsDTO.getBankName());
			//bankBranchName.setStdCode(Long.parseLong(bankBranchDetailsDTO.getStdCode()));
			//bankBranchName.setLandlineNumber(Long.parseLong(bankBranchDetailsDTO.getLandlineNumber()));
			bankBranchName.setAddress(bankBranchDetailsDTO.getAddress());	
			bankBranchName.setBranchContactNumber(bankBranchDetailsDTO.getBranchContactNumber());
			bankBranchName.setRemarks(bankBranchDetailsDTO.getRemarks());
			/*if(bankBranchDetailsDTO.getBranchContactNumber()!=null && !bankBranchDetailsDTO.getBranchContactNumber().toString().trim().equalsIgnoreCase("")){
				//bankBranchName.setBranchContactNumber(Long.parseLong(bankBranchDetailsDTO.getBranchContactNumber()));
			} else {
				bankBranchName.setBranchContactNumber(null);
			}
			*/
			bankBranchName = bankBranchDetailsRepository.save(bankBranchName);

			message = messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage("Bank Branch " + message);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			//log.info("Bank Branch - {} Updated Successfully");
	
		} else {
			String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			//log.warn("No Record Found..");
		}
	} catch (DataIntegrityViolationException e) {
		log.error("<---- BankBranchDetailsDaoImpl.update() ----> EXCEPTION", e);
		response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());

	} catch (Exception e) {
		log.error("<---- BankNameDaoImpl.update() ----> EXCEPTION", e);
		response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
	}

		//log.info(" <-----BankBranchDetailsDaoImpl.update() Service END -----> ");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("BankBranchDetailsDaoImpl.getById() Service STARTED ");
		BaseDTO response = new BaseDTO();
		BankBranchDetailsDTO dto = new BankBranchDetailsDTO();
		String message = "";
		try {
			Optional<BankBranchDetailsEntity> bankBranch = bankBranchDetailsRepository.findById(id);
			if (bankBranch.isPresent()) {
				dto = mapper.convertValue(bankBranch, BankBranchDetailsDTO.class);
				BankBranchDetailsEntity singlebranch = bankBranch.get();
				//log.info("bankBranch"+ bankBranch);
				//log.info("singlebranch"+ singlebranch);
				String dist="";
				try {
				StateMasterEntity state = stateMasterRepository.findByStateCode(singlebranch.getStateCode());
				dto.setStateId(state.getId());
				//log.info("StateMasterEntity"+ state);
				BankBranchDetailsEntity singlebranch1 = bankBranch.get();
				//log.info("StateMasterEntity----1"+ singlebranch1);
				dist= singlebranch1.getDistrictName();
				}catch(Exception e) {
					log.warn("StateMasterEntity"+ e);
					
				}
				
				try {
				 DistrictMasterEntity district = districtMasterRepository.findByDistrictCode(dist);
				dto.setDistrictId(district.getId());
				//log.info("DistrictMasterEntity"+ district);
				}catch(Exception e) {
					
					log.warn("DistrictMasterEntity"+ e);
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
			log.error("BankBranchDetailsDaoImpl.getById() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			message = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(message);
		}
		//log.info(" BankBranchDetailsDaoImpl.getById() Service END ");
		return response;
	}

//	public BaseDTO getAll() {
//		BaseDTO response = new BaseDTO();
//		log.info("<==== BankBranchDetailsDaoImpl.getAll() Started ====>");
//		List<BankBranchDetailsEntity> list = null;
//		try {
//
//			list = bankBranchDetailsRepository.findAll();
//			if (list.size() != 0) {
//				
//				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//				response.setMessage(msg);
//				response.setResponseContents(list);
//				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
//				log.info("Bank Branch Get All Details Fetched Successfully");
//			} else {
//				response.setMessage("Bank Branch List is Empty");
//				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
//			}
//		} catch (Exception e) {
//			log.error("<== Exception BankBranchDetailsDaoImpl.getAll() ==>", e);
//		}
//		log.info("<==== BankBranchDetailsDaoImpl.getAll() Ended ====>");
//		return response;
//	}
	@Override
	public List<BankBranchDetailsEntity> getAll(){
		//log.info("<==== BankBranchDetailsDaoImpl.getAll() Started ====>");
		BaseDTO response = new BaseDTO();
		List<BankBranchDetailsEntity> bankBranchDetailsEntity = new ArrayList<BankBranchDetailsEntity>();
		try {
			List<BankBranchDetailsEntity> bankBranchDetailsEntity1=bankBranchDetailsRepository.findAll();
			for(BankBranchDetailsEntity bankbranch : bankBranchDetailsEntity1) {
				BankBranchDetailsEntity bankbranch1 = mapper.convertValue(bankbranch, BankBranchDetailsEntity.class);
				bankBranchDetailsEntity.add(bankbranch1);
			}
			if(bankBranchDetailsEntity.isEmpty()) {
				response.setMessage("no record.....");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				//log.warn("no record found......");
			}else {
				response.setResponseContent(bankBranchDetailsEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("successfully get bank branch details....");
			}
		}
		catch(Exception e) {
			log.error("<== Exception BankBranchDetailsDaoImpl.getAll() ==>", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		return bankBranchDetailsEntity;
		
	}
	

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info(" <----- BankBranchDetailsDaoImpl.getLazyList() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDetailsDao.getLazyList(pageData);
		} catch (Exception e) {
			log.error("<---- BankBranchDetailsDaoImpl.getLazyList()-{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchDetailsDaoImpl.getLazyList() Service END -----> ");
		return response;
	}

//	@Override
//	public BaseDTO delete(UUID id) {
//		log.info(" <----- BankBranchDetailsDaoImpl.delete() Service STARTED -----> ");
//		BaseDTO response = new BaseDTO();
//		try {
//			response = bankBranchDetailsDao.delete(id);
//		} catch (Exception e) {
//			log.error("<---- BankBranchDetailsDaoImpl.delete() -{}", e.getMessage());
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
//			response.setMessage(msg);
//		}
//		log.info(" <----- BankBranchDetailsDaoImpl.delete() Service END -----> ");
//		return response;
//	}
	@Override
	public BaseDTO delete(UUID id) {
		//log.info(" <----- BankBranchDetailsDaoImpl.delete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			BankBranchDetailsEntity bankBranchDetailsEntity = bankBranchDetailsRepository.getOne(id);
		    if(bankBranchDetailsEntity != null) {
		    	bankBranchDetailsRepository.delete(bankBranchDetailsEntity);
		    	//log.info("BankBranchdetails-   {} deleted successfully", bankBranchDetailsEntity.getId());
		    	String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US);
		    	response.setMessage(msg);
		    	response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		    	//log.info("bankbranchdetailsdetails deleted successfully.....");
		    }
		    else {
		    	response.setMessage(" no record found...");
		    	response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
		    	//log.warn("no record found..");
		    }
		}
		catch(Exception e) {
			log.error("<---- BankBranchDetailsDaoImpl.delete() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null, Locale.US);
			response.setMessage(msg);
		}
		//log.info(" <----- BankBranchDetailsDaoImpl.delete() Service END -----> ");
		return response;
		
		
	}
	
	@Override
	public BaseDTO softDelete(UUID id) {
		//log.info(" <----- BankBranchDetailsDaoImpl.softDelete() Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDetailsDao.softDelete(id);
		} catch (Exception e) {
			log.error("<---- BankBranchDetailsDaoImpl.softDelete() -{}", e.getMessage());
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank Branch");
		}
		//log.info(" <----- BankBranchDetailsDaoImpl.softDelete() Service END -----> ");
		return response;
	}

	public BaseDTO getAllActive() {
		//log.info("<------BankBranchDetailsDaoImpl.getAllActive()------> Started");
		BaseDTO response = new BaseDTO();
		List<BankBranchDetailsEntity> bankBranchDetailsEntity = null;
		try {
			bankBranchDetailsEntity = bankBranchDetailsRepository.getAllActive();
			if (bankBranchDetailsEntity != null) {
				response.setResponseContents(bankBranchDetailsEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Bank Branch Get All Active Details Fetched Ssuccessfully");
			} else {
				//log.error("No Bank Branch Details are Active");
				response.setMessage("No Bank Branch Details are Active");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<------BankBranchDetailsDaoImpl.getAllActive()------> exception", e);
			String msg = messageSource.getMessage(ResponseConstant.GET_ALL_ACTIVE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<------BankBranchDetailsDaoImpl.getAllActive()------> Ended");
		return response;
	}

	public BaseDTO getAllLazyWithIfscCode(PaginationRequestDTO pageData) {
		//log.info("<== BankBranchDetailsDaoImpl.getAllLazyWithIfscCode() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<BankBranchDetailsEntity> bankBranchDetails = null;
		List<BankBranchDetailsEntity> contentList = new ArrayList<BankBranchDetailsEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- BankBranchDetailsDaoImpl.getAllLazyWithIfscCode() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (bankBranchDetails != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(bankBranchDetails.getNumberOfElements());
				response.setTotalRecords(bankBranchDetails.getTotalElements());
				response.setTotalPages(bankBranchDetails.getTotalPages());
				for (BankBranchDetailsEntity passbookTypMaster : bankBranchDetails) {
					contentList.add(passbookTypMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- BankBranchDetailsDaoImpl.getAllLazyWithIfscCode() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== BankBranchDetailsDaoImpl.getAllLazyWithIfscCode() Ended ==>");
		return response;
	}

	@Override
	public BaseDTO getBankBranchDetails(String ifscCode) {
		//log.info("<== BankBranchDetailsDaoImpl.getBankBranchDetails() Started ==>");
		BaseDTO response = new BaseDTO();
		List<BankBranchDetailsEntity> responseList = new ArrayList<BankBranchDetailsEntity>();
		try {
			responseList = bankBranchDetailsRepository.getBankBranchDetails(ifscCode);
			if (!responseList.isEmpty()) {
				
				BankBranchDetailsEntity singel = responseList.get(0);
				singel.setAddress(singel.getAddress());
				responseList.set(0, singel);
				
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setMessage("Bank Branch Details Fetched Successfully.");
				response.setResponseContents(responseList);
			} else {
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
				response.setMessage("No Records Found.");
			}

		} catch (Exception e) {
			log.error("<=== ===>", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("No Records Found.");
		}
		//log.info("<== BankBranchDetailsDaoImpl.getBankBranchDetails() Ended ==>");
		return response;
	}
	
	public BaseDTO getAllLazyWithBranchName(PaginationRequestDTO pageData) {
		//log.info("<== BankBranchDetailsDaoImpl.getAllLazyWithBranchName() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<BankBranchDetailsEntity> bankBranchDetails = null;
		List<BankBranchDetailsEntity> contentList = new ArrayList<BankBranchDetailsEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- BankBranchDetailsDaoImpl.getAllLazyWithBranchName() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search1(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search1(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (bankBranchDetails != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(bankBranchDetails.getNumberOfElements());
				response.setTotalRecords(bankBranchDetails.getTotalElements());
				response.setTotalPages(bankBranchDetails.getTotalPages());
				for (BankBranchDetailsEntity passbookTypMaster : bankBranchDetails) {
					contentList.add(passbookTypMaster);
				}
				
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- BankBranchDetailsDaoImpl.getAllLazyWithBranchName() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== BankBranchDetailsDaoImpl.getAllLazyWithBranchName() Ended ==>");
		return response;
	}


	public BaseDTO getBranchList(PaginationRequestDTO pageData) {
		//log.info("<== BankBranchDetailsDaoImpl.getBranchList() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<BankBranchDetailsEntity> bankBranchDetails = null;
		List<BankBranchDetailsEntity> contentList = new ArrayList<BankBranchDetailsEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- BankBranchDetailsDaoImpl.getBranchList() ---------- >>>>>>>");

			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search2(pageRequest, pageData.getSearch().toUpperCase());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					bankBranchDetails = bankBranchDetailsRepository.search2(pageRequest, pageData.getSearch().toUpperCase());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					bankBranchDetails = bankBranchDetailsRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.DESC, pageData.getSortField())));
				}
			}
			if (bankBranchDetails != null) {
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				response.setNumberOfElements(bankBranchDetails.getNumberOfElements());
				response.setTotalRecords(bankBranchDetails.getTotalElements());
				response.setTotalPages(bankBranchDetails.getTotalPages());
				for (BankBranchDetailsEntity passbookTypMaster : bankBranchDetails) {
					contentList.add(passbookTypMaster);
				}
		
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Successfully Get Details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- BankBranchDetailsDaoImpl.getBranchList() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== BankBranchDetailsDaoImpl.getBranchList() Ended ==>");
		return response;
	}
}
