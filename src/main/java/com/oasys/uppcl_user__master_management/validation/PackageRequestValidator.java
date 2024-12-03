package com.oasys.uppcl_user__master_management.validation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.PackageRequestDTO;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

@Component
public class PackageRequestValidator {

	public BaseDTO validate(PackageRequestDTO requestDTO, Boolean isUpdateRequest) {
		BaseDTO responseDTO = new BaseDTO();
		if(Boolean.FALSE.equals(isUpdateRequest) && StringUtils.isBlank(requestDTO.getName())) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
					.getMessage(new Object[] { Constants.NAME }));
			return responseDTO;
		}
		if (Boolean.FALSE.equals(isUpdateRequest) && requestDTO.getName().length() < Constants.NAME_FIELD_MIN_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE
					.getMessage(new Object[] { Constants.NAME_FIELD_MIN_LENTH , Constants.NAME}));
			return responseDTO;
		}
		if (Boolean.FALSE.equals(isUpdateRequest) && requestDTO.getName().length() > Constants.NAME_FIELD_MAX_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.NAME, Constants.NAME_FIELD_MAX_LENTH }));
			return responseDTO;
		}
		if(Boolean.TRUE.equals(isUpdateRequest) && Objects.isNull(requestDTO.getId())) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
					.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		if(Boolean.TRUE.equals(isUpdateRequest) && Objects.isNull(requestDTO.getStatus())) {
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
					.getMessage(new Object[] { Constants.STATUS_PARAM }));
			return responseDTO;
		}
		if (!StringUtils.isEmpty(requestDTO.getRemarks())
				&& requestDTO.getRemarks().length() < Constants.TEXT_FIELD_MIN_LENGTH) {
			responseDTO.setMessage(ResponseMessageConstant.FIELD_REQUIRED_MIN_LENGTH
					.getMessage(new Object[] { Constants.REMARKS, Constants.TEXT_FIELD_MIN_LENGTH }));
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_REQUIRED_MIN_LENGTH.getErrorCode());
			return responseDTO;
		}
		if (StringUtils.isNotBlank(requestDTO.getRemarks())
				&& requestDTO.getRemarks().length() > Constants.REMARKS_FIELD_MAX_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.REMARKS, Constants.REMARKS_FIELD_MAX_LENTH }));
			return responseDTO;
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
		if (Boolean.FALSE.equals(isUpdateRequest)) {
			if (CollectionUtils.isEmpty(requestDTO.getServiceCategoryIds())) {
				responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
						.getMessage(new Object[] { Constants.SERVICE_CATEGORY_IDS }));
				return responseDTO;
			}
			if (1 == requestDTO.getServiceCategoryIds().size()) {
				responseDTO.setStatusCode(ResponseMessageConstant.INSUFFICIENT_SERVICE_COUNT.getErrorCode());
				responseDTO.setMessage(ResponseMessageConstant.INSUFFICIENT_SERVICE_COUNT.getMessage());
				return responseDTO;
			}
		}
		
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		return responseDTO;
	}
}
