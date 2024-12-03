package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PrivilegeDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2741393201736081673L;

	@NotEmpty(message = "103")
	@Pattern(regexp = "^[A-Z_]+$", message = "105")
	private String privilegeName;
	
	@Column(name = "display_name")
	@NotEmpty(message = "103")
	private String displayName;
	
	@Pattern(regexp = "^[A-Z_]+$", message = "105")
	private String parentName;

	
	private boolean isDefaultAssignToSuper = true;
	
	private Set<PrivilegeChildDto> childs;
	
	
}
