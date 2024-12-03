package com.oasys.uppcl_user__master_management.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.config.PaginationRequestDTO;
import com.oasys.uppcl_user__master_management.dao.PlanMasterDao;
import com.oasys.uppcl_user__master_management.dto.PlanMasterDTO;
import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;
import com.oasys.uppcl_user__master_management.repository.PlanMasterRepository;
import com.oasys.uppcl_user__master_management.response.ResponseConstant;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Service("PlanMasterDaoImpl")
@Log4j2
public class PlanMasterDaoImpl implements PlanMasterDao {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	PlanMasterRepository planMasterRepository;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public BaseDTO addPlan(PlanMasterDTO planMasterDTO) {
		// log.info("<---- PlanMasterDaoImpl.addPlan() ----> STARTED - {} ",
		// planMasterDTO);
		BaseDTO response = new BaseDTO();
		planMasterDTO.setPlanName(planMasterDTO.getPlanName().trim());
		if (StringUtils.isNotBlank(planMasterDTO.getRemarks())) {
			planMasterDTO.setRemarks(planMasterDTO.getRemarks().trim());
		}
		if (Boolean.TRUE.equals(planMasterDTO.getIsDefault())) {
			PlanMasterEntity planMasterEntity = planMasterRepository.findByIsDefaultTrue();
			if (Objects.nonNull(planMasterEntity)) {
				response.setMessage(
						ResponseMessageConstant.PLAN_ALREADY_EXIST.getMessage(new Object[] { Constants.DEFAULT }));
				response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
				return response;
			} else {
				 planMasterEntity = new PlanMasterEntity();
				planMasterEntity.setAmount(0.0);
				planMasterEntity.setPriority(1);
				planMasterEntity.setPlanName(planMasterDTO.getPlanName());
				planMasterEntity.setIsActive(Boolean.TRUE);
				planMasterEntity.setRemarks(planMasterDTO.getRemarks());
				planMasterRepository.save(planMasterEntity);
			}

		}

		Integer maxPriority = planMasterRepository.getMaxPriority();
		PlanMasterEntity planMasterEntity = planMasterRepository
				.findByPlanName(planMasterDTO.getPlanName().toUpperCase());
		if (Objects.nonNull(planMasterEntity)) {
			response.setMessage(ResponseMessageConstant.PLAN_ALREADY_EXIST
					.getMessage(new Object[] { planMasterDTO.getPlanName() }));
			response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			return response;
		}
		PlanMasterEntity highestAmountPlan = planMasterRepository.getPlanByPriority(maxPriority);
		if (Objects.nonNull(highestAmountPlan) && Objects.nonNull(highestAmountPlan.getAmount())
				&& planMasterDTO.getAmount() <= highestAmountPlan.getAmount()) {
			response.setMessage(ResponseMessageConstant.NEW_PLAN_AMOUNT_SHOULD_BE_GREATER_THAN_HIGHER_PRIORITY_PLAN
					.getMessage(new Object[] { highestAmountPlan.getPlanName(), highestAmountPlan.getAmount() }));
			response.setStatusCode(ResponseMessageConstant.BAD_REQUEST.getErrorCode());
			return response;
		}
		planMasterEntity = new PlanMasterEntity();
		planMasterEntity.setAmount(planMasterDTO.getAmount());
		planMasterEntity.setPriority(maxPriority + 1);
		planMasterEntity.setPlanName(planMasterDTO.getPlanName());
		planMasterEntity.setIsActive(Boolean.TRUE);
		planMasterEntity.setRemarks(planMasterDTO.getRemarks());
		planMasterRepository.save(planMasterEntity);
		response.setMessage(
				messageSource.getMessage(ResponseConstant.CREATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US));
		response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return response;
	}

