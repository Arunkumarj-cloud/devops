package com.oasys.security.jwt;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CommonDataController {

	
	@Value("${masterdata.domain.url}")
	private String masterManagementHost;

	@Value("${masterdata.domain.api.grade}")
	private String masterDataGrade;

	@Value("${masterdata.domain.api.material}")
	private String masterDataMaterial;

	@Value("${masterdata.domain.api.supplytype}")
	private String masterDataSupplyType;

	@Value("${masterdata.domain.api.routemaster}")
	private String masterDataRouteMaster;

	@Value("${masterdata.domain.api.verificationtype}")
	private String masterDataVerificationType;

	@Value("${masterdata.domain.api.molassestype}")
	private String masterDataMolassesType;

	@Value("${masterdata.domain.url}")
	private String masterDataUrl;

	//@Value("${usermanagement.service.url}")
	private String userManagementUrl;

	@Autowired
	RestTemplate restTemplate;

//	@Autowired
//	HitUrl hiturl;
//	
	@Value("${spring.common.username}")
	private String urll;

	@Autowired
	HttpServletRequest headerRequest;

	@Autowired
	ObjectMapper objectMapper;
	
	
	@Autowired
	ServiceHeader serviceHeader;
//	
//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${spring.common.username}")
	private String useranme;
	
	@Value("${spring.common.password}")
	private String password;

	
	//@Autowired
	//UtilityService utilityService;

	public AuthenticationDTO executeBusinessUser(String jwtToken) {
		List<ClientHttpRequestInterceptor> interceptors = serviceHeader.getHeader(jwtToken);
		restTemplate.setInterceptors(interceptors);
//		URI uri = serviceHeader.getServiceEndPointByServiceName(Constant.USER_SERVICE, Constant.USER_CONTEXT_PATH);
		String url = urll + "/authentication/check_token?token=" + jwtToken;
		AuthenticationDTO userDetails = restTemplate.getForObject(url, AuthenticationDTO.class);
		return userDetails;

	}
	



	


	
	
	

}
