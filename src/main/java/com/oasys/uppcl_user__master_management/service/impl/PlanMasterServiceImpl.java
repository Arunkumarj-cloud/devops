package com.oasys.uppcl_user__master_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.oasys.config.BaseDTO;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.PlanMasterDao;
import com.oasys.uppcl_user__master_management.dto.PlanMasterDTO;
import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;
import com.oasys.uppcl_user__master_management.repository.PlanMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PlanMasterService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PlanMasterServiceImpl implements PlanMasterService {

	@Autowired
	PlanMasterDao planMasterDao;

	@Autowired
	PlanMasterRepository planMasterRepository;

	@Autowired
	MessageSource messageSource;
	
	@Override
	public BaseDTO addPlan(PlanMasterDTO planMasterDTO) {
		//log.info("<---- PlanMasterServiceImpl.addUser() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {
			response = planMasterDao.addPlan(planMasterDTO);
		} catch(DataIntegrityViolationException e) {
			log.error("<---- PlanMasterServiceImpl.addUser() ---->EXCEPTION");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Add User");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.addUser() ---->EXCEPTION {}", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			//response.setMessage("Unable to Add User");
			String msg = messageSource.getMessage(ResponseConstant.CREATE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<---- PlanMasterServiceImpl.addUser() ---->ENDED");
		return response;
	}

	@Override
	public BaseDTO getAllPlans() {
		//log.info("<---- PlanMasterServiceImpl.getUser() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {
			response = planMasterDao.getAllPlans();
		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.getUser() ---->EXCEPTION");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Invalid Merchant id");
		}
		//log.info("<---- PlanMasterServiceImpl.getUser() ---->ENDED");
		return response;
	}

	@Override
	public BaseDTO deletePlan(UUID id) {
		//log.info("<---- PlanMasterServiceImpl.deletePlan() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {
			response = planMasterDao.getById(id);
		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.deletePlan() ---->EXCEPTION");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Invalid Merchant id");
		}
		//log.info("<---- PlanMasterServiceImpl.deletePlan() ---->ENDED");
		return response;
	}

	@Override
	public BaseDTO softDeletePlan(UUID id) {
		//log.info("<---- PlanMasterServiceImpl.softDeletePlan() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {
			response = planMasterDao.softDeletePlan(id);
		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.softDeletePlan() ---->EXCEPTION");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Invalid Merchant id");
		}
		//log.info("<---- PlanMasterServiceImpl.softDeletePlan() ---->ENDED");
		return response;
	}

	@Override
	public BaseDTO updatePlan(UUID id, PlanMasterDTO planMasterDTO) {
		//log.info("<---- PlanMasterServiceImpl.updatePlan() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {
			response = planMasterDao.updatePlan(id, planMasterDTO);

		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.updatePlan()  ....Exception {}", e);
			response.setMessage("Failed to update Plan");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		// log.info("<---- PlanMasterServiceImpl.updatePlan() ---->Ended");
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<---- PlanMasterServiceImpl.getById() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {

			// usermanagementRepository.getByUserId(usermanagementDTO.getUserId());
			PlanMasterEntity usermanagementEntity = planMasterRepository.getOne(id);
			// response = usermanagementDao.getById(id);
			response.setMessage("User Retrieved");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(usermanagementEntity);

		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.getById() ---->EXCEPTION {}", e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Invalid Merchant id");
		}
		//log.info("<---- PlanMasterServiceImpl.getById() ---->ENDED");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		BaseDTO response = new BaseDTO();
		//log.info("<=== Started PlanMasterServiceImpl.getAllLazy()===>");
		try {
			response = planMasterDao.getLazyList(pageData);
		} catch (Exception e) {
			log.error(" Exception PlanMasterServiceImpl.getAllLazy() " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<=== Ended PlanMasterServiceImpl.getAllLazy() ===>");
		return response;
	}
	
	@Override
	public BaseDTO getDefaultPlan() {
		//log.info("<---- PlanMasterServiceImpl.getDefaultPlan() ---->STARTED");
		BaseDTO response = new BaseDTO();
		try {

			PlanMasterEntity findByIsDefaultTrue = planMasterRepository.findByIsDefaultTrue();
			response.setMessage("Plan Retrieved");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(findByIsDefaultTrue);

		} catch (Exception e) {
			log.error("<---- PlanMasterServiceImpl.getDefaultPlan() ---->EXCEPTION");
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("No Default plan exist");
		}
		//log.info("<---- PlanMasterServiceImpl.getDefaultPlan() ---->ENDED");
		return response;
	}
	@Override
	public BaseDTO findbyPlanUUID(String name) {
		BaseDTO response = new BaseDTO();
		try {
			PlanMasterEntity plan = planMasterRepository.findByPlanNameIgnoreCase(name);
			response.setMessage("Plan Retrieved");
			response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			response.setResponseContent(plan);
		} catch (Exception e) {
			log.error("EXCEPTION "+e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("No Default plan exist");
		}
		return response;
	}
	
	@Override
	public Boolean isPlanExistById(UUID id) {
		Optional<PlanMasterEntity> planEntityOptional = planMasterRepository.findById(id);
		if (planEntityOptional.isPresent() && Boolean.TRUE.equals(planEntityOptional.get().getIsActive())) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}
	
	@Override
	public BaseDTO getAllActive() {
		BaseDTO response = new BaseDTO();
		List<PlanMasterEntity> planMasterEntityList = planMasterRepository.findAllActive();
		if (CollectionUtils.isEmpty(planMasterEntityList)) {
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			response.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage());
			return response;
		}
		response.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		response.setResponseContent(planMasterEntityList);
		return response;
	}

}
