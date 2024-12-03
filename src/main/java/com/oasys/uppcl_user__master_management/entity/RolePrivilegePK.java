package com.oasys.uppcl_user__master_management.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class RolePrivilegePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3301562951598275693L;

	@Column(name = "role_id", updatable = false, nullable = false, length = 16)
	private UUID roleId;

	@Column(name = "privilege_name")
	private String privilegeName;
	
	
}
