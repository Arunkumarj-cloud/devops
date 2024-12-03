package com.oasys.feign.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.oasys.clients.userservice.utils.CommonUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

//	private static final String AUTHORIZATION_HEADER = "X-Authorization";
//	private static final String TOKEN_TYPE = "Bearer";

	
	//  @Value("${slf4jmdcfilter.uuid.key}") 
	//  private String mdcTokenKey;
	  
	//  @Value("${corealtion.rquest.hedaer.key}") 
	//  private String requestHeader;
	 

	@Autowired
	private CommonUtil commonUtil;

	@Override
	public void apply(RequestTemplate requestTemplate) {
	//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//	String toekn = commonUtil.extractHeaderToken();
	//	if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
	//		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
			// requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s",
			// TOKEN_TYPE, details.getTokenValue()));
			// requestTemplate.header(requestHeader, MDC.get(mdcTokenKey));
		//	requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, toekn));
		//	requestTemplate.header(requestHeader, MDC.get(mdcTokenKey));
		}
	}