	@Override
	public BaseDTO getAllPlans() {
		//log.info("<---- PlanMasterDaoImpl.getAllPlans() ----> STARTED");
		BaseDTO response = new BaseDTO();
		try {
			List<PlanMasterEntity> userList = planMasterRepository.findAll();

			if (userList != null) {
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setResponseContent(userList);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Paln Master Get All Details Fetched Successfully");
			} else {
				//log.warn("No Records Found..");
				response.setMessage("No Records Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}

		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.getAllPlans() ----> EXCEPTION: " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
		}
		//log.info("<---- PlanMasterDaoImpl.getAllPlans() ----> ENDED");
		return response;
	}

	@Override
	public BaseDTO deletePlan(UUID id) {
		//log.info("<---- PlanMasterDaoImpl.deletePlan() ----> STARTED");
		BaseDTO response = new BaseDTO();
		PlanMasterEntity planMasterEntity = null;
		try {
			planMasterEntity = planMasterRepository.getOne(id);
			if (planMasterEntity != null) {
				planMasterRepository.delete(planMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.DELETE_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("Paln master deleted successfully");
			} else {
				//log.error("Id doesn't exist");
				response.setMessage("No Record Deleted..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.deletePlan() ----> EXCEPTION: " + e);
			String msg = messageSource.getMessage(ResponseConstant.DELETE_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(409);
		}
		//log.info("<---- PlanMasterDaoImpl.deletePlan() ----> ENDED");
		return response;
	}

	@Override
	public BaseDTO softDeletePlan(UUID id) {
		//log.info("<---- PlanMasterDaoImpl.softDeletePlan() ----> STARTED");
		BaseDTO response = new BaseDTO();
		try {

			PlanMasterEntity planMasterEntity = planMasterRepository.getOne(id);
			if (planMasterEntity != null) {
				if (planMasterEntity.getIsActive() == true) {
					planMasterEntity.setIsActive(false);
					planMasterEntity = planMasterRepository.save(planMasterEntity);
					response.setMessage("Successfully Deleted");
				response.setMessage("Successfully deleted the Plan Details..");
			} else {
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
			}
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.softDeletePlan() ----> EXCEPTION: " + e);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
			response.setMessage("Unable to get Plan details..");
		}
		//log.info("<---- PlanMasterDaoImpl.softDeletePlan() ----> ENDED");
		return response;
	}

	@Override
	public BaseDTO updatePlan(UUID id, PlanMasterDTO planMasterDTO) {
		//log.info(" <----- PlanMasterDaoImpl update Service STARTED -----> ");
		BaseDTO response = new BaseDTO();
		planMasterDTO.setPlanName(planMasterDTO.getPlanName().trim());
		if (StringUtils.isNotBlank(planMasterDTO.getRemarks())) {
			planMasterDTO.setRemarks(planMasterDTO.getRemarks().trim());
		}
		PlanMasterEntity planMasterEntity = planMasterRepository
				.findByPlanNameNotInId(planMasterDTO.getPlanName().toUpperCase(), id);
		if (Objects.nonNull(planMasterEntity)) {
			response.setMessage(ResponseMessageConstant.PLAN_ALREADY_EXIST
					.getMessage(new Object[] { planMasterDTO.getPlanName() }));
			response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			return response;
		}
		planMasterEntity = planMasterRepository.getOne(id);
		if (Objects.isNull(planMasterEntity)) {
			String msg = messageSource.getMessage(ResponseConstant.NOT_FOUND, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.NOT_FOUND.getCode());
			return response;
		}
		if (Objects.nonNull(planMasterEntity.getIsDefault()) && Boolean.TRUE.equals(planMasterEntity.getIsDefault())) {
			String msg = messageSource.getMessage(ResponseConstant.FAILED_TO_UPDATE_DEFAULT_PLAN, null, Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			return response;
		}

		if (Objects.nonNull(planMasterEntity.getIsDefault()) && Boolean.TRUE.equals(planMasterDTO.getIsDefault())) {
			response.setMessage(
					ResponseMessageConstant.PLAN_ALREADY_EXIST.getMessage(new Object[] { Constants.DEFAULT }));
			response.setStatusCode(ErrorDescription.ALREADY_EXISTS.getCode());
			return response;
		}

		if (planMasterEntity.getAmount() != planMasterDTO.getAmount()) {
			PlanMasterEntity higherAmountPlan = planMasterRepository
					.getHigherAmountPlanByPriority(planMasterEntity.getPriority());
			if (Objects.nonNull(higherAmountPlan) && Objects.nonNull(higherAmountPlan.getAmount())
					&& planMasterDTO.getAmount() >= higherAmountPlan.getAmount()) {
				response.setMessage(ResponseMessageConstant.PLAN_AMOUNT_CANNOT_GREATER_THAN_HIGHER_PRIORITY_PLAN
						.getMessage(new Object[] { higherAmountPlan.getPlanName(), higherAmountPlan.getAmount() }));
				response.setStatusCode(ResponseMessageConstant.BAD_REQUEST.getErrorCode());
				return response;
			}

			PlanMasterEntity lowerAmountPlan = planMasterRepository
					.getLowerAmountPlanByPriority(planMasterEntity.getPriority());
			if (Objects.nonNull(lowerAmountPlan) && Objects.nonNull(lowerAmountPlan.getAmount())
					&& planMasterDTO.getAmount() <= lowerAmountPlan.getAmount()) {
				response.setMessage(ResponseMessageConstant.PLAN_AMOUNT_CANNOT_LESS_THAN_LOWER_PRIORITY_PLAN
						.getMessage(new Object[] { lowerAmountPlan.getPlanName(), lowerAmountPlan.getAmount() }));
				response.setStatusCode(ResponseMessageConstant.BAD_REQUEST.getErrorCode());
				return response;
			}
		}
		planMasterEntity.setPlanName(planMasterDTO.getPlanName().trim());
		planMasterEntity.setAmount(planMasterDTO.getAmount());
		if (StringUtils.isNotBlank(planMasterDTO.getRemarks())) {
			planMasterEntity.setRemarks(planMasterDTO.getRemarks().trim());
		}
		planMasterEntity.setIsActive(planMasterDTO.getIsActive());
		planMasterRepository.save(planMasterEntity);
		response.setMessage(
				messageSource.getMessage(ResponseConstant.UPDATE_SUCCESS_RESPONSE_MESSAGE, null, Locale.US));
		response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		return response;
	}

	@Override
	public BaseDTO getById(UUID id) {
		//log.info("<---- PlanMasterDaoImpl.getById() ----> STARTED");
		BaseDTO response = new BaseDTO();
		PlanMasterEntity planMasterEntity = null;
		try {
			planMasterEntity = planMasterRepository.getOne(id);
			if (planMasterEntity != null) {
				response.setResponseContent(planMasterEntity);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				response.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
				//log.info("get plan master details");
			} else {
				//log.error("No Record Found..");
				response.setMessage("No Record Found..");
				response.setStatusCode(ErrorDescription.EMPTY_DATA.getCode());
			}
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.getById() ----> EXCEPTION: " + e);
			String msg = messageSource.getMessage(ResponseConstant.GET_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<---- PlanMasterDaoImpl.getById() ----> ENDED");
		return response;
	}

	@Override
	public BaseDTO getLazyList(PaginationRequestDTO pageData) {
		//log.info("<== PlanMasterDaoImpl.getAllLazy() Started ==>");
		BaseDTO response = new BaseDTO();
		Page<PlanMasterEntity> merhcantTypMasterList = null;
		List<PlanMasterEntity> contentList = new ArrayList<PlanMasterEntity>();
		Pageable pageRequest;
		try {
			//log.info("<<<< ------- PlanMasterDaoImpl.getAllLazy() ---------- >>>>>>>");
			if (pageData.getSearch() != null) {
				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.ASC, pageData.getSortField()));
					merhcantTypMasterList = planMasterRepository.search(pageRequest, pageData.getSearch());
				} else {
					pageRequest = PageRequest.of(pageData.getPageNo(), pageData.getPaginationSize(),
							Sort.by(Direction.DESC, pageData.getSortField()));
					merhcantTypMasterList = planMasterRepository.search(pageRequest, pageData.getSearch());
				}
			} else {

				if (pageData.getSortOrder().equalsIgnoreCase("ASC")) {
					merhcantTypMasterList = planMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
							pageData.getPaginationSize(), Sort.by(Direction.ASC, pageData.getSortField())));
				} else {
					merhcantTypMasterList = planMasterRepository.findAll(PageRequest.of(pageData.getPageNo(),
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
				for (PlanMasterEntity merchantTypMaster : merhcantTypMasterList) {
					contentList.add(merchantTypMaster);
				}
				response.setResponseContents(contentList);
				String msg = messageSource.getMessage(ResponseConstant.GET_SUCCESS_RESPONSE_MESSAGE, null,Locale.US);
				response.setMessage(msg);
				//log.info("Successfully get lazy List details");
			}
		} catch (Exception e) {
			log.error("<<<< ------- PlanMasterDaoImpl.getAllLazy() ---------- Exception>>>>", e);
			String msg = messageSource.getMessage(ResponseConstant.LAZYLIST_FAILURE_RESPONSE_MESSAGE, null,Locale.US);
			response.setMessage(msg);
			response.setStatusCode(ErrorDescription.FAILURE_RESPONSE.getCode());
		}
		//log.info("<== PlanMasterDaoImpl.getAllLazy() Ended ==>");
		return response;
	}
	
	public List<PlanMasterEntity> exceptId(UUID id){
		//log.info(" <----- PlanMasterDaoImpl exceptId  STARTED -----> ");
		List<PlanMasterEntity> planMasterEntity = new ArrayList<PlanMasterEntity>();
		try {
			planMasterEntity= planMasterRepository.getByIdNotEquatToId(id);
			
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.exceptId() ----> EXCEPTION", e);
		}
		//log.info(" <----- PlanMasterDaoImpl exceptId  END -----> ");
		return planMasterEntity;
		
	}
	
	private PlanMasterEntity updatedValues(PlanMasterEntity planMasterEntity,
			PlanMasterDTO planMasterDTO) {
		PlanMasterEntity planMaster = planMasterEntity;
		planMaster.setPlanName(planMasterDTO.getPlanName());
		planMaster.setIsActive(planMasterDTO.getIsActive());
		planMaster.setIsDefault(planMasterDTO.getIsDefault());
		planMaster.setRemarks(planMasterDTO.getRemarks());
		return planMaster;
	}
	
	private boolean validateIsDefault(UUID uuid) {
		//log.info(" <----- PlanMasterDaoImpl validateIsDefault  STARTED -----> ");
		boolean flag = false;
		try {
			flag = validateIsDefaultorNot(uuid);
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.validateIsDefault() ----> EXCEPTION", e);
		}
		//log.info(" <----- PlanMasterDaoImpl validateIsDefault  END -----> ");
		return flag;
	}

	public boolean validateIsDefaultorNot(UUID uuid) {
		
		//log.info(" <----- PlanMasterDaoImpl validateIsDefault  STARTED -----> ");
		PlanMasterEntity subscription = new PlanMasterEntity();
		PlanMasterEntity subscription1 = new PlanMasterEntity();
		
		try {
			subscription = planMasterRepository.findByPlanId(uuid);
			subscription1 = planMasterRepository.findByIsDefaultTrue();
			if(subscription1 != null) {
			if(subscription.getId().equals(subscription1.getId()))
			{
				return true;
			}
			else{
				return false;
			}
			}else
			{
				return true;
			}
		} catch (Exception e) {
			log.error("<---- PlanMasterDaoImpl.validateIsDefault() ----> EXCEPTION", e);
		}
		//log.info(" <----- PlanMasterDaoImpl validateIsDefault  END -----> ");
		return false;
		
	}

	
}
