package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PrivilegeChildDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2741393201736081673L;

	@NotEmpty(message = "103")
	@Pattern(regexp = "^[A-Z_]+$", message = "105")
	private String childPrivilegeName;
	
	@Column(name = "display_name")
	@NotEmpty(message = "103")
	private String childDisplayName;
	
	private boolean childDefaultAssignToAdmin = true;
	
}
