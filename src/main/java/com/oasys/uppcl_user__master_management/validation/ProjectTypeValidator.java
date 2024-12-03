package com.oasys.uppcl_user__master_management.validation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.uppcl_user__master_management.dto.ProjectTypeRequestDTO;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ProjectTypeValidator {


	public BaseDTO isRequestValid(ProjectTypeRequestDTO requestDTO, Boolean isUpdateRequest) {
		BaseDTO responseDTO = new BaseDTO();
		if (Boolean.TRUE.equals(isUpdateRequest) && Objects.isNull(requestDTO.getId())) {
			log.error("request parameter missing : {}", requestDTO.getId());
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getMessage(new Object[] { Constants.ID }));
			return responseDTO;
		}
		if (StringUtils.isBlank(requestDTO.getName())) {
			log.error("request parameter missing : {}", requestDTO.getName());
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(
					ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getMessage(new Object[] { Constants.NAME }));
			return responseDTO;
		}
		if (requestDTO.getName().length() < Constants.NAME_FIELD_MIN_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MINIMUM_FIELD_LENGTH_VALIDATION_FAILURE
					.getMessage(new Object[] { Constants.NAME_FIELD_MIN_LENTH, Constants.NAME }));
			return responseDTO;
		}
		if (requestDTO.getName().length() > Constants.NAME_FIELD_MAX_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.NAME, Constants.NAME_FIELD_MAX_LENTH }));
			return responseDTO;
		}
		if (Objects.isNull(requestDTO.getStatus())) {
			log.error("invalid request parameter : {}", requestDTO.getStatus());
			responseDTO.setStatusCode(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.MANDATORY_PARAMETER_MISSING
					.getMessage(new Object[] { Constants.STATUS_PARAM }));
			return responseDTO;
		}
		if(StringUtils.isNotBlank(requestDTO.getRemarks()) && requestDTO.getRemarks().length()>Constants.REMARKS_FIELD_MAX_LENTH) {
			responseDTO.setStatusCode(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED.getErrorCode());
			responseDTO.setMessage(ResponseMessageConstant.FIELD_LENGTH_EXCEEDED
					.getMessage(new Object[] { Constants.REMARKS, Constants.REMARKS_FIELD_MAX_LENTH }));
			return responseDTO;
		}
		responseDTO.setStatusCode(ResponseMessageConstant.SUCCESS_RESPONSE.getErrorCode());
		return responseDTO;
	}
}
