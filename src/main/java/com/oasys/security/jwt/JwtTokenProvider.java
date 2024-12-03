package com.oasys.security.jwt;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class JwtTokenProvider {
	 private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	 
	    @Value("${app.jwtSecret}")
	    private String jwtSecret;
	    
	    @Value("${app.jwtExpirationInMs}")
	    private int jwtExpirationInMs;
	    
	    public String generateToken(String authenticationDTO) {
	        String subject = null;
	        try {
				ObjectMapper Obj = new ObjectMapper();
				subject = Obj.writeValueAsString(authenticationDTO);
			} catch (JsonProcessingException e) {
				return null;
			}
	        Date now = new Date();
	        //Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
	        String token =  Jwts.builder()
	                .setSubject(subject)
	                .setIssuedAt(new Date())
	                //.setExpiration(expiryDate)
	                .signWith(SignatureAlgorithm.HS512, jwtSecret)
	                .compact();
	        return token;
	    }

	    public AuthenticationDTO getCustomerObjectFromJWT(String token) throws IOException {
	        Claims claims = Jwts.parser()
	                .setSigningKey(jwtSecret)
	                .parseClaimsJws(token)
	                .getBody();
	        ObjectMapper mapper = new ObjectMapper();
	        AuthenticationDTO authenticationDTO = null;
			try {
				authenticationDTO = mapper.readValue(claims.getSubject(), AuthenticationDTO.class);
			} catch (JsonProcessingException exception) {
//				log.error("=======getCustomerObjectFromJWT=========",exception);
				exception.printStackTrace();
			}
	        return authenticationDTO;
	    }

	    public boolean validateToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException ex) {
	            logger.error("Invalid JWT signature");
	        } catch (MalformedJwtException ex) {
	            logger.error("Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	            logger.error("Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            logger.error("Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            logger.error("JWT claims string is empty.");
	        }
	        return false;
	    }
	    
	    public String parseJwt(String jwtToken) {
			String headerAuth = jwtToken;
			if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
				return headerAuth.substring(7, headerAuth.length());
			}
			return null;
		}

}


