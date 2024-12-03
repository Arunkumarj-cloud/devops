package com.oasys.uppcl_user__master_management.service.impl;





import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hazelcast.util.CollectionUtil;
import com.oasys.clients.userservice.utils.CommonUtil;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.security.Oauth2UserDetails;
import com.oasys.uppcl_user__master_management.dto.PlanServiceMappingRequestDTO;
import com.oasys.uppcl_user__master_management.dto.PlanServiceMappingResponseDTO;
import com.oasys.uppcl_user__master_management.dto.ServiceMasterResponseDTO;
import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;
import com.oasys.uppcl_user__master_management.entity.PlanServiceMappingEntity;
import com.oasys.uppcl_user__master_management.repository.PlanMasterRepository;
import com.oasys.uppcl_user__master_management.repository.PlanServiceMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;
import com.oasys.uppcl_user__master_management.service.PlanServiceMappingService;
import com.oasys.uppcl_user__master_management.validation.PlanServiceMappingRequestValidator;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class PlanServiceMappingServiceImpl implements  PlanServiceMappingService {

	@Autowired
	private PlanServiceMappingRequestValidator validator;

	@Autowired
	private PlanServiceMappingRepository planServiceMappingRepository;

	@Autowired
	private PlanMasterRepository planMasterRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private Oauth2UserDetails oauth2UserDetails;
	

	/**
	 * Method implementation to save Plan service mapping
	 *
	 * @param requestDTO : PlanServiceMappingRequestDTO
	 * @return BaseDTO
	 */
	@Override
	@Transactional
	public BaseDTO save(PlanServiceMappingRequestDTO requestDTO) {
		BaseDTO responseDTO = validator.validate(requestDTO);
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		responseDTO = new BaseDTO();
		requestDTO.getServiceIds().forEach(serviceId -> {
			Optional<PlanServiceMappingEntity> planServiceMappingEntityOptional = planServiceMappingRepository
					.findByPlanIdAndServiceId(requestDTO.getPlanId(), serviceId);
			if (planServiceMappingEntityOptional.isPresent()) {
				log.info("mapping already exist for planId : {}, serviceId: {}", requestDTO.getPlanId(), serviceId);
				return;
			}
			PlanServiceMappingEntity planServiceMappingEntity = new PlanServiceMappingEntity();
			planServiceMappingEntity.setPlanMasterEntity(planMasterRepository.findById(requestDTO.getPlanId()).get());
			planServiceMappingEntity.setServiceMasterEntity(serviceRepository.findById(serviceId).get());
			planServiceMappingEntity.setCreatedBy(oauth2UserDetails.getId());
			planServiceMappingRepository.save(planServiceMappingEntity);

		});
		responseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
		responseDTO.setMessage(ResponseMessageConstant.CREATE_SUCCESS_RESPONSE.getMessage());
		return responseDTO;
	}

	/**
	 * Method implementation to update Plan service mapping
	 *
	 * @param requestDTO : PlanServiceMappingRequestDTO
	 * @return BaseDTO
	 */
	@Override
	@Transactional
	public BaseDTO update(PlanServiceMappingRequestDTO requestDTO) {
		BaseDTO responseDTO = validator.validate(requestDTO);
		if (ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode() != responseDTO.getStatusCode()) {
			return responseDTO;
		}
		responseDTO = new BaseDTO();

		requestDTO.getServiceIds().forEach(serviceId -> {
			Optional<PlanServiceMappingEntity> planServiceMappingEntityOptional = planServiceMappingRepository
					.findByPlanIdAndServiceId(requestDTO.getPlanId(), serviceId);
			if (planServiceMappingEntityOptional.isPresent()) {
				log.info("mapping already exist for planId : {}, serviceId: {}", requestDTO.getPlanId(), serviceId);
				return;
			}
			PlanServiceMappingEntity planServiceMappingEntity = new PlanServiceMappingEntity();
			planServiceMappingEntity.setPlanMasterEntity(planMasterRepository.findById(requestDTO.getPlanId()).get());
			planServiceMappingEntity.setServiceMasterEntity(serviceRepository.findById(serviceId).get());
			planServiceMappingEntity.setCreatedBy(oauth2UserDetails.getId());
			planServiceMappingRepository.save(planServiceMappingEntity);

		});
		planServiceMappingRepository.deleteByPlanIdAndNotInServiceIds(requestDTO.getPlanId(),
				requestDTO.getServiceIds());
		responseDTO.setStatusCode(ResponseMessageConstant.UPDAE_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.UPDAE_RESPONSE.getMessage());
		return responseDTO;
	}

	/**
	 * Gets mapping services by plan id and returns generic response.
	 *
	 * @param planId 
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO getByPlanId(UUID planId) {
		BaseDTO responseDTO = new BaseDTO();
		PlanServiceMappingResponseDTO planServiceMappingResponseDTO = this.getPlanServiceMappingResponse(planId);
		if (Objects.isNull(planServiceMappingResponseDTO)) {
			log.error("no record found for planId {}", planId);
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.PLAN_ID }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SEARCH_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		responseDTO.setResponseContent(planServiceMappingResponseDTO);
		return responseDTO;
	}
	
	/**
	 * Gets the plan service mapping response
	 *
	 * @param planId the plan id
	 * @return PlanServiceMappingResponseDTO
	 */
	private PlanServiceMappingResponseDTO getPlanServiceMappingResponse(UUID planId) {
		List<PlanServiceMappingEntity> planServiceMappingList = planServiceMappingRepository.findByPlanId(planId);
		if (CollectionUtil.isEmpty(planServiceMappingList)) {
			log.error("no record found for planId {}", planId);
			return null;
		}
		PlanServiceMappingResponseDTO planServiceMappingResponseDTO = new PlanServiceMappingResponseDTO();
		planServiceMappingResponseDTO.setPlanId(planId);
		planServiceMappingResponseDTO.setPlanName(planServiceMappingList.get(0).getPlanMasterEntity().getPlanName());
		List<ServiceMasterResponseDTO> serviceMasterResponseList = new ArrayList<>();
		planServiceMappingList.forEach(planServiceMapping -> {
			ServiceMasterResponseDTO serviceDTO = new ServiceMasterResponseDTO();
			serviceDTO.setId(planServiceMapping.getServiceMasterEntity().getId());
			serviceDTO.setName(planServiceMapping.getServiceMasterEntity().getServiceName());
			serviceMasterResponseList.add(serviceDTO);
		});
		planServiceMappingResponseDTO.setServiceMasterResponseList(serviceMasterResponseList);
		return planServiceMappingResponseDTO;
	}

	/**
	 * Method implementation to update existing mapping based on planId and serviceId
	 *
	 * @param planId 
	 * @param serviceId 
	 * @return BaseDTO
	 */
	@Override
	@Transactional
	public BaseDTO update(String planId, String serviceId) {
		BaseDTO responseDTO = new BaseDTO();
		if (Boolean.FALSE.equals(CommonUtil.isValidUUID(planId))) {
			log.error("request parameter invalid : {}", planId);
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getMessage(new Object[] { Constants.PLAN_ID }));
			return responseDTO;
		}
		if (Boolean.FALSE.equals(CommonUtil.isValidUUID(serviceId))) {
			log.error("request parameter invalid : {}", serviceId);
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
					.getMessage(new Object[] { Constants.SERVICE_ID }));
			return responseDTO;
		}
		Optional<PlanServiceMappingEntity> planServiceMappingEntityOptional = planServiceMappingRepository
				.findByPlanIdAndServiceId(UUID.fromString(planId), UUID.fromString(serviceId));
		if (planServiceMappingEntityOptional.isPresent()) {
			log.info("mapping already exist for planId : {}, serviceId: {}", planId, serviceId);
			responseDTO.setStatusCode(ResponseMessageConstant.PLAN_SERVICE_MAPPING_ALREADY_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.PLAN_SERVICE_MAPPING_ALREADY_EXIST
					.getMessage(new Object[] { planId, serviceId }));
			return responseDTO;
		}
		PlanServiceMappingEntity planServiceMappingEntity = new PlanServiceMappingEntity();
		planServiceMappingEntity.setPlanMasterEntity(planMasterRepository.findById(UUID.fromString(planId)).get());
		planServiceMappingEntity.setServiceMasterEntity(serviceRepository.findById(UUID.fromString(serviceId)).get());
		planServiceMappingEntity.setCreatedBy(oauth2UserDetails.getId());
		planServiceMappingRepository.save(planServiceMappingEntity);
		responseDTO.setStatusCode(ResponseMessageConstant.UPDAE_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.UPDAE_RESPONSE.getMessage());
		return responseDTO;
	}

	/**
	 * Delete by plan id and service id.
	 *
	 * @param planId
	 * @param serviceId 
	 * @return BaseDTO
	 */
	@Override
	@Transactional
	public BaseDTO deleteByPlanIdAndServiceId(UUID planId, UUID serviceId) {
		BaseDTO responseDTO = new BaseDTO();
		Optional<PlanServiceMappingEntity> planServiceMappingEntityOptional = planServiceMappingRepository
				.findByPlanIdAndServiceId(planId, serviceId);
		if (!planServiceMappingEntityOptional.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.PLAN_SERVICE_MAPPING_DOES_NOT_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.PLAN_SERVICE_MAPPING_DOES_NOT_EXIST
					.getMessage(new Object[] { planId, serviceId }));
			return responseDTO;
		}
		try {
			planServiceMappingRepository.deleteByPlanIdAndServiceId(planId, serviceId);
		} catch (Exception e) {
			log.error("error occurred while deleting plan service mapping : {}", e);
			responseDTO.setStatusCode(ResponseMessageConstant.FAILED_TO_DELETE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FAILED_TO_DELETE.getMessage());
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.DELET_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.DELET_RESPONSE.getMessage());
		return responseDTO;
	}
	
	/**
	 * Gets all plans with their mapping services.
	 *
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO getAll() {
		BaseDTO responseDTO = new BaseDTO();
		List<PlanServiceMappingResponseDTO> planServiceMappingResponseList = new ArrayList<>();
		List<PlanMasterEntity> planEntityList = planMasterRepository.findAllActive();
		if (CollectionUtils.isEmpty(planEntityList)) {
			log.error("no record found for {}", planEntityList);
			responseDTO.setStatusCode(ResponseMessageConstant.NOT_FOUND.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.NOT_FOUND.getMessage(new Object[] { Constants.PLAN_ID }));
			return responseDTO;
		}
		planEntityList.forEach(planEntity -> {
			PlanServiceMappingResponseDTO planServiceMappingResponse = this
					.getPlanServiceMappingResponse(planEntity.getId());
			if (Objects.nonNull(planServiceMappingResponse)) {
				planServiceMappingResponseList.add(planServiceMappingResponse);
			}
		});
		responseDTO.setStatusCode(ResponseMessageConstant.SEARCH_RESPONSE.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.SEARCH_RESPONSE.getMessage());
		responseDTO.setResponseContent(planServiceMappingResponseList);
		return responseDTO;
	}
}

