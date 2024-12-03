package com.oasys.uppcl_user__master_management.validation;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.PlanServiceMappingRequestDTO;
import com.oasys.uppcl_user__master_management.entity.PlanMasterEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceMasterEntity;
import com.oasys.uppcl_user__master_management.repository.PlanMasterRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class PlanServiceMappingRequestValidator {
	@Autowired
    private PlanMasterRepository planMasterRepository;

    @Autowired
    private ServiceRepository serviceRepository;

/**
 * Method implementation to validate Plan Service Mapping request
 *
 * @param requestDTO :PlanServiceMappingRequestDTO
 * @return BaseDTO
 */
public BaseDTO validate(PlanServiceMappingRequestDTO requestDTO) {
	BaseDTO responseDTO = new BaseDTO();
	if (Objects.isNull(requestDTO.getPlanId())) {
		log.error("request parameter missing : {}", requestDTO.getPlanId());
		responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
				.getMessage(new Object[] { Constants.PLAN_ID }));
		return responseDTO;
	}
	Optional<PlanMasterEntity> planMasterEntityOptional = planMasterRepository.findById(requestDTO.getPlanId());
	if (!planMasterEntityOptional.isPresent()) {
		log.error("request parameter invalid : {}", requestDTO.getPlanId());
		responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
		responseDTO.setMessage(
				ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getMessage(new Object[] { Constants.PLAN_ID }));
		return responseDTO;
	}
	if (Boolean.FALSE.equals(planMasterEntityOptional.get().getIsActive())) {
		log.error("request parameter missing : {}", requestDTO.getPlanId());
		responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
		responseDTO.setMessage(
				ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getMessage(new Object[] { Constants.PLAN_ID }));
		return responseDTO;
	}
	if (CollectionUtils.isEmpty(requestDTO.getServiceIds())) {
		log.error("request parameter missing : {}", requestDTO.getServiceIds());
		responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
		responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
				.getMessage(new Object[] { Constants.SERVICE_IDS }));
		return responseDTO;
	}
	for (UUID serviceId : requestDTO.getServiceIds()) {
		Optional<ServiceMasterEntity> serviceMasterEntityOptional = serviceRepository.findById(serviceId);
		if (!serviceMasterEntityOptional.isPresent()) {
			log.error("request parameter invalid : {}", serviceId);
			responseDTO.setStatusCode(ResponseMessageConstant.INVALID_REQUEST_PARAMETER.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.INVALID_REQUEST_PARAMETER
					.getMessage(new Object[] { Constants.SERVICE_IDS }));
			return responseDTO;
		}

	}
	responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
	return responseDTO;
}
}
