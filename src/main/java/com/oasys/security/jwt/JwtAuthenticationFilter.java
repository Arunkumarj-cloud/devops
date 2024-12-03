package com.oasys.security.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.oasys.config.Constants;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
	
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Value("${sessionExpiryTimeInSeconds}")
  	private Integer sessionExpiryTimeInSeconds;
    
    @Autowired
    private CommonDataController commonDataController;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			String path = request.getServletPath();
			log.info("api path ==== : {} ",path);
			if (path.toLowerCase().contains("/vers") || path.toLowerCase().contains("//ge")
					|| path.toLowerCase().contains("/priority") || path.toLowerCase().contains("/commonmaster")
					|| path.toLowerCase().contains("/helpdeskfaq") || path.toLowerCase().contains("/role-master")
					|| path.toLowerCase().contains("/sla") || path.toLowerCase().contains("/devicelost")
					|| path.toLowerCase().contains("/user") || path.toLowerCase().contains("/asset-type")
					|| path.toLowerCase().contains("/assetaccessories")
					|| path.toLowerCase().contains("/helpdeskWorkFlow") || path.toLowerCase().contains("/device")
					|| path.toLowerCase().contains("/helpdeskknowledge") || path.toLowerCase().contains("/ealrequest")
					|| path.toLowerCase().contains("/ealstock") || path.toLowerCase().contains("/issuefrom")
					|| path.toLowerCase().contains("/issuedetails") || path.toLowerCase().contains("/ticketstatus")
					|| path.toLowerCase().contains("/grievance")) {

				log.info("user service ==== : ");
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				String authTokenHeader = request.getHeader("X-Authorization");
				log.info("token-------------------------------------"+authTokenHeader);
				AuthenticationDTO authenticationDTO = this.executeGet(headers, authTokenHeader);
				
				log.info("user service response:: {}",authenticationDTO);
				if (Objects.isNull(authenticationDTO) || Objects.isNull(authenticationDTO.getUserId())) {
					this.authenticate(jwt);
				}
				authenticationDTO.setToken("Bearer ".concat(jwt));
				Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationDTO,
						"authentication", null);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				
				
			
				
			}
			
			/*if (path.toLowerCase().contains("/issuefrom") ||path.toLowerCase().contains("/issuedetails") || path.toLowerCase().contains("/ticketstatus")) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				String authTokenHeader = request.getHeader("X-Authorization");
				AuthenticationDTO authenticationDTO = this.executeGet(headers, authTokenHeader);
				if (Objects.isNull(authenticationDTO) || Objects.isNull(authenticationDTO.getUserId())) {
					this.authenticate(jwt);
				}
				authenticationDTO.setToken("Bearer ".concat(jwt));
				Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationDTO,
						"authentication", null);
				SecurityContextHolder.getContext().setAuthentication(authentication);
		
				
			}
			
			if (path.toLowerCase().contains("/ticket") || path.toLowerCase().contains("/ticketcategory") || path.toLowerCase().contains("/priority")|| path.toLowerCase().contains("/commonmaster")
					|| path.toLowerCase().contains("/helpdeskfaq") || path.toLowerCase().contains("/role-master")|| path.toLowerCase().contains("/sla")|| path.toLowerCase().contains("/devicelost")
					||path.toLowerCase().contains("/user")	||path.toLowerCase().contains("/asset-type")||path.toLowerCase().contains("/assetaccessories") ||path.toLowerCase().contains("/helpdeskWorkFlow")
					||path.toLowerCase().contains("/device") ||path.toLowerCase().contains("/helpdeskknowledge") ||path.toLowerCase().contains("/ealrequest") ||path.toLowerCase().contains("/ealstock")) {
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				String authTokenHeader = request.getHeader("X-Authorization");
				AuthenticationDTO authenticationDTO = this.executeGet(headers, authTokenHeader);
				if (Objects.isNull(authenticationDTO) || Objects.isNull(authenticationDTO.getUserId())) {
					this.authenticate(jwt);
				}
				authenticationDTO.setToken("Bearer ".concat(jwt));
				Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationDTO,
						"authentication", null);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
			}*/
			
			else {
				log.info("uppcl service user ==== : ");
				log.info("token-------------------------------------"+jwt);
				this.authenticate(jwt);
			}
		} catch (Exception e) {
			log.error("SignatureException token Expired or Invalid ; {}", e);
		}
		
		filterChain.doFilter(request, response);
    }

    

    
    
    
    
    
    private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("X-Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
    
    private AuthenticationDTO executeGet(HttpHeaders headers,String token) {
		try {
			if (headers.getContentType() == null) {
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			}
			AuthenticationDTO authenticationDTO = commonDataController.executeBusinessUser(token);
			return authenticationDTO;
		} catch (Exception ex) {
			log.error("error occurred while calling user service token api :{}",ex.getMessage());
		}
		return null;
	}
    
	private void authenticate(String jwt) throws IOException {
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			AuthenticationDTO authenticationDTO = tokenProvider.getCustomerObjectFromJWT(jwt);
			if (authenticationDTO != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    	authenticationDTO = executeGet("Bearer ".concat(jwt));
				log.info("========authenticationDTO=======" + authenticationDTO);
				if (authenticationDTO == null || authenticationDTO.getUserId() == null) {
					throw new InvalidTokenException("Token not allowed");
				}
				if (redisUtil.hasKey(
						authenticationDTO.getUserName() +
						  Constants.UNDERSCORE + authenticationDTO.getRole())) {
					redisUtil.expire(
							authenticationDTO.getUserName() +
							  Constants.UNDERSCORE + authenticationDTO.getRole(),
							sessionExpiryTimeInSeconds);
					log.info("========token updated======={}, token : {}", LocalDateTime.now(), redisUtil.getValue(
							authenticationDTO.getEmail() + Constants.UNDERSCORE + authenticationDTO.getEmployeeId()));
				} else {
					log.info("========token not found ======={}" + LocalDateTime.now());
					throw new InvalidTokenException("Token Expired");
				}
				Collection<GrantedAuthority> authorities = new ArrayList<>();

				// Create a new GrantedAuthority object for the "Admin" authority
				GrantedAuthority adminAuthority = new SimpleGrantedAuthority("Admin");

				// Add the admin authority to the collection
				authorities.add(adminAuthority);

				// Set the authorities for the authenticationDTO object
				authenticationDTO.setAuthorities(authorities);
				if (authenticationDTO != null && authenticationDTO.getUserId() != null) {
					authenticationDTO.setToken("Bearer ".concat(jwt));
					Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationDTO,
							"authentication", authenticationDTO.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
}
}
			else{

				throw new InvalidTokenException("Token not allowed");


			}}
}


	
}