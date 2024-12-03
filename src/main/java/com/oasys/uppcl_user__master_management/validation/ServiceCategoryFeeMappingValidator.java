package com.oasys.uppcl_user__master_management.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.ServiceCategoryFeeMappingRequestDTO;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryEntity;
import com.oasys.uppcl_user__master_management.entity.ServiceCategoryFeeMappingEntity;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryFeeMappingRepository;
import com.oasys.uppcl_user__master_management.repository.ServiceCategoryRepository;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

@Component
public class ServiceCategoryFeeMappingValidator {
	@Autowired
	private ServiceCategoryRepository serviceRepository;
	
	@Autowired
	private ServiceCategoryFeeMappingRepository serviceFeeMappingRepository;
	
	public BaseDTO validate(ServiceCategoryFeeMappingRequestDTO requestDTO, Boolean isUpdateRequest) {
		BaseDTO responseDTO = new BaseDTO();
		if (Objects.isNull(requestDTO.getServiceCategoryId())) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
					.getMessage(new Object[] { Constants.SERVICE_CATEGORY_ID }));
			return responseDTO;
		}
		Optional<ServiceCategoryEntity> serviceMasterOptional = serviceRepository.findById(requestDTO.getServiceCategoryId());
		if (!serviceMasterOptional.isPresent()) {
			responseDTO.setStatusCode(ResponseMessageConstant.SERVICE_DOES_NOT_EXIST.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.SERVICE_DOES_NOT_EXIST
					.getMessage(new Object[] { requestDTO.getServiceCategoryId() }));
			return responseDTO;
		}
		if (Boolean.FALSE.equals(isUpdateRequest)) {
			Optional<ServiceCategoryFeeMappingEntity> serviceFeeMappingOptional = serviceFeeMappingRepository
					.findByServiceCategoryId(requestDTO.getServiceCategoryId());
			if (serviceFeeMappingOptional.isPresent()) {
				responseDTO.setStatusCode(ResponseMessageConstant.SERVICE_FEE_MAPPING_ALREADY_EXIST.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.SERVICE_FEE_MAPPING_ALREADY_EXIST.getMessage());
				return responseDTO;
			}
		}
		if (Objects.isNull(requestDTO.getAmount())) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getMessage(new Object[] { Constants.AMOUNT }));
			return responseDTO;
		}
		if(requestDTO.getAmount() < 0) {
			responseDTO.setStatusCode(ResponseMessageConstant.AMOUNT_CANNOT_BE_NEGATIVE.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.AMOUNT_CANNOT_BE_NEGATIVE.getMessage(new Object[] { Constants.AMOUNT }));
			return responseDTO;
		}
		if (requestDTO.getAmount() > 999999999.0) {
			responseDTO.setStatusCode(ResponseMessageConstant.VALUE_OUT_OF_RANGE.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.VALUE_OUT_OF_RANGE.getMessage(new Object[] { Constants.AMOUNT }));
			return responseDTO;
		}
		if (!StringUtils.isEmpty(requestDTO.getRemarks())
				&& requestDTO.getRemarks().length() < Constants.TEXT_FIELD_MIN_LENGTH) {
			responseDTO.setMessage(ResponseMessageConstant.FIELD_REQUIRED_MIN_LENGTH
					.getMessage(new Object[] { Constants.REMARKS, Constants.TEXT_FIELD_MIN_LENGTH }));
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_REQUIRED_MIN_LENGTH.getErrorCode());
			return responseDTO;
		}
		if (!StringUtils.isEmpty(requestDTO.getRemarks())
				&& requestDTO.getRemarks().length() > Constants.REMARKS_FIELD_MAX_LENTH) {
			responseDTO.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.REMARKS, Constants.REMARKS_FIELD_MAX_LENTH }));
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		return responseDTO;

	}

}
