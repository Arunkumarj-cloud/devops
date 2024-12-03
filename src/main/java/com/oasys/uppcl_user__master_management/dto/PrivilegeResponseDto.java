package com.oasys.uppcl_user__master_management.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class PrivilegeResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9193111483662932471L;

	@EqualsAndHashCode.Include
	private String privilegeName;

	private boolean isAssign;
	
	private String displayName;

	private Set<PrivilegeResponseDto> childs= new HashSet<>();

	public PrivilegeResponseDto(String privilegeName, String displayName) {
		super();
		this.privilegeName = privilegeName;
		this.displayName = displayName;
	}
	
	
	

}