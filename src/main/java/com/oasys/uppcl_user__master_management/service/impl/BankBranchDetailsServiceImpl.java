package com.oasys.uppcl_user__master_management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.BankBranchDetailsDao;
import com.oasys.uppcl_user__master_management.dto.BankBranchDetailsDTO;
import com.oasys.uppcl_user__master_management.dto.BankBranchResDTO;
import com.oasys.uppcl_user__master_management.dto.BankIfscResponseDTO;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;
import com.oasys.uppcl_user__master_management.repository.BankBranchDetailsRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.service.BankBranchDetailsService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BankBranchDetailsServiceImpl implements BankBranchDetailsService{


	@Autowired
	BankBranchDetailsDao bankBranchDetailsDao;
	
	@Autowired
	BankBranchDetailsRepository bankBranchDetailsRepository;

	@Autowired
	MessageSource messageSource;
	
//	@Override
//	public BaseDTO getAll() {
//		BaseDTO response = new BaseDTO();
//		log.info("<===Started BankBranchDetailsServiceImpl.getAll()===>");
//		try {
//			response = bankBranchDetailsDao.getAll();
//		} catch (Exception e) {
//			log.error(" Exception BankBranchDetailsServiceImpl.getAll() " + e);
//			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
//			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
//			response.setMessage(msg);
//		}
//		log.info("<===Ended BankBranchDetailsServiceImpl.getAll()===>");
//		return response;
//	}
	
	@Override
	public BaseDTO getAll() {
		BaseDTO response = new BaseDTO();
		List<BankBranchDetailsEntity> bankBranchDetailsEntity=null;
		try {
			bankBranchDetailsEntity=bankBranchDetailsDao.getAll();
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setResponseContents(bankBranchDetailsEntity);
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			
		}
		catch(Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getAll() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<===Ended BankBranchDetailsServiceImpl.getAll()===>");
		return response;
		
	}
	

	@Override
	public BaseDTO create(BankBranchDetailsDTO bankBranchDetailsDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankBranchDetailsServiceImpl.create() ===>");
		try {
			response = bankBranchDetailsDao.create(bankBranchDetailsDTO);
			
		}catch(DataIntegrityViolationException e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankBranchDetailsServiceImpl.create():" + e);
			
		}catch (Exception e) {
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			log.error("Excetion in BankBranchDetailsServiceImpl.create():" + e);
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.create() ===>");
		return response;
	}

	@Override
	public BaseDTO update(UUID id, BankBranchDetailsDTO bankBranchDetailsDTO) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankBranchDetailsServiceImpl.update() ===>");
		try {
			response = bankBranchDetailsDao.update(id, bankBranchDetailsDTO);
		}catch(DataIntegrityViolationException e) {
			log.error(" Exception BankBranchDetailsServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.update()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.UPDATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.update() ===>");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankBranchDetailsServiceImpl.getById()===>");
		try {
			response = bankBranchDetailsDao.getById(id);
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getById() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getById() ===>");
		return response;
	}

	@Override
	public BaseDTO delete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankBranchDetailsServiceImpl.delete() ===>");
		try {
			response = bankBranchDetailsDao.delete(id);
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.delete()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.delete() ===>");
		return response;
	}

	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		//log.info("<===Started BankBranchDetailsServiceImpl.getAllActive() ===>");
		try {
			response = bankBranchDetailsDao.getAllActive();
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getAllActive()" + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getAllActive() ===>");
		return response;
	}

	
	@Override
	public BaseDTO softDelete(UUID id) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankBranchDetailsServiceImpl.softDelete()===>");
		try {
			BankBranchDetailsEntity bankBranchDetailsEntity = bankBranchDetailsRepository.getOne(id);
			if (bankBranchDetailsEntity != null) {
				if (bankBranchDetailsEntity.getStatus() == true) {
					bankBranchDetailsEntity.setStatus(false);
					bankBranchDetailsEntity = bankBranchDetailsRepository.save(bankBranchDetailsEntity);
					response.setMessage("Successfully Deleted");
					response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
					//log.info("Successfully Deleted");
				} else {
					response.setMessage("Unable to Delete Bank Branch Details..");
					response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
					//log.warn("Unable to Delete Bank Branch Details..");
				}
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
				//log.warn("No Record Found..");
			}
		} catch (Exception e) {
			log.error("<---- BankBranchDetailsServiceImpl.softDelete() ----> EXCEPTION", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to Delete Bank Branch Details");
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.softDelete() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getLazyListWithIfscCode(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started BankBranchDetailsServiceImpl.getAllLazy()===>");
		try {
			response = bankBranchDetailsDao.getAllLazyWithIfscCode(pageData);
			
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getAllLazy() ===>");
		return response;
	}

	@Override
	public BaseDTO getBankBranchDetails(BankBranchDetailsDTO bankBranchDetailsDTO) {
		//log.info("<=== Started BankBranchDetailsServiceImpl.getBankBranchDetails() ===>");
		BaseDTO response = new BaseDTO();
		try {
			response = bankBranchDetailsDao.getBankBranchDetails(bankBranchDetailsDTO.getIfscCode());
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getBankBranchDetails() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getBankBranchDetails() ===>");
		return response;
	}
		
	@Override
	public BaseDTO getLazyListWithBranchName(PaginationRequestDTO pageData) {
		//log.info("<=== Started BankBranchDetailsServiceImpl.getLazyListWithBranchName() ===>");
		BaseDTO response = new BaseDTO();

		try {
			response = bankBranchDetailsDao.getAllLazyWithBranchName(pageData);
			
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getLazyListWithBranchName() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getLazyListWithBranchName() ===>");
		return response;
	}
	
	
	@Override
	public BaseDTO getBranchList(PaginationRequestDTO pageData) {
		//log.info("<=== Started BankBranchDetailsServiceImpl.getBranchList() ===>");
		BaseDTO response = new BaseDTO();

		try {
			response = bankBranchDetailsDao.getBranchList(pageData);
			
		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getBranchList() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getBranchList() ===>");
		return response;
	}
	
	
	@Override
	public BaseDTO getBankdetailsByIfscCode(BankBranchDetailsDTO bankBranchDetailsDTO) {
	   	//log.info("<=== Started BankBranchDetailsServiceImpl.getBankdetailsByIfscCode() ===>");
		BaseDTO response = new BaseDTO();
		ObjectMapper obj = new ObjectMapper();
		BankBranchResDTO bankBranchDetailsDTO1 = new BankBranchResDTO();
        BankBranchDetailsEntity res = new BankBranchDetailsEntity();
		try {
			RestTemplate restTemplate = new RestTemplate();
		String url = "http://api.techm.co.in/api/v1/ifsc/" + bankBranchDetailsDTO.getIfscCode().toUpperCase();
			//log.info("ifscapi url - {}" + url);
			String ifscResponse = restTemplate.getForObject(url, String.class);
			//log.info("ifscResponse - {} " + ifscResponse);
			
			bankBranchDetailsDTO1 = obj.readValue(ifscResponse.toLowerCase(), BankBranchResDTO.class);
			
			Optional<BankBranchDetailsEntity> optional = bankBranchDetailsRepository.findByIfsc(bankBranchDetailsDTO.getIfscCode());
			
			res = optional.get();
			 res.setAddress(bankBranchDetailsDTO1.getData().getAddress());
			 res.setBranchContactNumber(bankBranchDetailsDTO1.getData().getContact());
		     res = bankBranchDetailsRepository.save(res);
			
			BankIfscResponseDTO responsedata = getreponsedata(bankBranchDetailsDTO1);
			response.setResponseContent(responsedata);
			
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setMessage("Bank Branch Details Fetched Successfully.");

		} catch (Exception e) {
			log.error(" Exception BankBranchDetailsServiceImpl.getBankdetailsByIfscCode() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended BankBranchDetailsServiceImpl.getBankdetailsByIfscCode() ===>");
		return response;
	}
	

	private BankIfscResponseDTO getreponsedata(BankBranchResDTO bankBranchDetailsDTO1) {

		BankIfscResponseDTO response = new BankIfscResponseDTO();
		try {
			response.setAddress(bankBranchDetailsDTO1.getData().getAddress().substring(0, 1).toUpperCase()
					+ bankBranchDetailsDTO1.getData().getAddress().substring(1));
			response.setBankName(bankBranchDetailsDTO1.getData().getBank().substring(0, 1).toUpperCase()
					+ bankBranchDetailsDTO1.getData().getBank().substring(1));
		response.setBranchContactNumber(bankBranchDetailsDTO1.getData().getContact().substring(0,1).toUpperCase()
				+ bankBranchDetailsDTO1.getData().getContact().substring(1));
			response.setBranchName(bankBranchDetailsDTO1.getData().getBranch().substring(0, 1).toUpperCase()
					+ bankBranchDetailsDTO1.getData().getBranch().substring(1));
			response.setDistrictName(bankBranchDetailsDTO1.getData().getDistrict().substring(0, 1).toUpperCase()
					+ bankBranchDetailsDTO1.getData().getDistrict().substring(1));
			response.setIfscCode(bankBranchDetailsDTO1.getData().getIfsc().toUpperCase());
			response.setStateName(bankBranchDetailsDTO1.getData().getState().substring(0, 1).toUpperCase()
					+ bankBranchDetailsDTO1.getData().getState().substring(1));

		} catch (Exception e) {

		}

		return response;
	}


	@Override
	public List<BankBranchDetailsEntity> listAll() {
		List<BankBranchDetailsEntity> bankBranchDetailsEntities = new ArrayList<BankBranchDetailsEntity>();
		try {
		      bankBranchDetailsEntities = bankBranchDetailsRepository.findAll(Sort.by("ifscCode").ascending());
		}catch(Exception e)
		{
			log.error(" Exception BankBranchDetailsServiceImpl.List() for export to excel " , e);
			//response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		
		return bankBranchDetailsEntities;
		    
	}
}
