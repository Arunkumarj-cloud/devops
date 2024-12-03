package com.oasys.security.jwt;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private UUID userId;

	private String userName;

	private List<String> entityList;// entity type code and table is ALLOCATED_ENTITY
	
	//private Map<String,List<LicenseDetails>> allocatedEntityName; // Department users who has assigned the actual entity
	
	private String email;
	
	private String designationCode;
	
	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
	private Collection<? extends GrantedAuthority> authorities; 
	
	private List<String> roleCodes;
	private String role;
	private String phoneNumber;
	
	private String token;
	
	private Boolean isCustomer = false;
	private String employeeId;
	
    public AuthenticationDTO(String subject) {
        this.userName = subject;
    }

	@Override
	public String toString() {
		return "AuthenticationDTO [userId=" + userId + ", userName=" + userName + ", email=" + email
				+ ", designationCode=" + designationCode + ", roleCodes=" + roleCodes + ", isCustomer=" + isCustomer
				+ ", employeeId=" + employeeId + ", role=" + role+ "]";
	}

}
