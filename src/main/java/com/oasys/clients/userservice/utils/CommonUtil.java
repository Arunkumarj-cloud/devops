package com.oasys.clients.userservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oasys.cexception.ServiceLayerExecutionException;
import com.oasys.config.BaseDTO;
import com.oasys.config.Constants;
import com.oasys.config.ErrorDescription;
import com.oasys.uppcl_user__master_management.dto.ResponseContent;
import com.oasys.uppcl_user__master_management.dto.UserManagementResponseDTO;
import com.oasys.uppcl_user__master_management.response.ResponseMessageConstant;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CommonUtil {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private HttpServletRequest headerRequest;

	@Value("${usermanagement.path}")
	private String userManagementPath;

	@Autowired
	private RestTemplate restTemplate;
	
	private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/jpeg", "image/gif","application/pdf");
	
	public <T> T modalMap(Object ob, Class<T> type) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(ob, type);

	}

	public void modalMapCopy(Object source, Object destination) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.map(source, destination);

	}

	public static Boolean isValidUUID(String string) {
		if (Objects.isNull(string)) {
			return Boolean.FALSE;
		}
		return Constants.UUID_REGEX_PATTERN.matcher(string).matches();
	}

	public <T> T ojectMap(Object ob, Class<T> type) {
		return objectMapper.convertValue(ob, type);

	}
	
	public static Boolean isDateFormatValid(String date) {
		try {
			LocalDateTime.parse(date, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
		} catch (DateTimeParseException pe) {
			log.info("invalid date : {}", date);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}


	
	public Boolean  isPincodeValid(String pincode) {
		if (Objects.isNull(pincode)) {
			return Boolean.FALSE;
		}
		return Constants.PINCODE_REGEX.matcher(pincode.trim()).matches();
	}
	
	/*public String extractHeaderToken() {
		String authHeaderValue = null;
		try {
			Enumeration<String> headers = request.getHeaders("X-Authorization");
			while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
				String value = headers.nextElement();
				if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
					authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
					// Add this here for the auth details later. Would be better to change the
					// signature of this method.
					request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
							value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
					int commaIndex = authHeaderValue.indexOf(',');
					if (commaIndex > 0) {
						authHeaderValue = authHeaderValue.substring(0, commaIndex);
					}
					return authHeaderValue;
				}
			}
		} catch (Exception e) {

			return null;
		}

		return authHeaderValue;
	}*/
	
	public <T> T jsonToObject(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (Exception e) {
			throw new ServiceLayerExecutionException(e);
		}
	}
	
	public ResponseContent getUserDetailsById(UUID id) {
		ResponseEntity<UserManagementResponseDTO> apiResponse = null;
		HttpHeaders headers = new HttpHeaders();
		StringBuffer url = new StringBuffer(userManagementPath);
		url.append("/registration/"+id);
		String access_token = headerRequest.getHeader("X-Authorization");
		headers.set("X-Authorization", access_token);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<ResponseEntity<UserManagementResponseDTO>> entity = new HttpEntity<>(headers);

		try {
			apiResponse = restTemplate.exchange(url.toString(), HttpMethod.GET, entity,
					UserManagementResponseDTO.class);

		} catch (Exception e) {
			log.error("Exception occurred while hitting api /registration/{id} for id :: {} , exception :: {}--->",
					url.toString(), e);
			return null;
		}
		log.info("{} api response :: {}", url.toString(), apiResponse);
		if (Objects.nonNull(apiResponse) && Objects.nonNull(apiResponse.getBody())
				&& Objects.nonNull(apiResponse.getBody().getResponseContent())) {
			return apiResponse.getBody().getResponseContent();
		} else {
			log.error("{} api response body:: {}", url.toString(), apiResponse.getBody());
		}
		return null;

	}
	
	public boolean isStringContainsAlphabetsOnly(String string) {
		if (StringUtils.isBlank(string)) {
			log.info("blank string passed : {}", string);
			return false;
		}
		try {
			return string.matches("^[a-zA-Z]*$");
		} catch (Exception e) {
			log.error("exception occurred while verifying string :: {}, ex : {}", string, e);
			return false;
		}
	}
	
	public String getClientIP(HttpServletRequest request) {

        String remoteAddr = "";

        if (Objects.nonNull(request)) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isBlank(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
	
	public Boolean isValidPhoneNumber(String phoneNumber) {
		if (StringUtils.isBlank(phoneNumber)) {
			return Boolean.FALSE;
		}
		return Constants.PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
	}
	
	public Boolean isValidatePanNumber(String panNumber) {
		if (StringUtils.isBlank(panNumber)) {
			return Boolean.FALSE;
		}
		
		return Constants.PAN_NUMBER_PATTERN.matcher(panNumber).matches();
	}
	
	
	public static BaseDTO validateFileExtension(String fileContentType) {
		log.info("===fileContentType= {}",fileContentType);
		BaseDTO baseDTO = new BaseDTO();
		if (contentTypes.contains(fileContentType)) {
			baseDTO.setStatusCode(ErrorDescription.SUCCESS_RESPONSE.getCode());
			return baseDTO;
		} else {
			baseDTO.setStatusCode(ErrorDescription.BAD_REQUEST.getCode());
			baseDTO.setMessage(ResponseMessageConstant.FILE_EXTENSION_NOT_ALLOWED.getMessage(contentTypes.toString()));
			return baseDTO;
		}
	}
	

}
